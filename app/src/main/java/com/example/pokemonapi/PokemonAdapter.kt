package com.example.pokemonapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(
    private val pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image: ImageView =
            itemView.findViewById(R.id.itemPokemonImage)

        val name: TextView =
            itemView.findViewById(R.id.itemPokemonName)

        val type: TextView =
            itemView.findViewById(R.id.itemPokemonType)

        val weight: TextView =
            itemView.findViewById(R.id.itemPokemonWeight)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)

        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val pokemon = pokemonList[position]

        holder.name.text =
            pokemon.name.replaceFirstChar { it.uppercase() }

        holder.type.text =
            "Type: ${pokemon.type.replaceFirstChar { it.uppercase() }}"

        holder.weight.text =
            "Weight: ${pokemon.weight}"

        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}