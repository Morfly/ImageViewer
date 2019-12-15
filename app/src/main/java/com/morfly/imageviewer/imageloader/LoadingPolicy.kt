package com.morfly.imageviewer.imageloader

enum class LoadingPolicy(val index: Int) {
    NO_MEMORY_CACHE(1 shl 0),
    NO_DISK_CACHE(1 shl 1),
    OFFLINE(1 shl 2);


    operator fun invoke() = index
}