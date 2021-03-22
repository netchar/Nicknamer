/*
 * Copyright  (c) 2021 Eugene Glushankov
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

package com.netchar.nicknamer.presentation.infrastructure.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsImpl(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {
    override fun trackEvent(event: AnalyticsEvent) {
        val bundle = Bundle()

        for (parameter in event.parameters) {
            bundle.putString(parameter.key, parameter.value)
        }

        firebaseAnalytics.logEvent(event.eventName, bundle)
    }

    override fun trackScreen(screenEvent: AnalyticsEvent.ViewScreen) {
        val bundle = Bundle().apply {
            putString(screenEvent.parameters.keys.first(), screenEvent.parameters.values.first())
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}