package ru.kolsa.core.ui.skeleton

import ru.kolsa.core.ui.R

data class SkeletonViewItem(
    val shimmerColor: Int = R.color.skeletonShimmer,
    val maskColor: Int = R.color.skeletonMask,
    val shimmerDuration: Long = 1500L
)