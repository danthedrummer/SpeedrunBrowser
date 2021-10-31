package com.ddowney.speedrunbrowser.core.network.responses

public data class Level(
  private val id: String,
  private val name: String,
  private val weblink: String,
  private val rules: String,
)