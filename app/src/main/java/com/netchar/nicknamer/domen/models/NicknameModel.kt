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

import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class NicknameModel(
        private val breakable: HashSet<String>,
        private val endings: HashMap<String, ArrayList<CharContinuation>>
) {
    companion object {
        private const val ENDING_MASK = "$$"
        private val random: Random = Random()
    }

    fun generateNickname(length: Int): Nickname {
        val word = StringBuilder(ENDING_MASK)

        while (true) {
            val endingStartIndex: Int = word.length - ENDING_MASK.length
            val wordEnding = word.substring(endingStartIndex, word.length)
            val possibleEndings = endings[wordEnding]

            if (endingStartIndex >= length && breakable.contains(wordEnding) || possibleEndings == null) {
                break
            }

            val nextEndingChar = getNextEndingChar(possibleEndings)

            if (nextEndingChar != null) {
                word.append(nextEndingChar)
            }
        }

        val result = word.toString().replace("$", "")

        if (result.length == length) {
            return Nickname(result)
        }

        return generateNickname(length)
    }

    private fun getNextEndingChar(endingContinuations: ArrayList<CharContinuation>): Char? {
        val foundEnding = endingContinuations.find { it.probability > random.nextDouble() }
        return foundEnding?.char
    }
}