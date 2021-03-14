package com.netchar.nicknamer.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.netchar.nicknamer.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(this.getString(R.string.copy_clipboard_label), text)
    clipboard.setPrimaryClip(clip)
}

/**
 * Fragment binding delegate, may be used since onViewCreated up to onDestroyView (inclusive)
 * */
fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
        object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
            private var binding: T? = null

            override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
                    binding ?: factory(requireView()).also {
                        // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                            viewLifecycleOwner.lifecycle.addObserver(this)
                            binding = it
                        }
                    }

            override fun onDestroy(owner: LifecycleOwner) {
                binding = null
            }
        }