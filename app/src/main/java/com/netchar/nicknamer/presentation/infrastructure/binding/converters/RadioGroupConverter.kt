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

package com.netchar.nicknamer.presentation.infrastructure.binding.converters

import androidx.databinding.BindingConversion
import androidx.databinding.InverseMethod
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.service.NicknameGeneratorService.*

object GenderConverter {
    @JvmStatic
    @InverseMethod("buttonIdToGender")
    fun genderToButtonId(gender: Config.Gender) : Int {
        return when(gender) {
            Config.Gender.MALE -> R.id.main_radio_btn_male
            Config.Gender.FEMALE -> R.id.main_radio_btn_female
        }
    }

    @JvmStatic
    fun buttonIdToGender(id: Int) : Config.Gender {
        return when (id) {
            R.id.main_radio_btn_male -> Config.Gender.MALE
            R.id.main_radio_btn_female -> Config.Gender.FEMALE
            else -> error("No such id specified: $id")
        }
    }
}

object AlphabetConverter {
    @JvmStatic
    @InverseMethod("buttonIdToAlphabet")
    fun alphabetToButtonId(gender: Config.Alphabet) : Int {
        return when(gender) {
            Config.Alphabet.CYRILLIC -> R.id.main_radio_btn_cyrillic
            Config.Alphabet.LATIN -> R.id.main_radio_btn_latin
        }
    }

    @JvmStatic
    fun buttonIdToAlphabet(id: Int) : Config.Alphabet {
        return when (id) {
            R.id.main_radio_btn_cyrillic -> Config.Alphabet.CYRILLIC
            R.id.main_radio_btn_latin -> Config.Alphabet.LATIN
            else -> error("No such id specified: $id")
        }
    }
}