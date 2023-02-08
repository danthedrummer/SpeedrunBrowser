package com.ddowney.speedrunbrowser.core.network.responses

public sealed class EmbeddableProperty<T> {

  public data class NotEmbedded<T>(val ids: List<String>) : EmbeddableProperty<T>()

  public data class Embedded<T>(val data: List<T>): EmbeddableProperty<T>()
}