package com.ddowney.speedrunbrowser.core.model

public data class RuleSet(
  val showMilliseconds: Boolean,
  val requireVerification: Boolean,
  val requireVideo: Boolean,
  val runTimes: List<String>,
  val defaultTime: String,
  val emulatorsAllowed: Boolean,
)