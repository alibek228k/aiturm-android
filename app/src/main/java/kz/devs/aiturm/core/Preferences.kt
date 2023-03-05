package kz.devs.aiturm.core

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private const val name = "aiturm.preferences"

    fun getSharedPreferences(context: Context): SharedPreferences?{
        return try {
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }catch (e: RuntimeException){
            null
        }
    }
}