package ru.kolsa.feature.workouts.ui.recyclerView

import android.util.Log
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.kolsa.feature.workouts.ui.view.WorkoutView
import ru.kolsa.workouts.entities.WorkoutViewItem

internal class WorkoutsAdapter(
    private val onKolsaLogoClick: (() -> Unit),
    private val onSelectWorkoutClick: (WorkoutView.ItemClickResult) -> Unit,
    private val onTextChanged: (id: String, newValue: String) -> Unit,
    private val onTypeSelected: (Int?) -> Unit
) : RecyclerView.Adapter<WorkoutsViewHolder>() {

    private val differ = AsyncListDiffer(this, WorkoutsDiffUtilCallback)
    private val displayedItems = mutableSetOf<Int>()

    init {
        setHasStableIds(true)
    }

    override fun onViewAttachedToWindow(holder: WorkoutsViewHolder) {
        super.onViewAttachedToWindow(holder)
        val animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            ru.kolsa.core.ui.R.anim.fall_down
        )
        if (!displayedItems.contains(holder.layoutPosition)) {
            holder.itemView.startAnimation(animation)
            displayedItems.add(holder.layoutPosition)
        } else {
            holder.itemView.clearAnimation()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        return parent.createMainViewHolder(
            viewType
        )
    }

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) {
        val items = differ.currentList
        when (val item = items[position]) {

            is MainItem.SkeletonTopBlock -> {
                (holder as? WorkoutsViewHolder.SkeletonTopBlockViewHolder)?.bind()
            }

            is MainItem.SkeletonItem -> {
                (holder as? WorkoutsViewHolder.SkeletonWorkoutViewHolder)?.bind()
            }

            is MainItem.SearchItem -> {
                (holder as? WorkoutsViewHolder.SearchViewHolder)?.bind(
                    item.textFieldContent,
                    onTextChanged
                )
            }

            is MainItem.TopBlock -> {
                (holder as? WorkoutsViewHolder.TopBlockViewHolder)?.bind(item.title, onKolsaLogoClick)
            }

            is MainItem.FiltersItem -> {
                (holder as? WorkoutsViewHolder.FilterViewHolder)?.bind(
                    item.types,
                    item.selectedType
                ) { selectedType ->
                    onTypeSelected.invoke(selectedType)
                }
            }

            is WorkoutViewItem -> {
                (holder as? WorkoutsViewHolder.WorkoutViewHolder)?.bind(item, onSelectWorkoutClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].recyclerViewItemType()
    }

    override fun getItemId(position: Int): Long {
        return differ.currentList[position].recyclerViewId()
    }

    fun updateItems(items: List<Any>) {
        differ.submitList(items)
    }
}