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

class ViewGroupSelectionMapper<TRadioValue>(vararg pairs: Pair<Int, TRadioValue>) {
    private val idsToValueMap = mapOf(*pairs)
    operator fun get(radioItemId: Int): TRadioValue = idsToValueMap.getValue(radioItemId)
    operator fun get(gender: TRadioValue): Int = idsToValueMap.keys.first { gender == idsToValueMap[it] }
}