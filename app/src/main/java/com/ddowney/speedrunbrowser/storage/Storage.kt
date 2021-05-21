package com.ddowney.speedrunbrowser.storage

interface Storage {

  /**
   * Store the given string [value] for the given [key]
   */
  fun put(key: String, value: String)

  /**
   * Store the given [value] for the given [key].
   * The implementation should handle serialisation.
   */
  fun put(key: String, value: Any)

  /**
   * Retrieves the [String] stored for the given [key] or null if it doesn't exist.
   */
  fun get(key: String): String?

  /**
   * Retrieves the object stored for the given [key] or null if it doesn't exist.
   * The implementation should handle deserialisation.
   */
  fun <T> get(key: String, clazz: Class<T>): T?

  /**
   * Removes the value for the given [key]
   */
  fun remove(key: String)

  /**
   * Removes all values from storage
   */
  fun clear()
}