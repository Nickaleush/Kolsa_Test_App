package ru.kolsa.feature.workouts.ui.recyclerView

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.kolsa.feature.workouts.ui.view.FilterView
import ru.kolsa.feature.workouts.ui.view.WorkoutView
import ru.kolsa.workouts.entities.WorkoutViewItem

internal class WorkoutsAdapter(
    private val onSelectWorkoutClick: (WorkoutView.ItemClickResult) -> Unit,
    private val onTextChanged: (id: String, newValue: String) -> Unit,
    private val onTypeSelected: (Int?) -> Unit
) : RecyclerView.Adapter<MainViewHolder>() {

    private val differ = AsyncListDiffer(this, WorkoutsDiffUtilCallback)
    private val displayedItems = mutableSetOf<Int>()

    init {
        setHasStableIds(true)
    }

    override fun onViewAttachedToWindow(holder: MainViewHolder) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return parent.createMainViewHolder(
            viewType
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val items = differ.currentList
        when (val item = items[position]) {

            is MainItem.SkeletonTopBlock -> (holder as? MainViewHolder.SkeletonTopBlockViewHolder)?.bind()

            is MainItem.SkeletonItem -> {
                (holder as? MainViewHolder.SkeletonWorkoutViewHolder)?.bind()
            }

            is MainItem.SearchItem -> {
                (holder as? MainViewHolder.SearchViewHolder)?.bind(item.textFieldContent, onTextChanged)
            }

            is MainItem.TopBlock -> {
                (holder as? MainViewHolder.TopBlockViewHolder)?.bind(item.title)
            }

            is MainItem.FiltersItem -> {
                (holder as? MainViewHolder.FilterViewHolder)?.bind(
                    item.types,
                    item.selectedType
                ) { selectedType ->
                    onTypeSelected.invoke(selectedType)
                }
            }

            is WorkoutViewItem -> {
                (holder as? MainViewHolder.WorkoutViewHolder)?.bind(item, onSelectWorkoutClick)
            }
        }
    }

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        val payload = payloads.getOrNull(0) as? List<*>
        if (payload.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        payload.forEach {
//            when (it) {
//                MainDiffUtilCallback.CHANGED_TOP_BLOCK -> {
//                    val config = differ.currentList[position] as? MainTopBlockConfig ?: return@forEach
//                    (holder as? MainViewHolder.MainTopBlockViewHolder)?.block?.configure(config)
//                }
//
//                MainDiffUtilCallback.CHANGED_CHIPS -> {
//                    val config = differ.currentList[position] as? MainItem.Chips
//                        ?: return@forEach
//                    (holder as? MainViewHolder.EmergencyAnnouncementViewHolder)?.view?.configure(
//                        config.item
//                    )
//                }
//
//                MainDiffUtilCallback.CHANGED_COMMON_ANNOUNCEMENTS -> {
//                    val config = differ.currentList[position] as? MainItem.Announcements
//                        ?: return@forEach
//                    (holder as? MainViewHolder.AnnouncementsViewHolder)?.view?.setup(config.item)
//                }
//            }
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