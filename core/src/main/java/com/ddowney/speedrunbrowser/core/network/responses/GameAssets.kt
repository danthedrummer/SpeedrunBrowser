package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class GameAssets(
  private val logo: Link,
  private val icon: Link,
  private val background: Link?,
  private val foreground: Link?,
  @SerializedName("cover-tiny") private val coverTiny: Link,
  @SerializedName("cover-small") private val coverSmall: Link,
  @SerializedName("cover-medium") private val coverMedium: Link,
  @SerializedName("cover-large") private val coverLarge: Link,
  @SerializedName("trophy-1st") private val trophyFirst: Link,
  @SerializedName("trophy-2nd") private val trophySecond: Link,
  @SerializedName("trophy-3rd") private val trophyThird: Link,
  @SerializedName("trophy-4th") private val trophyFourth: Link?,
)