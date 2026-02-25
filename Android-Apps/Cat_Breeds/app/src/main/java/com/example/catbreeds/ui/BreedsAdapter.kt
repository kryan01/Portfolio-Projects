package com.example.catbreeds.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.catbreeds.databinding.ItemBreedBinding
import com.example.catbreeds.Breed

class BreedsAdapter(
    private val items: MutableList<Breed>
) : RecyclerView.Adapter<BreedsAdapter.BreedVH>() {

    // Each visible row/card
    inner class BreedVH(val binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root)

    // Inflate item_breed.xml for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedVH {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedVH(binding)
    }

    // Bind data to the row
    override fun onBindViewHolder(holder: BreedVH, position: Int) {
        val b = items[position]
        holder.binding.apply {
            tvName.text = b.name
            tvTemperament.text = b.temperament
            tvOrigin.text = b.origin
            ivBreed.load(b.imageUrl) { crossfade(true) }
            root.setOnClickListener {
                Toast.makeText(root.context, b.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    // When data changes
    fun replaceAll(newItems: List<Breed>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
