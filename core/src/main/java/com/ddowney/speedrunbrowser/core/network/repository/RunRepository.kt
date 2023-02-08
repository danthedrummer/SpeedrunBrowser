package com.ddowney.speedrunbrowser.core.network.repository

import com.ddowney.speedrunbrowser.core.di.modules.IoDispatcher
import com.ddowney.speedrunbrowser.core.network.responses.Run
import com.ddowney.speedrunbrowser.core.network.services.RunService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Fetches [Run] details from the speedrun.com API.
 */
public class RunRepository @Inject internal constructor(
  private val runService: RunService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

  /**
   * Gets a page of [Run] entities.
   *
   * @param options a map of query params
   * @return a list of [Run] entities
   */
  public suspend fun getRuns(
    options: Map<String, String> = emptyMap(),
  ): List<Run> = withContext(ioDispatcher) {
    runService.getRuns(options).data
  }

  /**
   * Gets a specific [Run] entity
   *
   * @param runId the ID of the [Run] entity
   * @param options a map of query params
   * @return a [Run] entity
   */
  public suspend fun getRun(
    runId: String,
    options: Map<String, String> = emptyMap(),
  ): Run = withContext(ioDispatcher) {
    runService.getRun(runId, options).data
  }
}