package com.ddowney.speedrunbrowser.core.network.responses

public data class Player(
  public val rel: String,
  public val id: String?,
  public val name: String?,
  public val uri: String,
)