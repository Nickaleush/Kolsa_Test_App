package ru.kolsa.workouts

import ru.kolsa.core.ui.widgets.TextFieldViewItem
import ru.kolsa.workouts.entities.ExpandedWorkoutViewItem
import ru.kolsa.workouts.entities.WorkoutViewItem

object WorkoutsContract {

    sealed class Block {

        data class Top(
            val title: String
        ) : Block()

        data class Search(
            val item: TextFieldViewItem
        ) : Block()

        data class Filter(
            val types: List<Int>,
            val selectedType: Int?
        ) : Block()

        data class Workouts(
            val workouts: List<WorkoutViewItem>
        ) : Block()
    }

    sealed class State {
        val loadedOrNull: Loaded?
            get() = this as? Loaded

        val title: String = "Будь уверена в своей красоте!"

        data object Loading : State()

        sealed class Loaded : State() {
            abstract val blocks: List<Block>

            data class Collapsed(
                override val blocks: List<Block>
            ) : Loaded()

            data class Expanded(
                override val blocks: List<Block>,
                val expandedWorkout: ExpandedWorkoutViewItem
            ) : Loaded()
        }

        data class Empty(
            val text: String
        ) : State()

        data class Error(
            val text: String,
        ) : State()
    }

    sealed class Intent {
        data object RefreshData : Intent()
        data class ExpandWorkout(val workoutId: Int) : Intent()
        data object Collapse : Intent()
        data class FilterToggled(val type: Int?) : Intent()
        data class SearchFieldChanged(val id: String, val newContent: String) : Intent()
    }

    sealed class Event {
        data class ShowSnackBar(
            val message: String,
            val actionText: String? = null,
        ) : Event()

        data object ReleasePlayer: Event()

        data object HideSwipeRefresh : Event()
    }
}