package com.ddowney.speedrunbrowser.injection

import android.app.Application
import com.ddowney.speedrunbrowser.networking.CategoriesProvider
import com.ddowney.speedrunbrowser.networking.GameProvider
import com.ddowney.speedrunbrowser.networking.RunProvider
import com.ddowney.speedrunbrowser.networking.UserProvider
import com.ddowney.speedrunbrowser.storage.Storage
import dagger.Component
import javax.inject.Singleton

//@Singleton
//@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class])
//interface AppComponent {
//
//  fun inject(application: Application)
//
//  fun categoriesProvider(): CategoriesProvider
//  fun gameProvider(): GameProvider
//  fun runProvider(): RunProvider
//  fun userProvider(): UserProvider
//
//  fun storage(): Storage
//
//}