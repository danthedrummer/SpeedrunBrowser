package com.ddowney.speedrunbrowser.core.network.responses

public open class BulkGame(
  public val id: String,
  public val names: GameNames,
  public val abbreviation: String,
  public val weblink: String,
)