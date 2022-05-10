package com.ddowney.speedrunbrowser.core.network.api

import com.ddowney.speedrunbrowser.core.network.responses.ListRoot
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import com.ddowney.speedrunbrowser.core.network.responses.Run
import com.ddowney.speedrunbrowser.core.network.services.RunService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class RunApiTests {

  companion object {
    private const val RUN_ID = "some-run"
  }

  private val runService = mockk<RunService> {
    coEvery { getRuns(any()) } returns ListRoot(emptyList())
    coEvery { getRun(any(), any()) } returns ObjectRoot(mockk())
  }

  private val runApi = RunApi(
    runService = runService,
    ioDispatcher = TestCoroutineDispatcher()
  )

  // region getRuns
  @Test
  fun `getRuns should access the network`() = runBlockingTest {
    runApi.getRuns()
    coVerify { runService.getRuns() }
  }

  @Test
  fun `getRuns should add any additional queries`() = runBlockingTest {
    val options = mapOf(
      "foo" to "bar",
    )
    runApi.getRuns(options)
    coVerify { runService.getRuns(options) }
  }

  @Test
  fun `getRuns should return the contents of the response`() = runBlockingTest {
    val expected = listOf<Run>(mockk(), mockk())
    coEvery { runService.getRuns() } returns ListRoot(expected)
    val result = runApi.getRuns()
    assertThat(result).isEqualTo(expected)
  }
  // endregion

  // region getRun

  @Test
  fun `getRun should access the network`() = runBlockingTest {
    runApi.getRun(RUN_ID)
    coVerify { runService.getRun(RUN_ID) }
  }

  @Test
  fun `getRun should add any additional queries`() = runBlockingTest {
    val options = mapOf(
      "foo" to "bar",
    )
    runApi.getRun(RUN_ID, options)
    coVerify { runService.getRun(RUN_ID, options) }
  }

  @Test
  fun `getRun should return the contents of the response`() = runBlockingTest {
    val expected = mockk<Run>()
    coEvery { runService.getRun(RUN_ID) } returns ObjectRoot(expected)
    val result = runApi.getRun(RUN_ID)
    assertThat(result).isEqualTo(expected)
  }
  // endregion
}