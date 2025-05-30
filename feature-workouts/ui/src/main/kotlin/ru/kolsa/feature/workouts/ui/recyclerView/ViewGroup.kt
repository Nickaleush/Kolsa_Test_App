package ru.kolsa.feature.workouts.ui.recyclerView

import android.view.ViewGroup
import ru.kolsa.core.ui.extensions.dp
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.feature.workouts.ui.databinding.TopBlockSkeletonItemBinding
import ru.kolsa.feature.workouts.ui.databinding.WorkoutSkeletonItemBinding
import ru.kolsa.feature.workouts.ui.view.FilterView
import ru.kolsa.feature.workouts.ui.view.SearchView
import ru.kolsa.feature.workouts.ui.view.TopBlockView
import ru.kolsa.feature.workouts.ui.view.WorkoutView

internal fun ViewGroup.createMainViewHolder(
    type: Int
): WorkoutsViewHolder {
    return when (type) {
        MainItem.ITEM_TYPE_TOP_BLOCK -> createTopBlock()

        MainItem.ITEM_TYPE_SEARCH -> createSearchBlock()

        MainItem.ITEM_TYPE_FILTER -> createFiltersBlock()

        MainItem.ITEM_TYPE_WORKOUT -> createWorkoutView()

        MainItem.ITEM_TYPE_SKELETON_TOP_BLOCK -> createSkeletonTopBlock()

        MainItem.ITEM_TYPE_SKELETON_ITEM -> createSkeletonWorkout()

        else -> error("Unknown item type")
    }
}

private fun ViewGroup.createSkeletonTopBlock(): WorkoutsViewHolder {
    return WorkoutsViewHolder.SkeletonTopBlockViewHolder(
        TopBlockSkeletonItemBinding.inflate(layoutInflater).root
    )
}

private fun ViewGroup.createFiltersBlock(): WorkoutsViewHolder {
    return WorkoutsViewHolder.FilterViewHolder(
        FilterView(context).also {
            it.layoutParams = defaultParams()
            it.setPadding(10.dp, 10.dp, 10.dp, 10.dp)
        }
    )
}

private fun ViewGroup.createSkeletonWorkout(): WorkoutsViewHolder {
    return WorkoutsViewHolder.SkeletonWorkoutViewHolder(
        WorkoutSkeletonItemBinding.inflate(layoutInflater).root
    )
}

private fun ViewGroup.createSearchBlock(): WorkoutsViewHolder {
    return WorkoutsViewHolder.SearchViewHolder(
        SearchView(context).also {
            it.layoutParams = defaultParams()
        }
    )
}

private fun ViewGroup.createTopBlock(): WorkoutsViewHolder {
    return WorkoutsViewHolder.TopBlockViewHolder(
        TopBlockView(context).also {
            it.layoutParams = defaultParams()
        }
    )
}

private fun ViewGroup.createWorkoutView(): WorkoutsViewHolder {
    return WorkoutsViewHolder.WorkoutViewHolder(
        WorkoutView(context).also {
            it.layoutParams = defaultParams()
        }
    )
}

private fun defaultParams(): ViewGroup.MarginLayoutParams {
    return ViewGroup.MarginLayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}