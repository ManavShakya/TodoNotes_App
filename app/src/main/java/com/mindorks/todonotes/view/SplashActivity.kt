package com.mindorks.todonotes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mindorks.todonotes.utils.AppConsant.IS_LOGGED_IN
import com.mindorks.todonotes.R
import com.mindorks.todonotes.onboarding.OnBoardingActivity
import com.mindorks.todonotes.utils.AppConsant.ON_BOARDED_SUCCESSFULLY
import com.mindorks.todonotes.utils.StoreSession

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreferences()
        chechLoginStatus()
        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("SplashActivity", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d("SplashActivity", token)
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }

    private fun setupSharedPreferences() {
        StoreSession.init(this)
    }

    private fun chechLoginStatus() {
        var isLoggedIn: Boolean? = StoreSession.read(IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(ON_BOARDED_SUCCESSFULLY)
        if(isLoggedIn!!){
            //open my notes activity
            val intent = Intent(this, MyNotesActivity::class.java)
            startActivity(intent)
        }else {
            //open login
            if (isBoardingSuccess!!) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else{
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)

            }
        }
    }
}
