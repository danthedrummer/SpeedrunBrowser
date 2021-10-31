package com.ddowney.speedrunbrowser.core.storage

public interface Storage {

  /**
   * Store the given string [value] for the given [key]
   */
  public fun put(key: String, value: String)

  /**
   * Store the given [value] for the given [key].
   * The implementation should handle serialisation.
   */
  public fun put(key: String, value: Any)

  /**
   * Retrieves the [String] stored for the given [key] or null if it doesn't exist.
   */
  public fun get(key: String): String?

  /**
   * Retrieves the object stored for the given [key] or null if it doesn't exist.
   * The implementation should handle deserialisation.
   */
  public fun <T> get(key: String, clazz: Class<T>): T?

  /**
   * Removes the value for the given [key]
   */
  public fun remove(key: String)

  /**
   * Removes all values from storage
   */
  public fun clear()
}