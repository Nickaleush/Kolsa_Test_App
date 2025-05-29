package ru.kolsa.workouts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.kolsa.core.ui.extensions.isLoading
import ru.kolsa.core.ui.widgets.TextFieldViewItem
import ru.kolsa.domain.entity.Workout
import ru.kolsa.workouts.WorkoutsContract.Event
import ru.kolsa.workouts.WorkoutsContract.Intent
import ru.kolsa.workouts.WorkoutsContract.State
import ru.kolsa.workouts.businesslogic.WorkoutsService
import ru.kolsa.workouts.entities.ExpandedWorkoutViewItem
import ru.kolsa.workouts.entities.WorkoutViewItem
import kotlin.properties.Delegates

class WorkoutsViewModel(
    private val service: WorkoutsService
) : ViewModel() {

    private companion object {
        const val FIELD_ID_SEARCH = "FIELD_ID_SEARCH"
    }

    private val cachedWorkoutsFlow = MutableStateFlow<List<Workout>?>(null)

    private val stateFlow = MutableStateFlow<State>(State.Loading)
    val state = stateFlow.asStateFlow()

    private var selectedType: Int? = null

    private val sideEffectsChannel = Channel<Event>(Channel.BUFFERED)
    val sideEffects: Flow<Event> = sideEffectsChannel.receiveAsFlow()

    private var selectedWorkout: Workout? by Delegates.observable(null) { _, _, _ ->
        updateState()
    }

    private val textFieldsContents = hashMapOf<String, String>().also {
        it[FIELD_ID_SEARCH] = ""
    }

    private var forceUpdateFlag = false

    init {
        observeWorkouts()
        loadWorkouts()
    }

    private fun observeWorkouts() {
        viewModelScope.launch {
            cachedWorkoutsFlow.collect { workouts ->
                if (workouts == null) {
                    stateFlow.value = State.Loading
                } else {
                    updateState(workouts)
                }
            }
        }
    }

    private fun updateState(
        workouts: List<Workout>? = cachedWorkoutsFlow.value,
        videoUrl: String? = null
    ) {
        if (workouts == null) return

        val searchQuery = textFieldsContents[FIELD_ID_SEARCH].orEmpty().trim().lowercase()

        val filteredWorkouts = if (searchQuery.isBlank()) {
            workouts
        } else {
            workouts.filter { workout ->
                workout.title.lowercase().contains(searchQuery)
            }
        }

        stateFlow.value = buildLoadedState(filteredWorkouts, videoUrl)
    }

    private fun buildLoadedState(
        workouts: List<Workout>,
        videoUrl: String?
    ): State.Loaded {
        val blocks = listOfNotNull(
            WorkoutsContract.Block.Top("БУДЬ УВЕРЕНА В СВОЕЙ КРАСОТЕ"),
            WorkoutsContract.Block.Search(
                TextFieldViewItem(
                    id = FIELD_ID_SEARCH,
                    title = "",
                    content = textFieldsContents[FIELD_ID_SEARCH].orEmpty(),
                    placeholder = "Поиск",
                    isEnabled = true
                )
            ),
            WorkoutsContract.Block.Workouts(
                workouts.map { WorkoutViewItem(it) }
            )
        )

        return if (selectedWorkout != null && !videoUrl.isNullOrBlank()) {
            State.Loaded.Expanded(
                blocks = blocks,
                expandedWorkout = ExpandedWorkoutViewItem(
                    selectedWorkout!!,
                    videoUrl
                )
            )
        } else {
            State.Loaded.Collapsed(
                blocks = blocks,
                distinctSwitch = forceUpdateFlag
            )
        }
    }

    private fun loadWorkouts() {
        viewModelScope.launch {
            if (cachedWorkoutsFlow.value != null) return@launch

            val result = service.getWorkouts()
            when {
                result.isLoading -> stateFlow.value = State.Loading
                result.isFailure -> {
                    stateFlow.value = State.Error("Ошибка загрузки данных")
                    sideEffectsChannel.send(Event.ShowSnackBar("Пожалуйста, попробуйте позже"))
                }

                else -> {
                    val workouts = result.getOrThrow()
                    cachedWorkoutsFlow.value = workouts
                }
            }
        }
    }

    private fun refreshData() {
        cachedWorkoutsFlow.value = null
        loadWorkouts()
    }

    private fun forceUpdateUI() {
        forceUpdateFlag = !forceUpdateFlag
        loadWorkouts()
    }

    fun intent(intent: Intent) {
        Log.d("Intent Logger", "New Intent: $intent")
        when (intent) {
            is Intent.RefreshData -> {
                viewModelScope.launch {
                    delay(1000)
                    sideEffectsChannel.send(Event.HideSwipeRefresh)
                    refreshData()
                }
            }

            is Intent.Collapse -> {
                selectedWorkout = null
                viewModelScope.launch {
                    sideEffectsChannel.send(Event.ReleasePlayer)
                }
            }

            is Intent.SearchFieldChanged -> {
                textFieldsContents[intent.id] = intent.newContent
                updateState()
            }

            is Intent.FilterToggled -> {

            }

            is Intent.ExpandWorkout -> {
                viewModelScope.launch {
                    val workout =
                        cachedWorkoutsFlow.value?.firstOrNull { it.id == intent.workoutId }
                            ?: service.getWorkouts().getOrNull()
                                ?.firstOrNull { it.id == intent.workoutId }

                    if (workout != null) {
                        val videoUrlResult = service.getWorkoutVideoUrl(workout.id.toString())
                        if (videoUrlResult.isSuccess) {
                            val videoUrl = videoUrlResult.getOrThrow()
                            selectedWorkout = workout
                            updateState(videoUrl = videoUrl.link)
                        } else {
                            sideEffectsChannel.send(Event.ShowSnackBar("Не удалось загрузить видео"))
                        }
                    }
                }
            }
        }
    }
}
