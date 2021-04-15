package com.netchar.nicknamer.domen

import com.netchar.nicknamer.domen.models.NicknameModel

interface NicknameModelsProvider {
    fun getModels() : Map<String, NicknameModel>
}