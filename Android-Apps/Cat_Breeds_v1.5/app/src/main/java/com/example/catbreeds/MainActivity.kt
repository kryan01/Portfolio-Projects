package com.example.catbreeds

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catbreeds.databinding.ActivityMainBinding
import com.example.catbreeds.ui.BreedsAdapter
import com.example.catbreeds.data.CatApi

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BreedsAdapter

    private val allBreeds: MutableList<Breed> = mutableListOf()
    private val shownBreeds: MutableList<Breed> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView
        adapter = BreedsAdapter(mutableListOf())
        binding.rvBreeds.layoutManager = LinearLayoutManager(this)
        binding.rvBreeds.adapter = adapter
        binding.rvBreeds.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        // Search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val q = (newText ?: "").trim().lowercase()
                val filtered = if (q.isEmpty()) allBreeds else allBreeds.filter {
                    it.name.lowercase().contains(q) ||
                            it.origin.lowercase().contains(q) ||
                            it.temperament.lowercase().contains(q)
                }
                shownBreeds.clear()
                shownBreeds.addAll(filtered)
                adapter.replaceAll(shownBreeds)
                return true
            }
        })


        loadBreeds()
    }

    private fun loadBreeds() {
        CatApi.fetchBreeds(object : CatApi.BreedsCallback {
            override fun onSuccess(list: List<Breed>) {
                Log.d("Main", "Loaded ${list.size} breeds")
                Toast.makeText(this@MainActivity, "Loaded ${list.size} breeds", Toast.LENGTH_SHORT).show()

                allBreeds.clear()
                allBreeds.addAll(list)

                shownBreeds.clear()
                shownBreeds.addAll(allBreeds)
                adapter.replaceAll(shownBreeds)

                // fetch one image per breed
                allBreeds.forEach { breed ->
                    CatApi.fetchBreedImage(breed.id, object : CatApi.ImageCallback {
                        override fun onResult(url: String?) {
                            breed.imageUrl = url
                            val idx = shownBreeds.indexOf(breed)
                            if (idx != -1) runOnUiThread { adapter.notifyItemChanged(idx) }
                        }
                    })
                }
            }

            override fun onError(t: Throwable) {
                Log.e("Main", "Failed to load breeds", t)
                Toast.makeText(this@MainActivity, "Failed to load breeds", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
