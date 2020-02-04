package com.mindorks.todonotes.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.mindorks.todonotes.utils.AppConsant.SHARED_PREFERENCES_NAME

object StoreSession {
    private  var sharedPreferences:SharedPreferences? = null

    fun init(context: Context){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,MODE_PRIVATE)
        }
    }

    fun write(key: String,value: Boolean){
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putBoolean(key,value)
        editor?.apply()
    }

    fun read(key: String): Boolean?{
        return sharedPreferences?.getBoolean(key,false)
    }

    fun write(key: String,value: String){
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putString(key,value)
        editor?.apply()
    }

    fun readString(key: String): String?{
        return sharedPreferences?.getString(key,"")
    }
}