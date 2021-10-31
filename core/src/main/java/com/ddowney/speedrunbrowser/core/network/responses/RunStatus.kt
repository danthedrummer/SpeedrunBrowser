package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class RunStatus(
  public val status: String,
  public val examiner: String?,
  @SerializedName("verify-date") public val verifyDate: String?,
  public val reason: String?,
)