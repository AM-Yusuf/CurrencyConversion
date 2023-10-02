package com.example.currencyconversion.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Preference data store class to store app specific data
 * @param context get activity context
 */
class PrefDataStore(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

        // las api call timestamp
        val lastApiCallTime = longPreferencesKey("lastApiCallTime")
    }

    /**
     * set last api call timestamp
     * @param timeStamp
     */
    suspend fun setLastApiCallTime(timeStamp: Long) {
        context.dataStore.edit { preferences ->
            preferences[PrefDataStore.lastApiCallTime] = timeStamp
        }
    }

    // get the last api call time stamp. return 0 if not set yet
    val getLastApiCallTime: Flow<Long?> = context.dataStore.data.map {preferences ->
        preferences[lastApiCallTime] ?: 0
    }
}