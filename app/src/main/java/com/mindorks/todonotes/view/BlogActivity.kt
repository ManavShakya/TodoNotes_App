package com.mindorks.todonotes.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.mindorks.todonotes.R
import com.mindorks.todonotes.adapter.BlogAdapter
import com.mindorks.todonotes.model.JsonResponse
import kotlinx.android.synthetic.main.activity_blog.*
import org.json.JSONArray


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(JsonResponse::class.java,object : ParsedRequestListener<JsonResponse>{
                override fun onResponse(response: JsonResponse?) {
                    setupRecyclerView(response)
                }

                override fun onError(anError: ANError?) {
                    Log.d("BlogActivity",anError?.localizedMessage)
                }

            })
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setupRecyclerView(response: JsonResponse?){
        val blogAdapter = BlogAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }
}
