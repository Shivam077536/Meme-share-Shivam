   package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

   class MainActivity : AppCompatActivity() {
       var currentImageUrl: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        // Instantiate the RequestQueue.
        progressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(   // json object se image load krwami ha
             Request.Method.GET, url,null,
            Response.Listener{ response ->
                currentImageUrl =response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility =View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageview)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
    val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain "  // jis app ne hoga ki ham yeah sanb recieve krte ha unka option aayega
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this cool meme$currentImageUrl")
    val chooser = Intent.createChooser(intent,"Share this using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}