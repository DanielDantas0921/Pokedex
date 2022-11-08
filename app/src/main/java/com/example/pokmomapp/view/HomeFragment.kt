package com.example.pokmomapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokmomapp.R
import com.example.pokmomapp.model.Pokemon
import com.example.pokmomapp.util.PokemonTask
import com.xwray.groupie.GroupieAdapter


class HomeFragment : Fragment(),PokemonTask.Callback {

    val adapter = GroupieAdapter()
    val listcategory = mutableListOf<Pokemon>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_home)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())




       PokemonTask(this).execute( "https://pokeapi.co/api/v2/pokemon")
        PokemonTask(this).execute( "https://pokeapi.co/api/v2/pokemon?offset=20&limit=200")

        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { item, view ->
            val bundle  = Bundle()
            val pokemonName = (item as PokemonItem).pokemom.name
            bundle.putString("nome_do_pokemon",pokemonName)
            val pokemonUrl = (item as PokemonItem).pokemom.url
            bundle.putString("pokemon_url", pokemonUrl)
            findNavController().navigate(R.id.action_nav_home_to_home_Pokemon_Fragment, bundle)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onPreExecute() {
    }

    override fun onResult(pokemons: List<Pokemon>) {


//        listcategory.add(Pokemom("inácio"))
//        listcategory.add(Pokemom("bolsonaro_de_calcinha"))

        val formatado = pokemons.map { PokemonItem(it) }
        adapter.addAll(formatado)
        adapter.notifyDataSetChanged()

    }

    override fun onFailure(message: String) {
    }


}