package com.ddowney.speedrunbrowser.core.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class TimeFormatterTests {

  companion object {
    private const val MINUTE = 60F
    private const val HOUR = MINUTE * 60F
  }

  @Test
  fun `getReadableTime should handle hours`() {
    val result = TimeFormatter.getReadableTime(HOUR)
    assertThat(result).isEqualTo("01:00:00")
  }

  @Test
  fun `getReadableTime should expand to any number of hours`() {
    val result = TimeFormatter.getReadableTime(HOUR * 1234)
    assertThat(result).isEqualTo("1234:00:00")
  }

  @Test
  fun `getReadableTime should handle minutes`() {
    val result = TimeFormatter.getReadableTime(MINUTE)
    assertThat(result).isEqualTo("00:01:00")
  }

  @Test
  fun `getReadableTime should handle seconds`() {
    val result = TimeFormatter.getReadableTime(1F)
    assertThat(result).isEqualTo("00:00:01")
  }

  @Test
  fun `getReadableTime should handle combinations`() {
    val result = TimeFormatter.getReadableTime(23F + (MINUTE * 5) + (HOUR * 2))
    assertThat(result).isEqualTo("02:05:23")
  }
}