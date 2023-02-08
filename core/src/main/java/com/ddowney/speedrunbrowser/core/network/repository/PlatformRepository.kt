package com.ddowney.speedrunbrowser.core.network.repository

import android.util.Log
import com.ddowney.speedrunbrowser.core.db.SpeedrunBrowserDatabase
import com.ddowney.speedrunbrowser.core.db.entities.PlatformEntity
import com.ddowney.speedrunbrowser.core.di.modules.IoDispatcher
import com.ddowney.speedrunbrowser.core.model.Platform
import com.ddowney.speedrunbrowser.core.network.responses.ListRoot
import com.ddowney.speedrunbrowser.core.network.responses.PlatformResponse
import com.ddowney.speedrunbrowser.core.network.services.PlatformService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class PlatformRepository @Inject internal constructor(
  private val platformService: PlatformService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
  database: SpeedrunBrowserDatabase,
) {

  private val platformDao by lazy { database.platformDao() }

  public suspend fun fetchAllPlatforms() {
    withContext(ioDispatcher) {
      if (platformDao.getAll().isNotEmpty()) return@withContext

      var response: ListRoot<PlatformResponse>?
      var page = 0
      val max = 200

      do {
        response = platformService.getPlatforms(
          options = mapOf(
            "offset" to (page * max).toString(),
            "max" to max.toString(),
          )
        )
        page++
        platformDao.insertAll(response.data.map(PlatformEntity::toEntity))
      } while (response != null && response.pagination.size == response.pagination.max)
    }
  }

  public suspend fun getPlatforms(
    options: Map<String, String> = emptyMap(),
  ): List<Platform> = withContext(ioDispatcher) {
    val localPlatforms = platformDao.getAll()
    if (localPlatforms.isNotEmpty()) {
      return@withContext localPlatforms.map { it.toPlatform() }
    }

    val response = platformService.getPlatforms(options).data
    val platformEntities = response.map {
      PlatformEntity(
        id = it.id,
        name = it.name,
      )
    }
    platformDao.insertAll(platformEntities)
    platformEntities.map { it.toPlatform() }
  }

  public suspend fun getPlatform(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): Platform = withContext(ioDispatcher) {
    val localPlatform = platformDao.getById(id)
    if (localPlatform != null) {
      return@withContext localPlatform.toPlatform()
    }

    val response = platformService.getPlatformById(id, options).data
    val platformEntity = PlatformEntity(
      id = response.id,
      name = response.name,
    )
    platformDao.insert(platformEntity)
    platformEntity.toPlatform()
  }
}