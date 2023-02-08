package com.ddowney.speedrunbrowser.core.db.entities

import com.ddowney.speedrunbrowser.core.model.GameAssets
import com.ddowney.speedrunbrowser.core.network.responses.GameAssetsResponse

internal data class GameAssetsEntity(
  val logo: String,
  val icon: String,
  val background: String?,
  val foreground: String?,
  val coverTiny: String,
  val coverSmall: String,
  val coverMedium: String,
  val coverLarge: String,
  val trophyFirst: String,
  val trophySecond: String,
  val trophyThird: String,
  val trophyFourth: String?,
) {

  fun toGameAssets() = GameAssets(
    logo = logo,
    icon = icon,
    background = background,
    foreground = foreground,
    coverTiny = coverTiny,
    coverSmall = coverSmall,
    coverMedium = coverMedium,
    coverLarge = coverLarge,
    trophyFirst = trophyFirst,
    trophySecond = trophySecond,
    trophyThird = trophyThird,
    trophyFourth = trophyFourth,
  )

  companion object {

    fun toEntity(response: GameAssetsResponse) = GameAssetsEntity(
      logo = response.logo.uri,
      icon = response.icon.uri,
      background = response.background?.uri,
      foreground = response.foreground?.uri,
      coverTiny = response.coverTiny.uri,
      coverSmall = response.coverSmall.uri,
      coverMedium = response.coverMedium.uri,
      coverLarge = response.coverLarge.uri,
      trophyFirst = response.trophyFirst.uri,
      trophySecond = response.trophySecond.uri,
      trophyThird = response.trophyThird.uri,
      trophyFourth = response.trophyFourth?.uri,
    )
  }
}