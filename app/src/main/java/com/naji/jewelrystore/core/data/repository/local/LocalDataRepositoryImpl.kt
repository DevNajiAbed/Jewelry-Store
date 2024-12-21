package com.naji.jewelrystore.core.data.repository.local

import android.content.Context
import com.naji.jewelrystore.core.domain.repository.LocalDateRepository

class LocalDataRepositoryImpl : LocalDateRepository {
    private val prefsName = "prefs"
    private val userIdKey = "userId"

    override fun saveUserId(context: Context, userId: String) {
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putString(userIdKey, userId)
            .apply()
    }

    override fun getUserId(context: Context): String {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .getString(userIdKey, null) ?: ""
    }
}