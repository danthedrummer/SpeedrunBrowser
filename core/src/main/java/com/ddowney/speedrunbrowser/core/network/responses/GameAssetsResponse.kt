package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class GameAssetsResponse(
  public val logo: Link,
  public val icon: Link,
  public val background: Link?,
  public val foreground: Link?,
  @SerializedName("cover-tiny") public val coverTiny: Link,
  @SerializedName("cover-small") public val coverSmall: Link,
  @SerializedName("cover-medium") public val coverMedium: Link,
  @SerializedName("cover-large") public val coverLarge: Link,
  @SerializedName("trophy-1st") public val trophyFirst: Link,
  @SerializedName("trophy-2nd") public val trophySecond: Link,
  @SerializedName("trophy-3rd") public val trophyThird: Link,
  @SerializedName("trophy-4th") public val trophyFourth: Link?,
)