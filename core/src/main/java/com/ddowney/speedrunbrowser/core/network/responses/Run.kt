package com.ddowney.speedrunbrowser.core.network.responses

public data class Run(
  public val id: String,
  public val weblink: String,
  public val game: String,
  public val level: String?,
  public val category: String,
  public val videos: RunVideos,
  public val comment: String?,
  public val status: RunStatus,
  public val players: List<Player>,
  public val date: String?,
  public val submitted: String?,
  public val times: Times,
  public val system: GameSystem,
  public val splits: Splits,
  public val values: Map<String, String>,
)