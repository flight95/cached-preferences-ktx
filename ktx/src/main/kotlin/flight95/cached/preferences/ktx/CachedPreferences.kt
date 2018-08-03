package flight95.cached.preferences.ktx

import android.content.Context
import android.content.SharedPreferences

@Suppress("unused")
class CachedPreferences(val pref: SharedPreferences) {

    companion object {
        fun make(context: Context, category: CachedPreferencesCategory, mode: Int = Context.MODE_PRIVATE) =
            CachedPreferences(context.applicationContext.getSharedPreferences(category.value, mode))
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    inline fun <reified T> get(key: CachedPreferencesKey): T? =
        when (pref.contains(key.value)) {
            true ->
                when (T::class) {
                    Boolean::class -> pref.getBoolean(key.value, false)
                    Int::class -> pref.getInt(key.value, 0)
                    Long::class -> pref.getLong(key.value, 0L)
                    Float::class -> pref.getFloat(key.value, 0f)
                    String::class -> pref.getString(key.value, "")
                    else -> null
                }?.run { this as T }
            false -> null
        }

    fun set(key: CachedPreferencesKey, value: Any? = null): SharedPreferences.Editor =
        pref.edit().apply {
            when (value) {
                null -> remove(key.value)
                else -> {
                    when (value) {
                        is Boolean -> putBoolean(key.value, value)
                        is Int -> putInt(key.value, value)
                        is Long -> putLong(key.value, value)
                        is Float -> putFloat(key.value, value)
                        is String -> putString(key.value, value)
                    }
                }
            }
        }

    fun setDefault(key: CachedPreferencesKey, value: Any? = null): SharedPreferences.Editor =
        when (pref.contains(key.value)) {
            true -> pref.edit()
            false -> set(key, value)
        }

    fun clear(): SharedPreferences.Editor = pref.edit().clear()
}