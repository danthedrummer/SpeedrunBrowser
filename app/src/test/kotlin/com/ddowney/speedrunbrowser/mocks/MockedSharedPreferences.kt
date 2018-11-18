package com.ddowney.speedrunbrowser.mocks

import android.content.SharedPreferences
import org.mockito.Mockito.*

class MockedSharedPreferences {

    val map: MutableMap<String, Any?> = mutableMapOf()

    fun getSharedPreferences(): SharedPreferences{
        val sharedPreferences = mock(SharedPreferences::class.java)
        val sharedPreferencesEditor = mock(SharedPreferences.Editor::class.java)

        `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

        mockWrite(sharedPreferencesEditor) { putString(anyString(), nullable(String::class.java)) }
        mockWrite(sharedPreferencesEditor) { putBoolean(anyString(), nullable(Boolean::class.java))  }
        mockWrite(sharedPreferencesEditor) { putFloat(anyString(), nullable(Float::class.java))  }
        mockWrite(sharedPreferencesEditor) { putInt(anyString(), nullable(Int::class.java)) }
        mockWrite(sharedPreferencesEditor) { putLong(anyString(), nullable(Long::class.java)) }

        mockRead(sharedPreferences) { getString(anyString(), nullable(String::class.java)) }
        mockRead(sharedPreferences) { getBoolean(anyString(), nullable(Boolean::class.java)) }
        mockRead(sharedPreferences) { getFloat(anyString(), nullable(Float::class.java)) }
        mockRead(sharedPreferences) { getInt(anyString(), nullable(Int::class.java)) }
        mockRead(sharedPreferences) { getLong(anyString(), nullable(Long::class.java)) }

        mockClear(sharedPreferencesEditor)
        mockContains(sharedPreferences)
        mockRemove(sharedPreferencesEditor)

        return sharedPreferences
    }

    private fun mockContains(sharedPreferences: SharedPreferences) {
        doAnswer {
            map.containsKey(it.arguments[0] as String)
        }.`when`(sharedPreferences).contains(anyString())
    }

    private fun mockClear(sharedPreferencesEditor: SharedPreferences.Editor) {
        doAnswer {
            map.clear()
            sharedPreferencesEditor
        }.`when`(sharedPreferencesEditor).clear()
    }

    private fun mockWrite(sharedPreferencesEditor: SharedPreferences.Editor, write: SharedPreferences.Editor.() -> Unit) {
        doAnswer {
            map[it.arguments[0] as String] = it.arguments[1]
            sharedPreferencesEditor
        }.`when`(sharedPreferencesEditor).write()
    }

    private fun <E> mockRead(sharedPreferences: SharedPreferences, get: SharedPreferences.() -> E) where E : Any? {
        doAnswer {
            @Suppress("UNCHECKED_CAST")
            (map[it.arguments[0]] as? E ?: it.arguments[1] as? E)
        }.`when`(sharedPreferences).get()
    }

    private fun mockRemove(sharedPreferencesEditor: SharedPreferences.Editor) {
        doAnswer {
            map.remove(it.arguments[0] as String)
            sharedPreferencesEditor
        }.`when`(sharedPreferencesEditor).remove(anyString())
    }

}