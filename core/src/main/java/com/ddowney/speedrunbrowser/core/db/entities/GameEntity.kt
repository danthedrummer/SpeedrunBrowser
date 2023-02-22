package com.ddowney.speedrunbrowser.core.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ddowney.speedrunbrowser.core.model.Game
import com.ddowney.speedrunbrowser.core.network.responses.GameResponse
import com.ddowney.speedrunbrowser.core.serialization.Embed

@Entity(
  tableName = "game",
  foreignKeys = [
    ForeignKey(
      entity = GenreEntity::class,
      parentColumns = arrayOf("id"),
      childColumns = arrayOf("genres"),
    ),
    ForeignKey(
      entity = PlatformEntity::class,
      parentColumns = arrayOf("id"),
      childColumns = arrayOf("platforms"),
    )
  ]
)
internal data class GameEntity(
  @PrimaryKey val id: String,
  val name: String,
  val abbreviation: String,
  val weblink: String,
  val released: Int,
  val releaseDate: String?,
  @Embedded val ruleSet: RuleSetEntity?,
  val gameTypes: List<String>?,
  val platforms: List<PlatformEntity>?,
  val regions: List<String>?,
  val genres: List<GenreEntity>?,
  val engines: List<String>?,
  val developers: List<String>?,
  val publishers: List<String>?,
  val moderators: Map<String, String>?,
  val created: String?,
  @Embedded val assets: GameAssetsEntity?,
) {

  fun toGame(): Game = Game(
    id = id,
    name = name,
    abbreviation = abbreviation,
    weblink = weblink,
    released = released,
    releaseDate = releaseDate,
    ruleSet = ruleSet?.toRuleSet(),
    gameTypes = gameTypes,
    platforms = platforms?.map { it.toPlatform() },
    regions = regions,
    genres = genres?.map { it.toGenre() },
    engines = engines,
    developers = developers,
    publishers = publishers,
    moderators = moderators,
    created = created,
    assets = assets?.toGameAssets(),
  )

  companion object {

    fun toEntity(response: GameResponse) = GameEntity(
      id = response.id,
      name = response.names.international,
      abbreviation = response.abbreviation,
      weblink = response.weblink,
      released = response.released,
      releaseDate = response.releaseDate,
      ruleSet = response.ruleSet?.let(RuleSetEntity::toEntity),
      gameTypes = response.gameTypes,
      platforms = when (val platforms = response.platforms) {
        is Embed.Populated -> platforms.data.map(PlatformEntity::toEntity)
        is Embed.Raw -> platforms.data.map { PlatformEntity(it) }
      },
      regions = response.regions,
      genres = when (val genres = response.genres) {
        is Embed.Populated -> genres.data.map(GenreEntity::toEntity)
        is Embed.Raw -> genres.data.map { GenreEntity(it) }
      },
      engines = response.engines,
      developers = response.developers,
      publishers = response.publishers,
      moderators = response.moderators,
      created = response.created,
      assets = response.assets?.let(GameAssetsEntity::toEntity),
    )
  }
}