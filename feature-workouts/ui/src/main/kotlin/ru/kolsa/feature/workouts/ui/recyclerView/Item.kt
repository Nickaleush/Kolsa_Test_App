package ru.kolsa.feature.workouts.ui.recyclerView

import ru.kolsa.core.ui.extensions.toUniqueLong
import ru.kolsa.core.ui.widgets.TextFieldViewItem
import ru.kolsa.workouts.entities.ExpandedWorkoutViewItem
import ru.kolsa.workouts.entities.WorkoutViewItem

internal object MainItem {
    const val ITEM_TYPE_TOP_BLOCK = 1
    const val ITEM_TYPE_SEARCH = 2
    const val ITEM_TYPE_FILTER = 3
    const val ITEM_TYPE_WORKOUT = 4
    const val ITEM_TYPE_SKELETON_TOP_BLOCK = 5
    const val ITEM_TYPE_SKELETON_ITEM = 6

    data object SkeletonTopBlock

    data class TopBlock(
        val title: String
    )

    data object SkeletonItem

    data class SearchItem(
        val textFieldContent: TextFieldViewItem
    )

    data class FiltersItem(
        val types: List<Int>,
        val selectedType: Int?
    )
}

internal fun Any.recyclerViewId() = when (this) {

    is MainItem.TopBlock -> {
        "TopBlock".toUniqueLong()
    }

    is MainItem.SkeletonTopBlock -> {
        "SkeletonTopBlock".toUniqueLong()
    }

    is ExpandedWorkoutViewItem -> {
        "W-$id".toUniqueLong()
    }

    is MainItem.SearchItem -> {
        "SearchItem".toUniqueLong()
    }

    is WorkoutViewItem -> {
        "W-$id".toUniqueLong()
    }

    is MainItem.FiltersItem -> {
        val chipsIds = types.joinToString { "Chip${it}" }
        chipsIds.toUniqueLong()
    }

    is MainItem.SkeletonItem -> {
        "SkeletonItem".toUniqueLong()
    }

    else -> {
        error("Unable to detect item id for: $this")
    }
}

internal fun Any.recyclerViewItemType() = when (this) {
    is MainItem.SkeletonTopBlock -> MainItem.ITEM_TYPE_SKELETON_TOP_BLOCK
    is MainItem.SkeletonItem -> MainItem.ITEM_TYPE_SKELETON_ITEM
    is MainItem.TopBlock -> MainItem.ITEM_TYPE_TOP_BLOCK
    is MainItem.SearchItem -> MainItem.ITEM_TYPE_SEARCH
    is MainItem.FiltersItem -> MainItem.ITEM_TYPE_FILTER
    is WorkoutViewItem -> MainItem.ITEM_TYPE_WORKOUT

    else -> error("Unable to detect item view type for: $this")
}