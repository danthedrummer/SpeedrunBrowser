package com.ddowney.speedrunbrowser.core.network.responses

public data class Category(
  private val id: String,
  private val name: String,
  private val weblink: String,
  private val type: String,
  private val rules: String,
  private val players: Players,
  private val miscellaneous: Boolean,
)