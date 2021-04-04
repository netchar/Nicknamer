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

package com.netchar.nicknamer.presentation.infrastructure.helpers

class DoublePressHandler(private val doubleTapTimeout: Int = DEFAULT_TIMEOUT, private val listener: Listener) {
    companion object {
        const val DEFAULT_TIMEOUT = 1000
    }

    private var lastPressedTime: Long = 0

    fun perform() {
        val backPressElapsed = System.currentTimeMillis() - lastPressedTime

        if (backPressElapsed in 0..doubleTapTimeout) {
            listener.onSingleTapConfirmed()
        } else {
            listener.onSingleTap()
            lastPressedTime = System.currentTimeMillis()
        }
    }

    interface Listener {
        fun onSingleTap()
        fun onSingleTapConfirmed()
    }
}