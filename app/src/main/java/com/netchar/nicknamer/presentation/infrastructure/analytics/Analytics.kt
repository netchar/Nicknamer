package com.netchar.nicknamer.presentation.infrastructure.analytics

import com.netchar.nicknamer.domen.service.NicknameGeneratorService
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
    class SelectItem(itemName: String) : AnalyticsEvent(Constants.Event.SelectItem.EVENT, mapOf(Constants.Event.SelectItem.PARAM_ITEM_NAME to itemName))
    class GenerateNickname(config: NicknameGeneratorService.Config) : AnalyticsEvent(Constants.Event.EVENT, config.toToAnalyticsParams()) {
        companion object : AnalyticsDictionaryMapper<NicknameGeneratorService.Config> {
            override fun NicknameGeneratorService.Config.toToAnalyticsParams() = mapOf(
                    "gender" to gender.value,
                    "alphabet" to alphabet.value,
                    "nickname_length" to nicknameLength.toString()
            )
        }
    }
    class SelectContact(contract: Contact) : AnalyticsEvent(Constants.Event.SelectItem.EVENT, mapOf(contract.toAnalyticsParams())) {
        companion object : AnalyticsPairMapper<Contact> {
            override fun Contact.toAnalyticsParams() = "contact" to when (this) {
                Contact.Instagram -> "Lnstagram"
                Contact.LinkedIn -> "LinkedIn"
                Contact.Mail -> "Mail"
            }
        }
    }
}



