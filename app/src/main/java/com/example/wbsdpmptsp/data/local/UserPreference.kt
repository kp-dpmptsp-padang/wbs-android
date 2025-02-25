package com.example.wbsdpmptsp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreference(private val context: Context) {
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val userIdKey = stringPreferencesKey("user_id")
    private val userNameKey = stringPreferencesKey("user_name")
    private val userEmailKey = stringPreferencesKey("user_email")
    private val userRoleKey = stringPreferencesKey("user_role")

    suspend fun saveUser(response: AuthResponse) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = response.data?.accessToken ?: ""
            preferences[refreshTokenKey] = response.data?.refreshToken ?: ""
            preferences[userIdKey] = response.data?.user?.id.toString()
            preferences[userNameKey] = response.data?.user?.name ?: ""
            preferences[userEmailKey] = response.data?.user?.email ?: ""
            preferences[userRoleKey] = response.data?.user?.role ?: ""
        }
    }

    fun getUser(): Flow<User?> = context.dataStore.data.map { preferences ->
        User(
            role = preferences[userRoleKey] ?: "",
            name = preferences[userNameKey] ?: "",
            id = preferences[userIdKey]?.toIntOrNull(),
            email = preferences[userEmailKey] ?: ""
        )
    }

    fun getAccessToken(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[accessTokenKey]
    }

    fun getRefreshToken(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[refreshTokenKey]
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}