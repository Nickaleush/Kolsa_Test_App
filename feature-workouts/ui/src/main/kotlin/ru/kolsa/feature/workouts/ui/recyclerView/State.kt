package ru.kolsa.feature.workouts.ui.recyclerView

import android.util.Log
import ru.kolsa.workouts.WorkoutsContract

internal fun WorkoutsContract.State.Loaded.toRecyclerViewItems(): List<Any> {
    val itemsList = mutableListOf<Any>()
    blocks.forEach {
        when (it) {
            is WorkoutsContract.Block.Top -> itemsList.add(MainItem.TopBlock(it.title))
            is WorkoutsContract.Block.Filter -> itemsList.add(MainItem.FiltersItem(it.types,it.selectedType))
            is WorkoutsContract.Block.Workouts -> itemsList.addAll(it.workouts)
            is WorkoutsContract.Block.Search -> itemsList.add(MainItem.SearchItem(it.item))
        }
    }
    Log.d("fwwsv", "$itemsList")
    return itemsList
}