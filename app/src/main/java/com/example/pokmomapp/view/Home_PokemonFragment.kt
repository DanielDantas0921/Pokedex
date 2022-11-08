package com.example.pokmomapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.pokmomapp.R
import com.example.pokmomapp.model.PokemonAtributos
import com.example.pokmomapp.util.AtributosTask
import com.squareup.picasso.Picasso

class Home_PokemonFragment: Fragment(R.layout.fragment_home_pokemon), AtributosTask.Callback {

    private lateinit var img: ImageView
    private lateinit var tv_hp: TextView
    private lateinit var tv_attack: TextView
    private lateinit var tv_defense: TextView
    private lateinit var tv_special_attack: TextView
    private lateinit var tv_special_defense: TextView
    private lateinit var tv_speed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pokemonName = arguments?.getString("nome_do_pokemon")!!
        val pokemonUrl = arguments?.getString("pokemon_url")!!

        view.findViewById<TextView>(R.id.tv_nome_pokemom_home_pokemom).text = getString(R.string.nome_do_pokemom, pokemonName)
        activity?.findViewById<Toolbar>(R.id.toolbar)?.title = pokemonName

        AtributosTask(this).execute(pokemonUrl)
        img = view.findViewById(R.id.img_pokemon_home_pokemon)
        tv_hp = view.findViewById(R.id.tv_hp_pokemom_home_pokemom)
        tv_attack = view.findViewById(R.id.tv_attack_pokemom_home_pokemom)
        tv_defense = view.findViewById(R.id.tv_defense_pokemom_home_pokemom)
        tv_special_attack = view.findViewById(R.id.tv_special_attack_pokemom_home_pokemom)
        tv_special_defense = view.findViewById(R.id.tv_special_defense_pokemom_home_pokemom)
        tv_speed = view.findViewById(R.id.tv_speed_pokemom_home_pokemom)
    }

    override fun onPreExecute() {
    }

    override fun onResult(pokemons: List<PokemonAtributos>) {

        Picasso.get().load(pokemons[0].img_url).into(img)
        tv_hp.text = getString(R.string.hp_do_pokemom, pokemons[0].hp_value)
        tv_attack.text = getString(R.string.attack_do_pokemom, pokemons[0].attack_value)
        tv_defense.text = getString(R.string.defense_do_pokemom, pokemons[0].defense_value)
        tv_special_attack.text = getString(R.string.special_attack_do_pokemom, pokemons[0].special_attack_value)
        tv_special_defense.text = getString(R.string.special_defense_do_pokemom, pokemons[0].special_defense_value)
        tv_speed.text = getString(R.string.speed_do_pokemom, pokemons[0].speed)



    }

    override fun onFailure(message: String) {

    }


}