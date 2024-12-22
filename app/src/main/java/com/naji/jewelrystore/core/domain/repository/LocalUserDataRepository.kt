package com.naji.jewelrystore.core.domain.repository

import android.content.Context

interface LocalUserDataRepository {
    companion object {
        const val PREFS_NAME = "userPrefs"
        const val USER_ID_KEY = "userId"
    }

    fun saveUserId(userId: String)

    fun getUserId(): String?

    fun removeUserId()
}