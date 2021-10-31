package com.ddowney.speedrunbrowser.injection.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserAgent

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl