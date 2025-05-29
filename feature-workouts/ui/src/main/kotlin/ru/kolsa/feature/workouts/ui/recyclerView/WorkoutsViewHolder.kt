package ru.kolsa.feature.workouts.ui.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.SkeletonLayout
import ru.kolsa.core.ui.widgets.TextFieldViewItem
import ru.kolsa.feature.workouts.ui.view.FilterView
import ru.kolsa.feature.workouts.ui.view.SearchView
import ru.kolsa.feature.workouts.ui.view.TopBlockView
import ru.kolsa.feature.workouts.ui.view.WorkoutView
import ru.kolsa.workouts.entities.WorkoutViewItem

internal sealed class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class SkeletonTopBlockViewHolder(
        val view: SkeletonLayout,
    ) : MainViewHolder(view) {
        fun bind() {
            view.showSkeleton()
        }
    }

    class SkeletonWorkoutViewHolder(
        val view: SkeletonLayout
    ) : MainViewHolder(view) {
        fun bind() {
            view.showSkeleton()
        }
    }

    class SearchViewHolder(
        val view: SearchView
    ) : MainViewHolder(view) {
        fun bind(item: TextFieldViewItem, onTextChanged: (id: String, newValue: String) -> Unit) {
           view.setUpSearch(item, onTextChanged)
        }
    }

    class FilterViewHolder(
        val view: FilterView
    ) : MainViewHolder(view) {
        fun bind(types: List<Int>, selectedType: Int?, onTypeSelected: (Int?) -> Unit) {
            view.setChipItems(types, selectedType, onTypeSelected)
        }
    }

    class TopBlockViewHolder(
        private val block: TopBlockView,
    ) : MainViewHolder(block) {
        fun bind(title: String) {
            block.configure(title)
        }
    }

    class WorkoutViewHolder(
        private val view: WorkoutView,
    ) : MainViewHolder(view) {
        fun bind(
            workoutViewItem: WorkoutViewItem,
            onPropertyClick: ((WorkoutView.ItemClickResult) -> Unit),
        ) {
            view.configure(
                item = workoutViewItem
            )
            view.onPropertyClick = onPropertyClick
        }
    }

//    class ResidentialViewHolder(
//        val view: ResidentialRecyclerView
//    ) : MainViewHolder(view) {
//        fun bind(
//            residentialItems: List<ResidentialViewItem>,
//            onResidentialClick: ((ResidentialViewItem) -> Unit)
//        ) {
//            view.setResidentialList(residentialItems)
//            view.onItemClickListener = onResidentialClick
//        }
//    }
}