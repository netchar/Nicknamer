package com.netchar.nicknamer.presentation.infrastructure.analytics

interface AnalyticsPairMapper<TModel> {
    fun TModel.toAnalyticsParams(): Pair<String, String>
}

interface AnalyticsDictionaryMapper<TModel> {
    fun TModel.toToAnalyticsParams(): Map<String, String>
}