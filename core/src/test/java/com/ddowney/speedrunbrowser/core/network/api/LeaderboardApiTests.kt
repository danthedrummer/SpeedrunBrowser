package com.ddowney.speedrunbrowser.core.network.api

import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import com.ddowney.speedrunbrowser.core.network.services.LeaderboardService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LeaderboardApiTests {

  companion object {
    private const val GAME_ID = "some-game-id"
    private const val LEVEL_ID = "some-level-id"
    private const val CATEGORY_ID = "some-category-id"
  }

  private val leaderboardService = mockk<LeaderboardService> {
    coEvery { getLeaderboard(GAME_ID, CATEGORY_ID, any<Map<String, String>>()) } returns ObjectRoot(mockk())
    coEvery { getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID, any()) } returns ObjectRoot(mockk())
  }

  private val api = LeaderboardApi(
    leaderboardService = leaderboardService,
    ioDispatcher = TestCoroutineDispatcher()
  )

  // region getLeaderboard (for full game categories)
  @Test
  fun `getLeaderboard (full game) should access the network`() = runBlockingTest {
    api.getLeaderboard(GAME_ID, CATEGORY_ID)
    coVerify { leaderboardService.getLeaderboard(GAME_ID, CATEGORY_ID) }
  }

  @Test
  fun `getLeaderboard (full game) should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getLeaderboard(GAME_ID, CATEGORY_ID, queries)
    coVerify { leaderboardService.getLeaderboard(GAME_ID, CATEGORY_ID, queries) }
  }

  @Test
  fun `getLeaderboard (full game) should return the contents of the response`() = runBlockingTest {
    val expected = mockk<Leaderboard>()
    coEvery { leaderboardService.getLeaderboard(GAME_ID, CATEGORY_ID) } returns ObjectRoot(expected)
    val result = api.getLeaderboard(GAME_ID, CATEGORY_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getLeaderboard (for level categories)
  @Test
  fun `getLeaderboard (level) should access the network`() = runBlockingTest {
    api.getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID)
    coVerify { leaderboardService.getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID) }
  }

  @Test
  fun `getLeaderboard (level) should add any additional queries`() = runBlockingTest {
    val queries = mapOf(
      "foo" to "bar",
    )
    api.getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID, queries)
    coVerify { leaderboardService.getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID, queries) }
  }

  @Test
  fun `getLeaderboard (level) should return the contents of the response`() = runBlockingTest {
    val expected = mockk<Leaderboard>()
    coEvery { leaderboardService.getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID) } returns ObjectRoot(expected)
    val result = api.getLeaderboard(GAME_ID, LEVEL_ID, CATEGORY_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion
}