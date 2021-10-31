package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class Variable(
  private val id: String,
  private val name: String,
  private val category: String?,
  private val scope: VariableScope,
  private val mandatory: Boolean,
  @SerializedName("user-defined") private val userDefined: Boolean,
  private val obsoletes: Boolean,
  private val values: VariableValues,
)