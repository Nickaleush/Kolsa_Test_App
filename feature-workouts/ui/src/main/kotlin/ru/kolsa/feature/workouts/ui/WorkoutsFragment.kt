package ru.kolsa.feature.workouts.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import me.saket.inboxrecyclerview.page.PageCollapseEligibilityHapticFeedback
import me.saket.inboxrecyclerview.page.SimplePageStateChangeCallbacks
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kolsa.core.ui.extensions.collectFlowWithLifecycle
import ru.kolsa.core.ui.extensions.showSnackBar
import ru.kolsa.core.ui.utils.setupExpandableWorkout
import ru.kolsa.feature.workouts.ui.databinding.WorkoutsScreenBinding
import ru.kolsa.feature.workouts.ui.recyclerView.WorkoutsAdapter
import ru.kolsa.feature.workouts.ui.recyclerView.recyclerViewId
import ru.kolsa.feature.workouts.ui.recyclerView.toRecyclerViewItems
import ru.kolsa.feature.workouts.ui.recyclerView.toSkeletonRecyclerViewItems
import ru.kolsa.feature.workouts.ui.view.expandedworkout.ExpandedWorkoutViewClickAction
import ru.kolsa.workouts.WorkoutsContract
import ru.kolsa.workouts.WorkoutsViewModel

class WorkoutsFragment : Fragment(R.layout.workouts_screen) {

    private companion object {
        const val PULL_TO_COLLAPSE_VALUE = 35
    }

    private val binding by viewBinding(WorkoutsScreenBinding::bind)

    private val viewModel: WorkoutsViewModel by viewModel()

    private val pageStateChangeCallback = object : SimplePageStateChangeCallbacks() {
        override fun onPageCollapsed() {
            super.onPageCollapsed()
            viewModel.intent(WorkoutsContract.Intent.Collapse)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        observeViewModel()
        setupExpandableWorkout(
            binding.workoutsRecycler,
            binding.expandableWorkout,
            PULL_TO_COLLAPSE_VALUE
        )
        if (binding.workoutsRecycler.adapter == null) {
            binding.workoutsRecycler.adapter = WorkoutsAdapter(
                onKolsaLogoClick = {
                    viewModel.intent(WorkoutsContract.Intent.KolsaLogoClick)
                },
                onSelectWorkoutClick = { p ->
                    viewModel.intent(WorkoutsContract.Intent.ExpandWorkout(p.item.id))
                },
                onTextChanged = { id, newValue ->
                    viewModel.intent(WorkoutsContract.Intent.SearchFieldChanged(id, newValue))
                },
                onTypeSelected = { type ->
                    viewModel.intent(WorkoutsContract.Intent.FilterToggled(type))
                }
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            if (viewModel.state.value.expandedWorkout()) {
                viewModel.intent(WorkoutsContract.Intent.Collapse)
            } else {
                requireActivity().finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.expandableWorkout.addStateChangeCallbacks(pageStateChangeCallback)
        binding.expandableWorkout.addOnPullListener(PageCollapseEligibilityHapticFeedback(binding.expandableWorkout))
    }

    override fun onPause() {
        super.onPause()
        binding.expandableWorkout.removeStateChangeCallbacks(pageStateChangeCallback)
    }

    private fun setUpListeners() {
        binding.swipeToRefreshView.setOnRefreshListener {
            viewModel.intent(WorkoutsContract.Intent.RefreshData)
        }
        binding.expandedWorkoutView.actionClickListener = click@{
            when (it) {
                is ExpandedWorkoutViewClickAction.Toolbar -> {
                    binding.workoutsRecycler.collapse()
                }
            }
        }
    }

    private fun observeViewModel() {
        observeState()
        observeEvents()
    }

    private fun observeState() {
        collectFlowWithLifecycle(viewModel.state) {
            when (it) {
                is WorkoutsContract.State.Error -> {

                }

                is WorkoutsContract.State.Loaded.Collapsed -> {
                    (binding.workoutsRecycler.adapter as? WorkoutsAdapter)?.updateItems(it.toRecyclerViewItems())
                    binding.swipeToRefreshView.isEnabled = true
                    if (binding.workoutsRecycler.expandablePage?.isExpanded == true) {
                        binding.workoutsRecycler.collapse()
                        binding.expandedWorkoutView.releasePlayer()
                    }
                }

                is WorkoutsContract.State.Loaded.Expanded -> {
                    val workout = it.expandedWorkout
                    binding.expandedWorkoutView.updateWorkoutItem(
                        item = workout
                    )
                    binding.workoutsRecycler.expandItem(
                        workout.recyclerViewId(),
                        binding.expandableWorkout.isExpanded
                    )
                }

                is WorkoutsContract.State.Loading -> {
                    (binding.workoutsRecycler.adapter as? WorkoutsAdapter)?.updateItems(
                        toSkeletonRecyclerViewItems()
                    )
                    binding.swipeToRefreshView.isEnabled = false
                }

                is WorkoutsContract.State.Empty -> {

                }
            }
        }
    }

    private fun WorkoutsContract.State.expandedWorkout() =
        loadedOrNull is WorkoutsContract.State.Loaded.Expanded

    private fun observeEvents() {
        collectFlowWithLifecycle(viewModel.sideEffects) {
            when (it) {
                WorkoutsContract.Event.HideSwipeRefresh -> {
                    binding.swipeToRefreshView.isRefreshing = false
                }

                is WorkoutsContract.Event.ShowSnackBar -> {
                    binding.root.showSnackBar(
                        message = it.message,
                        actionText = it.actionText.orEmpty(),
                        actionTextColor = ResourcesCompat.getColor(
                            resources,
                            ru.kolsa.core.ui.R.color.white,
                            null
                        )
                    )
                }

                is WorkoutsContract.Event.ReleasePlayer -> {
                    binding.expandedWorkoutView.releasePlayer()
                }

                is WorkoutsContract.Event.OpenAboutKolsaScreen -> {
                    findNavController().navigate(R.id.action_workoutsFragment_to_about_nav_graph)
                }
            }
        }
    }
}