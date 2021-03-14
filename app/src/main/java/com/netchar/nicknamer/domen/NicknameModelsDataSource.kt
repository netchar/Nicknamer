package com.netchar.nicknamer.domen

import com.netchar.nicknamer.domen.models.NicknameModel


interface NicknameModelsDataSource {
    fun getDataSource(): Map<String, NicknameModel>
}