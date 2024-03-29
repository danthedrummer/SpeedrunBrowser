package com.ddowney.speedrunbrowser.core.network.responses

import com.google.gson.annotations.SerializedName

public data class Game(
  public val id: String,
  public val names: GameNames,
  public val abbreviation: String,
  public val weblink: String,
  public val release: Int,
  @SerializedName("release-date") public val releaseDate: String?,
  @SerializedName("ruleset") public val ruleSet: RuleSet?,
  @SerializedName("gametypes") public val gameTypes: List<String>?,
  public val platforms: List<String>?,
  public val regions: List<String>?,
  public val genres: List<String>?,
  public val engines: List<String>?,
  public val developers: List<String>?,
  public val publishers: List<String>?,
  public val moderators: Map<String, String>?,
  public val created: String?,
  public val assets: GameAssets?,
  public val pagination: Pagination?,
) {

  /**
   * Transforms this [Game] to the [BulkGame] model removing all of the
   * fields which may be null or not needed.
   *
   * @return an instance of [BulkGame]
   */
  public fun toBulkGame(): BulkGame = BulkGame(
    id = id,
    names = names,
    abbreviation = abbreviation,
    weblink = weblink,
  )
}