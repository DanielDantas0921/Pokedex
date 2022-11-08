package com.example.pokmomapp.view

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.pokmomapp.R
import com.example.pokmomapp.model.Pokemon
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class PokemonItem(val pokemom: Pokemon): Item<PokemonItem.PokemomViewHolder>() {

    class PokemomViewHolder(view: View): GroupieViewHolder(view)

    override fun createViewHolder(itemView: View): PokemomViewHolder {
        return PokemomViewHolder(itemView)
    }

    override fun bind(viewHolder: PokemomViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.tv_item_pokemom).text = pokemom.name
      viewHolder.itemView.findViewById<LinearLayout>(R.id.container_item_pokemom).setBackgroundColor(pokemom.bgColor.toInt())
    }

    override fun getLayout(): Int {
        return R.layout.item_pokemom
    }


}