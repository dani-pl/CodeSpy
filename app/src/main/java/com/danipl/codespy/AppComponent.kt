package com.danipl.codespy

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@EntryPoint
interface AppComponent {

    @get:ApplicationContext
    val appContext: Context
}