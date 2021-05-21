package com.ddowney.speedrunbrowser.mocks

import android.content.SharedPreferences
import java.util.*

class FakeSharedPreferences : SharedPreferences {

  private val editor = FakeEditor { transactions ->
    transactions.forEach {
      _map = it(_map)
    }
  }

  private var _map: Map<String, Any?> = mapOf()

  val map: Map<String, Any?>
    get() = _map

  override fun getAll(): MutableMap<String, *> = _map.toMutableMap()

  override fun getString(p0: String?, p1: String?): String? = _map[p0] as? String ?: p1

  @Suppress("UNCHECKED_CAST")
  override fun getStringSet(p0: String?, p1: MutableSet<String>?): MutableSet<String>? =
    _map[p0] as? MutableSet<String> ?: p1

  override fun getInt(p0: String?, p1: Int): Int = _map[p0] as? Int ?: p1

  override fun getLong(p0: String?, p1: Long): Long = _map[p0] as? Long ?: p1

  override fun getFloat(p0: String?, p1: Float): Float = _map[p0] as? Float ?: p1

  override fun getBoolean(p0: String?, p1: Boolean): Boolean = _map[p0] as? Boolean ?: p1

  override fun contains(p0: String?): Boolean = _map.contains(p0)

  override fun edit(): SharedPreferences.Editor = editor

  override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
    // Not supported
  }

  override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
    // Not supported
  }

  class FakeEditor(
    private val apply: (LinkedList<(Map<String, Any?>) -> Map<String, Any?>>) -> Unit,
  ) : SharedPreferences.Editor {

    private val transactions = LinkedList<(Map<String, Any?>) -> Map<String, Any?>>()

    override fun putString(p0: String, p1: String?): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.plus(p0 to p1)
      }
    }

    override fun putStringSet(p0: String, p1: MutableSet<String>?): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.plus(p0 to p1)
      }
    }

    override fun putInt(p0: String, p1: Int): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.plus(p0 to p1)
      }
    }

    override fun putLong(p0: String, p1: Long): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.plus(p0 to p1)
      }
    }

    override fun putFloat(p0: String, p1: Float): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.plus(p0 to p1)
      }
    }

    override fun putBoolean(p0: String, p1: Boolean): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.plus(p0 to p1)
      }
    }

    override fun remove(p0: String): SharedPreferences.Editor = apply {
      transactions.add { map ->
        map.minus(p0)
      }
    }

    override fun clear(): SharedPreferences.Editor = apply {
      transactions.add {
        emptyMap()
      }
    }

    override fun commit(): Boolean {
      apply()
      return true
    }

    override fun apply() {
      apply(transactions)
    }

  }
}