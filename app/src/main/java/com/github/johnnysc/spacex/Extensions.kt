package com.github.johnnysc.spacex

import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.util.*

inline val currentYear: Int
    get() = Calendar.getInstance().get(Calendar.YEAR)

inline fun <reified T> LiveData<T>.observe(
    owner: LifecycleOwner,
    noinline onChanged: (t: T) -> Unit
): Unit =
    observe(owner, Observer(onChanged))

inline fun <reified VM : ViewModel> Fragment.getViewModel(
    factory: ViewModelProvider.Factory? = null
): VM =
    ViewModelProviders.of(
        this,
        factory
    ).get(VM::class.java)

inline fun <reified VM : ViewModel> AppCompatActivity.getViewModel(
    factory: ViewModelProvider.Factory? = null
): VM =
    ViewModelProviders.of(
        this,
        factory
    ).get(VM::class.java)

fun ArrayAdapter<String>.update(list: List<String>) {
    clear()
    addAll(list)
    notifyDataSetChanged()
}
