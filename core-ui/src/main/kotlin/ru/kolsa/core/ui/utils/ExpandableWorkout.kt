package ru.kolsa.core.ui.utils

import androidx.fragment.app.Fragment
import me.saket.inboxrecyclerview.InboxRecyclerView
import me.saket.inboxrecyclerview.animation.ItemExpandAnimator
import me.saket.inboxrecyclerview.dimming.DimPainter
import me.saket.inboxrecyclerview.page.ExpandablePageLayout
import ru.kolsa.core.ui.R
import ru.kolsa.core.ui.extensions.dp

fun Fragment.setupExpandableWorkout(
    rv: InboxRecyclerView,
    propertyPage: ExpandablePageLayout,
    collapseThreshold: Int
) {
    rv.expandablePage = propertyPage
    rv.dimPainter = DimPainter.listAndPage(
        listColor = resources.getColor(R.color.purple, null),
        listAlpha = 0.2F,
        pageColor = resources.getColor(R.color.white, null),
        pageAlpha = 0.5F
    )
    rv.itemExpandAnimator = ItemExpandAnimator.scale()
    propertyPage.pullToCollapseThresholdDistance = collapseThreshold.dp
    propertyPage.animationDurationMillis = 500L
}