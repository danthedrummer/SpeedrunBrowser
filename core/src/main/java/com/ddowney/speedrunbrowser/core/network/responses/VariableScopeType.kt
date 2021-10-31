package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public enum class VariableScopeType {
  @SerializedName("global")
  GLOBAL,
  @SerializedName("full-game")
  FULL_GAME,
  @SerializedName("all-levels")
  ALL_LEVELS,
  @SerializedName("single-level")
  SINGLE_LEVEL,
}