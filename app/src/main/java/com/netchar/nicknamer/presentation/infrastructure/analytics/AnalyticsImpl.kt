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