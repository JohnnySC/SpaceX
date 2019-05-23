package com.github.johnnysc.spacex

import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*

inline val currentYear: Int
    get() = Calendar.getInstance().get(Calendar.YEAR)

inline fun <reified T> LiveData<T>.observe(
    owner: LifecycleOwner,
    noinline onChanged: (t: T) -> Unit
): Unit =
    observe(owner, Observer(onChanged))

fun ArrayAdapter<String>.update(list: List<String>) {
    clear()
    addAll(list)
    notifyDataSetChanged()
}
