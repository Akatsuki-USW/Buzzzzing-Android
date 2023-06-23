package com.onewx2m.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.onewx2m.local.datastore.SecurityPreferences
import com.onewx2m.local.datastore.generateSecurityPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import tech.thdev.useful.encrypted.data.store.preferences.security.generateUsefulSecurity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideEncryptedDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { applicationContext.preferencesDataStoreFile("security-preference") }
    )

    @Provides
    @Singleton
    fun provideSecurityPreference(
        dataStore: DataStore<Preferences>
    ): SecurityPreferences = dataStore.generateSecurityPreferences(generateUsefulSecurity())
}