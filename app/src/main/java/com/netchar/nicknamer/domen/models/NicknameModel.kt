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

package com.netchar.nicknamer.domen.models

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class NicknameModel(
        private val breakable: HashSet<String>,
        private val endingContinuations: HashMap<String, ArrayList<CharContinuation>>
) {
    companion object {
        private const val MASK = "$$"
        private val random: Random = Random()
    }

    fun generateNickname(length: Int): Nickname {
        var word = MASK

        while (true) {
            val endingStart: Int = word.length - MASK.length
            val ending = word.substring(endingStart, word.length)

            if (endingStart >= length && breakable.contains(ending)) {
                break
            }

            val continuations = endingContinuations[ending] ?: break

            val chance: Double = random.nextDouble()
            for (continuation in continuations) {
                if (chance < continuation.probability) {
                    word += continuation.char
                    break
                }
            }
        }
        return Nickname(word.replace("$", ""))
    }
}