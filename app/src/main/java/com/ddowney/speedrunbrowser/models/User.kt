package com.ddowney.speedrunbrowser.models

/**
 * Created by Dan on 19/12/2017.
 */
data class User(
  val id: String,
  val names: NamesModel,
  val weblink: String,
  val twitch: Run.Links?,
  val youtube: Run.Links?,
  val hitbox: Run.Links?,
  val twitter: Run.Links?,
  val speedrunslive: Run.Links?,
) {

  data class NamesModel(val international: String, val japanese: String?)

}