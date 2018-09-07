package flight95.cached.preferences.ktx.extensions

import android.content.SharedPreferences

@Suppress("unused", "IMPLICIT_CAST_TO_ANY")
inline fun <reified T> SharedPreferences.get(key: String): T? =
    when (contains(key)) {
        true ->
            when (T::class) {
                Boolean::class -> getBoolean(key, false)
                Int::class -> getInt(key, 0)
                Long::class -> getLong(key, 0L)
                Float::class -> getFloat(key, 0f)
                String::class -> getString(key, "")
                Set::class -> getStringSet(key, setOf<String>())
                else -> null
            }?.run { this as T }
        false -> null
    }

@Suppress("unused", "UNCHECKED_CAST")
fun SharedPreferences.set(key: String, value: Any? = null): SharedPreferences.Editor =
    edit().apply {
        when (value) {
            null -> remove(key)
            else -> {
                when (value) {
                    is Boolean -> putBoolean(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    is String -> putString(key, value)
                    is Set<*> -> putStringSet(key, value as Set<String>)
                }
            }
        }
    }

@Suppress("unused")
fun SharedPreferences.setDefault(key: String, value: Any? = null): SharedPreferences.Editor =
    when (contains(key)) {
        true -> edit()
        false -> set(key, value)
    }

@Suppress("unused")
fun SharedPreferences.clear(): SharedPreferences.Editor = edit().clear()
