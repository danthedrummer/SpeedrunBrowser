package com.ddowney.speedrunbrowser.core.serialization

/**
 * Describes a field which can be a [List] of [String] or [T]
 *
 * This is necessary for the speedrun.com API as they allow "embedded" properties which mean we can't
 * statically type the response objects returned in network requests.
 */
public sealed interface Embed<T> {

  public data class Populated<T>(
    val data: List<T>,
  ): Embed<T>

  public data class Raw<T>(
    val data: List<String>,
  ): Embed<T>
}