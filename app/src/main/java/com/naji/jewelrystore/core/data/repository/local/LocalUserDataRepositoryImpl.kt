package com.naji.jewelrystore.core.data.repository.local

import android.app.Application
import android.content.Context
import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import javax.inject.Inject

class LocalUserDataRepositoryImpl @Inject constructor(
    private val application: Application
) : LocalUserDataRepository {

    override fun saveUserId(userId: String) {
        application.getSharedPreferences(LocalUserDataRepository.PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(LocalUserDataRepository.USER_ID_KEY, userId)
            .apply()
    }

    override fun getUserId(): String? {
        return application.getSharedPreferences(LocalUserDataRepository.PREFS_NAME, Context.MODE_PRIVATE)
            .getString(LocalUserDataRepository.USER_ID_KEY, null)/* ?: ""*/
    }

    override fun removeUserId() {
        application.getSharedPreferences(LocalUserDataRepository.PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(LocalUserDataRepository.USER_ID_KEY)
            .apply()
    }
}