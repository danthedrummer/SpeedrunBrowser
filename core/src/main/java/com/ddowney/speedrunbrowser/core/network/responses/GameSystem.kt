package com.ddowney.speedrunbrowser.core.network.responses

public data class GameSystem(
  public val platform: String,
  public val emulated: Boolean,
  public val region: String?,
)