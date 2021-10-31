package com.ddowney.speedrunbrowser.core.network.responses

public data class Pagination(
  private val offset: Int,
  private val max: Int,
  private val size: Int,
)