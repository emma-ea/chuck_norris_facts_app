package com.rootedge.chuchnorrisfacts.chucknorrisfacts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var URL = "https://api.icndb.com/jokes/random"
    var okHttpClient : OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.elevation = 0.0f
        setContentView(R.layout.activity_main)

        button_nextfact.setOnClickListener{
            loadRandomFact()
        }
    }

    private fun loadRandomFact(){
       runOnUiThread{
           progressbar_loadingAPI.visibility = View.VISIBLE
       }

       var request : Request = Request.Builder().url(URL).build()
       okHttpClient.newCall(request).enqueue(object: Callback {
           override fun onFailure(call: Call?, e: IOException?){

           }

           override fun onResponse(call: Call?, response: Response?) {
               val json = response?.body()?.string()
               // we get this joke from the web
               val txt = (JSONObject(json).getJSONObject("value").get("joke")).toString()

               // we update the ui from the ui thread
                runOnUiThread{
                    progressbar_loadingAPI.visibility = View.GONE
                    // we used html class to decode entities
                    textview_facts.text = Html.fromHtml(txt)
                    button_nextfact.text = "Next Fact"
                }
           }
       })
   }
}
