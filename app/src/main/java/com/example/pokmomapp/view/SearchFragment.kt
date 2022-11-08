package com.example.pokmomapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokmomapp.R

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var btn_search: Button
    private lateinit var et_search: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_search = view.findViewById(R.id.btn_pesquisar_search)
        et_search = view.findViewById(R.id.et_search)
        activity?.findViewById<Toolbar>(R.id.toolbar)?.title = "Pesquisar Pokémon"

        btn_search.setOnClickListener {
            val bundle  = Bundle()
            val nome_do_pokemon = et_search.text.toString()
            Log.i("nome_do_pokemon", nome_do_pokemon)
            bundle.putString("nome_do_pokemon1",nome_do_pokemon)

            val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/$nome_do_pokemon"
            bundle.putString("pokemon_url1", pokemonUrl)

            findNavController().navigate(R.id.action_nav_search_to_search_PokemonFragment, bundle)

        }


    }

}