package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class Leaderboard(
  public val weblink: String,
  public val game: String,
  public val category: String,
  public val level: String?,
  public val platform: String?,
  public val region: String?,
  public val emulators: Boolean?,
  @SerializedName("video-only") public val videoOnly: Boolean,
  public val timing: String?,
  public val values: Map<String, String>,
  public val runs: List<RunContainer>,
)