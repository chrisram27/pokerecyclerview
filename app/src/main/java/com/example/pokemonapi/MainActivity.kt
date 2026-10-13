package com.example.pokemonapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonRecyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonRecyclerView = findViewById(R.id.pokemonRecyclerView)

        pokemonAdapter = PokemonAdapter(pokemonList)

        pokemonRecyclerView.adapter = pokemonAdapter
        pokemonRecyclerView.layoutManager = LinearLayoutManager(this)

        loadPokemon()
    }

    private fun loadPokemon() {

        val client = AsyncHttpClient()

        // Load the first 20 Pokemon
        for (id in 1..20) {

            client.get(
                "https://pokeapi.co/api/v2/pokemon/$id",
                object : JsonHttpResponseHandler() {

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JSON
                    ) {
                        val pokemonJson = json.jsonObject

                        val name = pokemonJson.getString("name")

                        val weight = pokemonJson.getInt("weight")

                        val type = pokemonJson
                            .getJSONArray("types")
                            .getJSONObject(0)
                            .getJSONObject("type")
                            .getString("name")

                        val imageUrl = pokemonJson
                            .getJSONObject("sprites")
                            .getJSONObject("other")
                            .getJSONObject("official-artwork")
                            .getString("front_default")

                        val pokemon = Pokemon(
                            name,
                            type,
                            weight,
                            imageUrl
                        )

                        pokemonList.add(pokemon)

                        // Keep Pokemon in numerical order
                        pokemonList.sortBy {
                            getPokemonNumber(it.name)
                        }

                        pokemonAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String,
                        throwable: Throwable?
                    ) {
                        // Request failed
                    }
                }
            )
        }
    }

    private fun getPokemonNumber(name: String): Int {
        val pokemonNames = listOf(
            "bulbasaur",
            "ivysaur",
            "venusaur",
            "charmander",
            "charmeleon",
            "charizard",
            "squirtle",
            "wartortle",
            "blastoise",
            "caterpie",
            "metapod",
            "butterfree",
            "weedle",
            "kakuna",
            "beedrill",
            "pidgey",
            "pidgeotto",
            "pidgeot",
            "rattata",
            "raticate"
        )

        return pokemonNames.indexOf(name)
    }
}