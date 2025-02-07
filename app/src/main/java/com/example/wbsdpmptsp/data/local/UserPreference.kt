package com.example.wbsdpmptsp.data.local

import android.content.Context
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
            preferences[accessTokenKey] = response.accessToken ?: ""
            preferences[refreshTokenKey] = response.refreshToken ?: ""
            preferences[userIdKey] = response.user?.id.toString()
            preferences[userNameKey] = response.user?.name ?: ""
            preferences[userEmailKey] = response.user?.email ?: ""
            preferences[userRoleKey] = response.user?.role ?: ""
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

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}