package ru.kolsa.feature.workouts.ui.view.expandedworkout

import ru.kolsa.workouts.entities.ExpandedWorkoutViewItem

sealed class ExpandedWorkoutViewClickAction {
    abstract val item: ExpandedWorkoutViewItem
    data class Toolbar(
        override val item: ExpandedWorkoutViewItem
    ) : ExpandedWorkoutViewClickAction()
}