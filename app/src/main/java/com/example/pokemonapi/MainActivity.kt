package com.example.pokemonapi

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonImage: ImageView
    private lateinit var pokemonName: TextView
    private lateinit var pokemonType: TextView
    private lateinit var pokemonWeight: TextView
    private lateinit var nextPokemonButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonImage = findViewById(R.id.pokemonImage)
        pokemonName = findViewById(R.id.pokemonName)
        pokemonType = findViewById(R.id.pokemonType)
        pokemonWeight = findViewById(R.id.pokemonWeight)
        nextPokemonButton = findViewById(R.id.nextPokemonButton)

        // Load a Pokemon when the app starts
        getPokemon()

        // Load another Pokemon when the button is clicked
        nextPokemonButton.setOnClickListener {
            getPokemon()
        }
    }

    private fun getPokemon() {

        // Choose a random Pokemon from the original 151
        val randomPokemon = (1..151).random()

        val client = AsyncHttpClient()

        client.get(
            "https://pokeapi.co/api/v2/pokemon/$randomPokemon",
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    val pokemon = json.jsonObject

                    // Get Pokemon information from the JSON
                    val name = pokemon.getString("name")
                    val weight = pokemon.getInt("weight")

                    val type = pokemon
                        .getJSONArray("types")
                        .getJSONObject(0)
                        .getJSONObject("type")
                        .getString("name")

                    val imageUrl = pokemon
                        .getJSONObject("sprites")
                        .getJSONObject("other")
                        .getJSONObject("official-artwork")
                        .getString("front_default")

                    // Display Pokemon name
                    pokemonName.text =
                        name.replaceFirstChar { it.uppercase() }

                    // Display Pokemon type
                    pokemonType.text =
                        "Type: ${type.replaceFirstChar { it.uppercase() }}"

                    // Display Pokemon weight
                    pokemonWeight.text =
                        "Weight: $weight"

                    // Display Pokemon image
                    Glide.with(this@MainActivity)
                        .load(imageUrl)
                        .into(pokemonImage)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String,
                    throwable: Throwable?
                ) {
                    pokemonName.text = "Failed to load Pokemon"
                }
            }
        )
    }
}