package com.example.catbreeds.data

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject
import com.example.catbreeds.Breed

object CatApi {
    private const val BASE = "https://api.thecatapi.com/v1"
    private val client = AsyncHttpClient()

    interface BreedsCallback { fun onSuccess(list: List<Breed>); fun onError(t: Throwable) }

    fun fetchBreeds(cb: BreedsCallback) {
        val url = "$BASE/breeds"
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                try {
                    val arr: JSONArray = json!!.jsonArray
                    val out = mutableListOf<Breed>()
                    for (i in 0 until arr.length()) {
                        val o: JSONObject = arr.getJSONObject(i)
                        out.add(
                            Breed(
                                id = o.getString("id"),
                                name = o.getString("name"),
                                temperament = o.optString("temperament", "—"),
                                origin = o.optString("origin", "—")
                            )
                        )
                    }
                    cb.onSuccess(out)
                } catch (e: Exception) {
                    cb.onError(e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                cb.onError(throwable ?: RuntimeException("Request failed"))
            }
        })
    }

    interface ImageCallback { fun onResult(url: String?) }

    fun fetchBreedImage(breedId: String, cb: ImageCallback) {
        val url = "$BASE/images/search?breed_ids=$breedId&limit=1"
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                val arr = json!!.jsonArray
                val imageUrl = if (arr.length() > 0) arr.getJSONObject(0).optString("url", null) else null
                cb.onResult(imageUrl)
            }
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                cb.onResult(null)
            }
        })
    }
}