package com.example.pokmomapp.util

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.pokmomapp.model.Pokemon
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class PokemonTask(val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()



    interface Callback {
        fun onPreExecute()
        fun onResult(pokemons: List<Pokemon>)
        fun onFailure(message: String)

    }

    fun execute(url: String) {
         callback.onPreExecute()


        executor.execute {

            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {

                val request = URL(url)
                urlConnection = request.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val statusCode: Int = urlConnection.responseCode



                if (statusCode > 400) {
                    throw IOException("Erro na comunicação com o servidor!")
                }



                stream = urlConnection.inputStream
                val jsonAsString =  stream.bufferedReader().use {
                    it.readText()
                }




                val pokemons = toPokemons(jsonAsString)



                handler.post {

                    callback.onResult(pokemons)
                }
            } catch (e: IOException) {
                val message = e.message ?: "Erro desconhecido"
                Log.e("Teste",message, e)

                handler.post {
                    callback.onFailure(message)

                }

            } finally {

                urlConnection?.disconnect()
                stream?.close()

            }


        }

    }

    private fun toPokemons(jsonAsString: String): List<Pokemon> {

        val pokemons = mutableListOf<Pokemon>()


        val jsonRoot = JSONObject(jsonAsString)
        val  jsonResult = jsonRoot.getJSONArray("results")
        for(i in 0 until jsonResult.length()){
            val jsonName = jsonResult.getJSONObject(i)
            val name =  jsonName.getString("name")
            val url = jsonName.getString("url")

            val start = 137 // h - matiz
            val end = 356 // h - matiz
            val diff = (end - start) / jsonResult.length()

            val hsv = floatArrayOf(
                start + (diff * i).toFloat(),
                100.0f,
                100.0f
            )

            pokemons.add(Pokemon(name,url, Color.HSVToColor(hsv).toLong()))


        }

        return pokemons

    }


}