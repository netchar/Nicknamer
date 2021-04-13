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

package com.netchar.nicknamer.data

import android.content.Context
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.models.CharContinuation
import com.netchar.nicknamer.domen.models.NicknameModel
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

interface NicknameModelsDataSource {
    fun getModels() : Map<String, NicknameModel>
}

class NicknameModelsDataSourceImpl(
        private val context: Context
) : NicknameModelsDataSource {
    private val models = mutableMapOf<String, NicknameModel>()

    override fun getModels(): Map<String, NicknameModel> {
        if (models.isNotEmpty()) {
            return models
        }

        val jsonObject = readRawJson(context)
        for (key in jsonObject.keys()) {
            models[key] = parse(jsonObject.getJSONObject(key))
        }

        return models
    }

    @Throws(JSONException::class, IOException::class)
    private fun readRawJson(context: Context): JSONObject {
        return context.resources.openRawResource(R.raw.models)
            .bufferedReader(Charsets.UTF_8)
            .use { bufferedReader ->
                JSONObject(bufferedReader.readText())
            }
    }

    @Throws(JSONException::class)
    private fun parse(modelObject: JSONObject): NicknameModel {
        // Read breakable sequences.
        val breakableNode = modelObject.getJSONArray("breakable")
        val breakableArray = Array(breakableNode.length()) { i -> breakableNode.getString(i) }

        // Read probabilities.
        val probabilityNode = modelObject.getJSONObject("p")
        val probabilities = hashMapOf<String, ArrayList<CharContinuation>>()

        for (key in probabilityNode.keys()) {
            val continuation = probabilities.getOrPut(key) { arrayListOf() }

            val continuationsNodes = probabilityNode.getJSONArray(key)
            val continuationsArray = Array(continuationsNodes.length()) { i -> continuationsNodes.getJSONArray(i) }
            val charsArray = continuationsArray.map { CharContinuation(it.getString(0).first(), it.getDouble(1)) }
            continuation.addAll(charsArray)
        }
        return NicknameModel(breakableArray.toHashSet(), probabilities)
    }
}