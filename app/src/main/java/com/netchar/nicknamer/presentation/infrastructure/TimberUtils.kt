package com.netchar.nicknamer.presentation.infrastructure

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class DebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "${super.createStackElementTag(element)}.${element.methodName}(); Line: ${element.lineNumber}"
    }
}

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {

        if (priority == Log.ERROR || priority == Log.WARN) {
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.log(message)

            if (throwable != null) {
                crashlytics.recordException(throwable)
            }
        }
    }
}
