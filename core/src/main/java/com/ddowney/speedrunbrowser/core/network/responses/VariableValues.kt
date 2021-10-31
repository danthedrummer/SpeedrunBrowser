package com.ddowney.speedrunbrowser.core.network.responses

public data class VariableValues(
  private val values: Map<String, Value>,
  private val default: String?,
)