package com.example.lab5_kayla_ryan

// imports
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // view references
    private lateinit var ivApod: ImageView      // film image
    private lateinit var tvTitle: TextView      // film title
    private lateinit var tvDate: TextView       // film release date
    private lateinit var tvExplanation: TextView    // film synopsis
    private lateinit var tvStatus: TextView     // status message (Loading... / Failed)
    private lateinit var btnNew: Button         // "Random Film" button

    // API endpoint: Ghibli films
    // Returns an array of Studio Ghibli films with fields like
    // title, release_date, description, movie_banner, image, etc.
    private val GHIBLI_URL = "https://ghibliapi.vercel.app/films"

    private var isLoading = false

    // entry point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // link to layout
        setContentView(R.layout.activity_main)

        // find all views from layout by ids
        ivApod = findViewById(R.id.ivApod)
        tvTitle = findViewById(R.id.tvTitle)
        tvDate = findViewById(R.id.tvDate)
        tvExplanation = findViewById(R.id.tvExplanation)
        tvStatus = findViewById(R.id.tvStatus)
        btnNew = findViewById(R.id.btnNew)

        // when user taps the button, another film is fetched
        btnNew.setOnClickListener { fetchRandomFilm() }

        // initial load
        fetchRandomFilm()
    }

    // fetch random film from the Ghibli API and updates the UI
    private fun fetchRandomFilm() {
        // prevent double-taps
        if (isLoading) return
        isLoading = true
        btnNew.isEnabled = false

        // show loading state and clear old content
        tvStatus.text = "Loading…"
        tvStatus.visibility = View.VISIBLE
        tvTitle.text = ""
        tvDate.text = ""
        tvExplanation.text = ""
        ivApod.setImageDrawable(null)

        // create a client (20s timeout)
        val client = AsyncHttpClient().apply { setTimeout(20_000) }

        // make the HTTP GET request
        client.get(GHIBLI_URL, object : JsonHttpResponseHandler() {

            // success path: get JSON
            // json.jsonArray is the array of films
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val arr: JSONArray = json.jsonArray  // list of films
                    if (arr.length() == 0) throw IllegalStateException("Empty response")

                    // pick random index in the array
                    val idx = Random.nextInt(arr.length())
                    val obj = arr.getJSONObject(idx)

                    // extract necessary fields
                    val title = obj.optString("title", "Untitled")
                    val release = obj.optString("release_date", "")
                    val description = obj.optString("description", "")

                    // prefer movie_banner (wide banner); fall back to image (poster)
                    val banner = obj.optString("movie_banner", null)
                    val image = if (!banner.isNullOrBlank()) banner else obj.optString("image", null)

                    // apply data to UI
                    runOnUiThread {
                        tvTitle.text = title
                        tvDate.text = release
                        tvExplanation.text = if (description.isBlank()) "(No description provided)" else description

                        // if url received, load image
                        if (!image.isNullOrBlank()) {
                            Glide.with(this@MainActivity).load(image).into(ivApod)
                        }

                        // hide status, re-enable button
                        tvStatus.text = ""
                        tvStatus.visibility = View.GONE
                        isLoading = false
                        btnNew.isEnabled = true
                    }
                } catch (_: Exception) {
                    // if parsing fails, show message
                    runOnUiThread {
                        tvStatus.text = "Parse error"
                        tvStatus.visibility = View.VISIBLE
                        isLoading = false
                        btnNew.isEnabled = true
                    }
                }
            }

            // failure path: network/server error
            // show message and unlock UI
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?) {
                runOnUiThread {
                    tvStatus.text = "Failed: $statusCode"
                    tvStatus.visibility = View.VISIBLE
                    isLoading = false
                    btnNew.isEnabled = true
                }
            }
        })
    }
}