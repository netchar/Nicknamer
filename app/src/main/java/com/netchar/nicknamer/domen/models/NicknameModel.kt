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

            val nextContinuation = continuations.find { it.probability > random.nextDouble() }

            if (nextContinuation != null) {
                word += nextContinuation.char
            }
        }
        return Nickname(word.replace("$", ""))
    }
}