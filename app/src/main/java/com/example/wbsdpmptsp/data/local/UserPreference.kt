package com.example.wbsdpmptsp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.TokenResponse
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
    private val onboardingCompletedKey = booleanPreferencesKey("onboarding_completed")

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

    suspend fun saveAccessToken(response: TokenResponse) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = response.data?.accessToken ?: ""
        }
    }

    fun getAccessToken(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[accessTokenKey]
    }

    fun getRefreshToken(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[refreshTokenKey]
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
            preferences.remove(refreshTokenKey)
            preferences.remove(userIdKey)
            preferences.remove(userNameKey)
            preferences.remove(userEmailKey)
            preferences.remove(userRoleKey)
        }
    }

    suspend fun saveOnboardingCompleted(isCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[onboardingCompletedKey] = isCompleted
        }
    }

    fun getOnboardingCompleted(): Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[onboardingCompletedKey] ?: false
    }
}