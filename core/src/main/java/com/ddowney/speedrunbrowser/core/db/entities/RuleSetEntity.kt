package com.ddowney.speedrunbrowser.core.db.entities

import com.ddowney.speedrunbrowser.core.model.RuleSet
import com.ddowney.speedrunbrowser.core.network.responses.RuleSetResponse

internal data class RuleSetEntity(
  val showMilliseconds: Boolean,
  val requireVerification: Boolean,
  val requireVideo: Boolean,
  val runTimes: List<String>,
  val defaultTime: String,
  val emulatorsAllowed: Boolean,
) {

  fun toRuleSet(): RuleSet = RuleSet(
    showMilliseconds = showMilliseconds,
    requireVerification = requireVerification,
    requireVideo = requireVideo,
    runTimes = runTimes,
    defaultTime = defaultTime,
    emulatorsAllowed = emulatorsAllowed,
  )

  companion object {

    fun toEntity(response: RuleSetResponse) = RuleSetEntity(
      showMilliseconds = response.showMilliseconds,
      requireVerification = response.requireVerification,
      requireVideo = response.requireVideo,
      runTimes = response.runTimes,
      defaultTime = response.defaultTime,
      emulatorsAllowed = response.emulatorsAllowed,
    )
  }
}
