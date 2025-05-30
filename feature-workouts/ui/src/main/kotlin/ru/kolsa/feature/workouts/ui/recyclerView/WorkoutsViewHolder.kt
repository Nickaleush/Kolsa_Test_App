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

internal sealed class WorkoutsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class SkeletonTopBlockViewHolder(
        val view: SkeletonLayout,
    ) : WorkoutsViewHolder(view) {
        fun bind() {
            view.showSkeleton()
        }
    }

    class SkeletonWorkoutViewHolder(
        val view: SkeletonLayout
    ) : WorkoutsViewHolder(view) {
        fun bind() {
            view.showSkeleton()
        }
    }

    class SearchViewHolder(
        val view: SearchView
    ) : WorkoutsViewHolder(view) {
        fun bind(item: TextFieldViewItem, onTextChanged: (id: String, newValue: String) -> Unit) {
            view.setUpSearch(item, onTextChanged)
        }
    }

    class FilterViewHolder(
        val view: FilterView
    ) : WorkoutsViewHolder(view) {
        fun bind(types: List<Int>, selectedType: Int?, onTypeSelected: (Int?) -> Unit) {
            view.setChipItems(types, selectedType, onTypeSelected)
        }
    }

    class TopBlockViewHolder(
        private val view: TopBlockView,
    ) : WorkoutsViewHolder(view) {
        fun bind(
            title: String,
            onKolsaLogoClick: (() -> Unit)
        ) {
            view.configure(title)
            view.onKolsaLogoClick = onKolsaLogoClick
        }
    }

    class WorkoutViewHolder(
        private val view: WorkoutView,
    ) : WorkoutsViewHolder(view) {
        fun bind(
            workoutViewItem: WorkoutViewItem,
            onWorkoutClick: ((WorkoutView.ItemClickResult) -> Unit),
        ) {
            view.configure(
                item = workoutViewItem
            )
            view.onWorkoutClick = onWorkoutClick
        }
    }
}