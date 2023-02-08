package com.ddowney.speedrunbrowser.core.network.repository

import com.ddowney.speedrunbrowser.core.db.SpeedrunBrowserDatabase
import com.ddowney.speedrunbrowser.core.db.dao.GameDao
import com.ddowney.speedrunbrowser.core.network.responses.Category
import com.ddowney.speedrunbrowser.core.network.responses.GameResponse
import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.Level
import com.ddowney.speedrunbrowser.core.network.responses.ListRoot
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import com.ddowney.speedrunbrowser.core.network.responses.Pagination
import com.ddowney.speedrunbrowser.core.network.responses.Variable
import com.ddowney.speedrunbrowser.core.network.services.GameService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GameResponseRepositoryTests {

  companion object {
    private const val GAME_ID = "default-game-id"

    private val defaultPagination = Pagination(
      max = 20,
      offset = 0,
      size = 20,
    )
  }

  private val gameService = mockk<GameService> {
    coEvery { getGames(any()) } returns ListRoot(emptyList(), defaultPagination)
    coEvery { getGame(GAME_ID, any()) } returns ObjectRoot(mockk())
    coEvery { getCategoriesForGame(GAME_ID, any()) } returns ListRoot(mockk(), defaultPagination)
    coEvery { getLevelsForGame(GAME_ID, any()) } returns ListRoot(mockk(), defaultPagination)
    coEvery { getVariablesForGame(GAME_ID, any()) } returns ListRoot(mockk(), defaultPagination)
    coEvery { getDerivedGamesForGame(GAME_ID, any()) } returns ListRoot(mockk(), defaultPagination)
    coEvery { getRecordsForGame(GAME_ID, any()) } returns ListRoot(mockk(), defaultPagination)
  }
  private val gameDao = mockk<GameDao>()
  private val database = mockk<SpeedrunBrowserDatabase> {
    every { gameDao() } returns gameDao
  }

  private val api = GameRepository(
    gameService = gameService,
    ioDispatcher = TestCoroutineDispatcher(),
    database = database,
  )

  // region getGames
  @Test
  fun `getGames should access the network`() = runBlockingTest {
    api.getGames()
    coVerify { gameService.getGames() }
  }

  @Test
  fun `getGames should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getGames(queries)
    coVerify { gameService.getGames(queries) }
  }

  @Test
  fun `getGames should return the contents of the response`() = runBlockingTest {
    val expected = listOf<GameResponse>(mockk())
    coEvery { gameService.getGames() } returns ListRoot(expected, defaultPagination)
    val result = api.getGames()
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getGame
  @Test
  fun `getGame should access the network`() = runBlockingTest {
    api.getGame(GAME_ID)
    coVerify { gameService.getGame(GAME_ID) }
  }

  @Test
  fun `getGame should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getGame(GAME_ID, queries)
    coVerify { gameService.getGame(GAME_ID, queries) }
  }

  @Test
  fun `getGame should return the contents of the response`() = runBlockingTest {
    val expected = mockk<GameResponse>()
    coEvery { gameService.getGame(GAME_ID) } returns ObjectRoot(expected)
    val result = api.getGame(GAME_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getCategoriesForGame
  @Test
  fun `getCategoriesForGame should access the network`() = runBlockingTest {
    api.getCategoriesForGame(GAME_ID)
    coVerify { gameService.getCategoriesForGame(GAME_ID) }
  }

  @Test
  fun `getCategoriesForGame should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getCategoriesForGame(GAME_ID, queries)
    coVerify { gameService.getCategoriesForGame(GAME_ID, queries) }
  }

  @Test
  fun `getCategoriesForGame should return the contents of the response`() = runBlockingTest {
    val expected = listOf<Category>(mockk())
    coEvery { gameService.getCategoriesForGame(GAME_ID) } returns ListRoot(expected, defaultPagination)
    val result = api.getCategoriesForGame(GAME_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getLevelsForGame
  @Test
  fun `getLevelsForGame should access the network`() = runBlockingTest {
    api.getLevelsForGame(GAME_ID)
    coVerify { gameService.getLevelsForGame(GAME_ID) }
  }

  @Test
  fun `getLevelsForGame should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getLevelsForGame(GAME_ID, queries)
    coVerify { gameService.getLevelsForGame(GAME_ID, queries) }
  }

  @Test
  fun `getLevelsForGame should return the contents of the response`() = runBlockingTest {
    val expected = listOf<Level>(mockk())
    coEvery { gameService.getLevelsForGame(GAME_ID) } returns ListRoot(expected, defaultPagination)
    val result = api.getLevelsForGame(GAME_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getVariablesForGame
  @Test
  fun `getVariablesForGame should access the network`() = runBlockingTest {
    api.getVariablesForGame(GAME_ID)
    coVerify { gameService.getVariablesForGame(GAME_ID) }
  }

  @Test
  fun `getVariablesForGame should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getVariablesForGame(GAME_ID, queries)
    coVerify { gameService.getVariablesForGame(GAME_ID, queries) }
  }

  @Test
  fun `getVariablesForGame should return the contents of the response`() = runBlockingTest {
    val expected = listOf<Variable>(mockk())
    coEvery { gameService.getVariablesForGame(GAME_ID) } returns ListRoot(expected, defaultPagination)
    val result = api.getVariablesForGame(GAME_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getVariablesForGame
  @Test
  fun `getDerivedGamesForGame should access the network`() = runBlockingTest {
    api.getDerivedGamesForGame(GAME_ID)
    coVerify { gameService.getDerivedGamesForGame(GAME_ID) }
  }

  @Test
  fun `getDerivedGamesForGame should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getDerivedGamesForGame(GAME_ID, queries)
    coVerify { gameService.getDerivedGamesForGame(GAME_ID, queries) }
  }

  @Test
  fun `getDerivedGamesForGame should return the contents of the response`() = runBlockingTest {
    val expected = listOf<GameResponse>(mockk())
    coEvery { gameService.getDerivedGamesForGame(GAME_ID) } returns ListRoot(expected, defaultPagination)
    val result = api.getDerivedGamesForGame(GAME_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getRecordsForGame
  @Test
  fun `getRecordsForGame should access the network`() = runBlockingTest {
    api.getRecordsForGame(GAME_ID)
    coVerify { gameService.getRecordsForGame(GAME_ID) }
  }

  @Test
  fun `getRecordsForGame should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getRecordsForGame(GAME_ID, queries)
    coVerify { gameService.getRecordsForGame(GAME_ID, queries) }
  }

  @Test
  fun `getRecordsForGame should return the contents of the response`() = runBlockingTest {
    val expected = listOf<Leaderboard>(mockk())
    coEvery { gameService.getRecordsForGame(GAME_ID) } returns ListRoot(expected, defaultPagination)
    val result = api.getRecordsForGame(GAME_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion
}