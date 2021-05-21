package com.ddowney.speedrunbrowser.models

/**
 * Created by Dan on 18/11/2017.
 */
data class Leaderboard(
  val weblink: String,
  val game: String,
  val category: String,
  val level: String,
  val platform: String,
  val region: String,
  val timing: String,
  val runs: List<RunPosition>,
) {

  data class RunPosition(val place: Int, val run: Run)

}