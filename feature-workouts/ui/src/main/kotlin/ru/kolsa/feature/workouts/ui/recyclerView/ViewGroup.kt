package ru.kolsa.feature.workouts.ui.recyclerView

import android.view.ViewGroup
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.feature.workouts.ui.databinding.FilterItemBinding
import ru.kolsa.feature.workouts.ui.databinding.TopBlockSkeletonItemBinding
import ru.kolsa.feature.workouts.ui.databinding.WorkoutSkeletonItemBinding
import ru.kolsa.feature.workouts.ui.view.FilterView
import ru.kolsa.feature.workouts.ui.view.SearchView
import ru.kolsa.feature.workouts.ui.view.TopBlockView
import ru.kolsa.feature.workouts.ui.view.WorkoutView

internal fun ViewGroup.createMainViewHolder(
    type: Int
): MainViewHolder {
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

private fun ViewGroup.createSkeletonTopBlock(): MainViewHolder {
    return MainViewHolder.SkeletonTopBlockViewHolder(
        TopBlockSkeletonItemBinding.inflate(layoutInflater).root
    )
}

private fun ViewGroup.createFiltersBlock(): MainViewHolder {
    return MainViewHolder.FilterViewHolder(
        FilterView(context).also {
            it.layoutParams = defaultParams()
        }
    )
}

private fun ViewGroup.createSkeletonWorkout(): MainViewHolder {
    return MainViewHolder.SkeletonWorkoutViewHolder(
        WorkoutSkeletonItemBinding.inflate(layoutInflater).root
    )
}

private fun ViewGroup.createSearchBlock(): MainViewHolder {
    return MainViewHolder.SearchViewHolder(
        SearchView(context).also {
            it.layoutParams = defaultParams()
        }
    )
}

private fun ViewGroup.createTopBlock(): MainViewHolder {
    return MainViewHolder.TopBlockViewHolder(
        TopBlockView(context).also {
            it.layoutParams = defaultParams()
        }
    )
}

private fun ViewGroup.createWorkoutView(): MainViewHolder {
    return MainViewHolder.WorkoutViewHolder(
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