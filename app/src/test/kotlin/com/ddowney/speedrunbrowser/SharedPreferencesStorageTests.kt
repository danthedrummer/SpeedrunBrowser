package com.ddowney.speedrunbrowser

import com.ddowney.speedrunbrowser.mocks.FakeSharedPreferences
import com.ddowney.speedrunbrowser.storage.SharedPreferencesStorage
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class SharedPreferencesStorageTests {

  private val gson = Gson()
  private val testKey = "c137"
  private val testString = "Rick Sanchez"
  private val testObject = TestObject(testString)

  private val fakeSharedPreferences = FakeSharedPreferences()
  private val storage = SharedPreferencesStorage(fakeSharedPreferences, gson)

  @Test
  fun `calling put string should persist that string`() {
    storage.put(testKey, testString)

    assertThat(fakeSharedPreferences.map).containsEntry(testKey, testString)
  }

  @Test
  fun `calling put string should overwrite existing entries for a given key`() {
    storage.put(testKey, testString)

    val newValue = "Morty Smith"
    storage.put(testKey, newValue)

    assertThat(fakeSharedPreferences.map).doesNotContainEntry(testKey, testString)
    assertThat(fakeSharedPreferences.map).containsEntry(testKey, newValue)
  }

  @Test
  fun `calling put object should persist that object`() {
    storage.put(testKey, testObject)

    assertThat(fakeSharedPreferences.map).containsEntry(testKey, gson.toJson(testObject))
  }

  @Test
  fun `calling put object should overwrite existing entries for a given key`() {
    storage.put(testKey, testObject)

    val newObject = TestObject("Schmeckles")
    storage.put(testKey, newObject)

    assertThat(fakeSharedPreferences.map).doesNotContainEntry(testKey, testObject)
    assertThat(fakeSharedPreferences.map).containsEntry(testKey, gson.toJson(newObject))
  }

  @Test
  fun `get string should return the stored value if the key exists`() {
    storage.put(testKey, testString)

    assertThat(storage.get(testKey)).isEqualTo(testString)
  }

  @Test
  fun `get string should return null if the key does not exist`() {
    assertThat(storage.get(testKey)).isNull()
  }

  @Test
  fun `get string should return a string representation of an object if the key exists`() {
    storage.put(testKey, testObject)

    assertThat(storage.get(testKey)).isEqualTo(gson.toJson(testObject))
  }

  @Test
  fun `get object should return the deserialised object from storage if the key exists`() {
    storage.put(testKey, testObject)

    assertThat(storage.get(testKey, TestObject::class.java)).isEqualTo(testObject)
  }

  @Test
  fun `get object should return null if the key does not exist`() {
    assertThat(storage.get(testKey, TestObject::class.java)).isNull()
  }

  @Test
  fun `remove should remove a key value pair from storage`() {
    storage.put(testKey, testString)
    assertThat(fakeSharedPreferences.map).containsEntry(testKey, testString)

    storage.remove(testKey)
    assertThat(fakeSharedPreferences.map).doesNotContainEntry(testKey, testString)
  }

  @Test
  fun `remove should do nothing if the key does not exist`() {
    storage.remove(testKey)
    assertThat(fakeSharedPreferences.map).doesNotContainKey(testKey)
  }

  @Test
  fun `clear should remove everything from storage`() {
    storage.put("foo", testString)
    storage.put("bar", testObject)
    assertThat(fakeSharedPreferences.map).isNotEmpty()

    storage.clear()
    assertThat(fakeSharedPreferences.map).isEmpty()
  }

  data class TestObject(val value: String)
}