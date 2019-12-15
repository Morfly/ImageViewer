package com.morfly.imageviewer

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WeakProperty<T>(private var value: WeakReference<T?>) : ReadWriteProperty<Any?, T?> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return value.get()
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = WeakReference(value)
    }
}

fun <T> weak(value: T? = null) = WeakProperty(WeakReference(value))