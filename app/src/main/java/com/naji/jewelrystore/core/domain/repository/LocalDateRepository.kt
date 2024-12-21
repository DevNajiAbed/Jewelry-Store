package com.naji.jewelrystore.core.domain.repository

import android.content.Context

interface LocalDateRepository {
    fun saveUserId(context: Context, userId: String)

    fun getUserId(context: Context): String
}