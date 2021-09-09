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

package com.netchar.nicknamer.presentation.infrastructure.analytics

import com.netchar.nicknamer.domen.NicknameGenerator.*
import com.netchar.nicknamer.presentation.ui.about.Contact

interface Analytics {
    fun trackEvent(event: AnalyticsEvent)
    fun trackScreen(screenEvent: AnalyticsEvent.ViewScreen)
}

sealed class AnalyticsEvent(val eventName: String, val parameters: Map<String, String>) {
    private companion object Constants {
        object ViewScreen {
            const val EVENT = "view_screen"
            const val PARAM_SCREEN_NAME = "screen_name"
        }

        object Event {
            const val EVENT = "event"
            const val GENERATE_NICKNAME = "generate_nickname"
            const val COPY_TO_CLIPBOARD = "copy_to_clipboard"
            const val PARAM_EVENT_NAME = "event_name"

            object SelectItem {
                const val EVENT = "select_item"
                const val PARAM_ITEM_NAME = "item_name"
            }
        }
    }

    class ViewScreen(screen: Screen) : AnalyticsEvent(Constants.ViewScreen.EVENT, mapOf(Constants.ViewScreen.PARAM_SCREEN_NAME to screen.value)) {
        enum class Screen(val value: String) {
            MAIN("Main"),
            ABOUT("About")
        }
    }
    class Event(eventName: String) : AnalyticsEvent(Constants.Event.EVENT, mapOf(Constants.Event.PARAM_EVENT_NAME to eventName))
    class CopyToClipboard(eventName: String) : AnalyticsEvent(Constants.Event.COPY_TO_CLIPBOARD, mapOf(Constants.Event.PARAM_EVENT_NAME to eventName))
    class SelectItem(itemName: String) : AnalyticsEvent(Constants.Event.SelectItem.EVENT, mapOf(Constants.Event.SelectItem.PARAM_ITEM_NAME to itemName))
    class GenerateNickname(config: Config) : AnalyticsEvent(Constants.Event.GENERATE_NICKNAME, config.toToAnalyticsParams()) {
        companion object : AnalyticsDictionaryMapper<Config> {
            override fun Config.toToAnalyticsParams() = mapOf(
                    "gender" to gender.value,
                    "alphabet" to alphabet.value,
                    "nickname_length" to nicknameLength.toString()
            )
        }
    }
    class SelectContact(contract: Contact) : AnalyticsEvent(Constants.Event.SelectItem.EVENT, mapOf(contract.toAnalyticsParams())) {
        companion object : AnalyticsPairMapper<Contact> {
            override fun Contact.toAnalyticsParams() = "contact" to when (this) {
                Contact.LinkedIn -> "LinkedIn"
                Contact.Mail -> "Mail"
            }
        }
    }
}



