package io.parrotsoftware.qatest.common.base.deletagate

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ViewBindingDelegate
 * @author (c) 2023, QA Test
 */

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline factory: (View?) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        @MainThread
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(view).also {
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            viewLifecycleOwner.lifecycle.removeObserver(this)
            Handler(Looper.getMainLooper()).post {
                binding = null
            }
        }

    }