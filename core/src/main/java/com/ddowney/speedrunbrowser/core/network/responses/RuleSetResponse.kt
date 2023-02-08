package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class RuleSetResponse(
  @SerializedName("show-milliseconds") public val showMilliseconds: Boolean,
  @SerializedName("require-verification") public val requireVerification: Boolean,
  @SerializedName("require-video") public val requireVideo: Boolean,
  @SerializedName("run-times") public val runTimes: List<String>,
  @SerializedName("default-time") public val defaultTime: String,
  @SerializedName("emulators-allowed") public val emulatorsAllowed: Boolean,
)