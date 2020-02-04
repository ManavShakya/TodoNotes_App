package com.mindorks.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mindorks.todonotes.utils.AppConsant.FULL_NAME
import com.mindorks.todonotes.utils.AppConsant.IS_LOGGED_IN
import com.mindorks.todonotes.utils.AppConsant.SHARED_PREFERENCES_NAME
import com.mindorks.todonotes.R
import com.mindorks.todonotes.utils.StoreSession

class LoginActivity : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var editTextFullName: EditText
    private lateinit var editTextUserName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonLogin = findViewById(R.id.button)
        editTextFullName = findViewById(R.id.EditTextFullName)
        editTextUserName = findViewById(R.id.EditTextUserName)
        setupSharedPreference()

        buttonLogin.setOnClickListener(View.OnClickListener {
            val fullName = editTextFullName.text.toString()
            var userName = editTextUserName.text.toString()
            if(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {
                val intent: Intent = Intent(this, MyNotesActivity::class.java)
                intent.putExtra(FULL_NAME, fullName)
                startActivity(intent)
                //successful login
                saveLoginStatus()
                saveFullName(fullName)
            }
            else
                Toast.makeText(this,"No text Entered",Toast.LENGTH_LONG).show()

        })

    }

    private fun saveFullName(fullName: String) {
        StoreSession.write(FULL_NAME,fullName)
    }

    private fun saveLoginStatus() {
        StoreSession.write(IS_LOGGED_IN,true)
    }

    private fun setupSharedPreference() {
        StoreSession.init(this)
    }
}
