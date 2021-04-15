/*
 * Copyright (c) 2021 Eugene Glushankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netchar.nicknamer.presentation.infrastructure.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider

object SliderBindingAdapter {
    @InverseBindingAdapter(attribute = "android:value")
    @JvmStatic
    fun getSliderValue(slider: Slider) = slider.value

    @BindingAdapter("android:valueAttrChanged")
    @JvmStatic
    fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
        slider.addOnChangeListener { _, _, _ ->
            attrChange.onChange()
        }
    }
}

