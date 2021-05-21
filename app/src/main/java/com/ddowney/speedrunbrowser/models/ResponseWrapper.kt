package com.ddowney.speedrunbrowser.models

/**
 * Wrapper model for responses from the Speedrun API. This is to accommodate for
 * a "data" key they have at the root of all of their JSON responses.
 *
 * The type of this data could be a Run, a Game, etc.
 */
data class ListRoot<out T>(val data: List<T>)

data class ObjectRoot<out T>(val data: T)