package com.mindorks.todonotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mindorks.todonotes.R
import com.mindorks.todonotes.utils.AppConsant.DESCRIPTION
import com.mindorks.todonotes.utils.AppConsant.TITLE

class DetailActivity : AppCompatActivity() {
    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setIntentData()
    }

    private fun setIntentData() {
        val intent = intent
        textViewTitle.text = intent.getStringExtra(TITLE)
        textViewDescription.text = intent.getStringExtra(DESCRIPTION)
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }
}
