package com.netchar.nicknamer.domen

import com.netchar.nicknamer.domen.models.NicknameModel

interface PersonNicknameModelsProvider {
    fun getModels() : Map<String, NicknameModel>
}