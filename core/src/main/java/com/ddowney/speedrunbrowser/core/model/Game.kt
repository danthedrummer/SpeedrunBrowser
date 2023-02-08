package com.ddowney.speedrunbrowser.core.model

public data class Game(
  val id: String,
  val name: String,
  val abbreviation: String,
  val weblink: String,
  val released: Int,
  val releaseDate: String?,
  val ruleSet: RuleSet?,
  val gameTypes: List<String>?,
  val platforms: List<String>?,
  val regions: List<String>?,
  val genres: List<Genre>?,
  val engines: List<String>?,
  val developers: List<String>?,
  val publishers: List<String>?,
  val moderators: Map<String, String>?,
  val created: String?,
  val assets: GameAssets?,
)