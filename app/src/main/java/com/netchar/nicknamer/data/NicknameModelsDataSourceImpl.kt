package com.netchar.nicknamer.data

import android.content.Context
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.models.CharContinuation
import com.netchar.nicknamer.domen.NicknameModelsDataSource
import com.netchar.nicknamer.domen.models.NicknameModel
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class NicknameModelsDataSourceImpl(
        private val context: Context
) : NicknameModelsDataSource {

    override fun getDataSource(): Map<String, NicknameModel> {
        val source = mutableMapOf<String, NicknameModel>()
        val jsonObject = readRawJson(context)
        for (key in jsonObject.keys()) {
            source[key] = parse(jsonObject.getJSONObject(key))
        }

        return source
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