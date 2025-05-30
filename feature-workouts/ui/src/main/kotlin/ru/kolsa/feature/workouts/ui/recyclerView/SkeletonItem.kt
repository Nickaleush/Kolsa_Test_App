package ru.kolsa.feature.workouts.ui.recyclerView

private const val SKELETON_ITEMS_COUNT = 5

internal fun toSkeletonRecyclerViewItems(): List<Any> {
    val itemsList = mutableListOf<Any>()
    itemsList.add(MainItem.SkeletonTopBlock)
    repeat(SKELETON_ITEMS_COUNT) {
        itemsList.add(MainItem.SkeletonItem)
    }
    return itemsList
}