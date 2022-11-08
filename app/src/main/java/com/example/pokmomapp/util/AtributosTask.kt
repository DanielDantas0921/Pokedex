package com.example.pokmomapp.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.pokmomapp.model.PokemonAtributos
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class AtributosTask(val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()



    interface Callback {
        fun onPreExecute()
        fun onResult(pokemons: List<PokemonAtributos>)
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




                val pokemons = toAtributosPokemon(jsonAsString)



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

    private fun toAtributosPokemon(jsonAsString: String): List<PokemonAtributos> {


        val pokemon_string = mutableListOf<String>()

        val jsonRoot = JSONObject(jsonAsString)

        val getJsonSprites = jsonRoot.getJSONObject("sprites")

//        Log.i("pokemon_object", getJsonSprites.toString())

        val getFrontShiny = getJsonSprites.getString("front_shiny")

        pokemon_string.add(getFrontShiny)


        val getStats = jsonRoot.getJSONArray("stats")




        for(j in 0 until  getStats.length()){
            val objectJason =  getStats.getJSONObject(j)
            when(j){
                0 -> {
                    val hp = objectJason.getInt("base_stat")
                    pokemon_string.add(hp.toString())
                }
                1 -> {
                    val attack = objectJason.getInt("base_stat")
                    pokemon_string.add(attack.toString())
                }
                2 -> {
                    val defense = objectJason.getInt("base_stat")
                    pokemon_string.add(defense.toString())
                }
                3 ->{
                    val special_attack = objectJason.getInt("base_stat")
                    pokemon_string.add(special_attack.toString())
                }
                4 -> {
                    val special_defense = objectJason.getInt("base_stat")
                    pokemon_string.add(special_defense.toString())
                }
                5 -> {
                    val speed = objectJason.getInt("base_stat")
                    pokemon_string.add(speed.toString())
                }

            }






        }

        val pokemomFormat = pokemon_string.map {
            PokemonAtributos(pokemon_string[0], pokemon_string[1].toInt(),pokemon_string[2].
            toInt(),pokemon_string[3].toInt(),pokemon_string[4].toInt(),pokemon_string[5].toInt(),pokemon_string[6].toInt())
        }

     return pokemomFormat



    }

}