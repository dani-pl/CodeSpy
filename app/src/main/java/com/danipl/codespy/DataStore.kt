package com.danipl.codespy

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class CodeSpyDataStore {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
}