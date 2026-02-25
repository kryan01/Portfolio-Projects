package com.example.catbreeds

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    var imageUrl: String? = null
)
