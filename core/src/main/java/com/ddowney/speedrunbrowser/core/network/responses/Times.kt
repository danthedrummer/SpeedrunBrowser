package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class Times(
  public val primary: String,
  @SerializedName("primary_t") public val primaryT: Int,
  public val realtime: String,
  @SerializedName("realtime_t") public val realtimeT: Int,
  @SerializedName("realtime_noloads") public val realtimeNoLoads: String,
  @SerializedName("realtime_noloads_t") public val realtimeNoLoadsT: Int,
  @SerializedName("ingame") public val inGame: String,
  @SerializedName("ingame_t") public val inGameT: Int,
)