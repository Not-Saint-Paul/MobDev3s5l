package com.example.mobdev_3s_5l

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plant(Timber.DebugTree())

        val recyclerView = findViewById<RecyclerView>(R.id.rView)
        val url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Timber.e(e, "Ошибка запроса")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body.string()
                Timber.d("JSON: $json") // ← посмотрите, нет ли null в массиве photo
                val wrapper = gson.fromJson(json, Wrapper::class.java)
                val photos = wrapper.photos.photo.filterNotNull() // ← защита!
                runOnUiThread {
                    recyclerView.adapter = Adapter(photos.filter {
                        !it.id.isNullOrEmpty() && !it.server.isNullOrEmpty() && !it.secret.isNullOrEmpty()
                    })
                }
            }
        })
    }
}