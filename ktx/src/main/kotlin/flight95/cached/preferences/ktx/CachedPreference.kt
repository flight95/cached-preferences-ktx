package flight95.cached.preferences.ktx

import androidx.annotation.StringRes
import androidx.preference.Preference
import flight95.cached.preferences.ktx.extensions.get
import flight95.cached.preferences.ktx.extensions.getKeyId
import flight95.cached.preferences.ktx.extensions.set

@Suppress("unused")
data class CachedPreference(val preference: Preference) {

    @StringRes val id: Int = preference.getKeyId()

    val key: String = preference.key
        ?: throw IllegalStateException("Preference ${preference.title} key was not valid.")

    inline fun <reified T> get(): T =
        preference.sharedPreferences
            ?.get(key)
            ?: throw IllegalStateException("Preference ${preference.title} value was not valid.")

    fun set(value: Any? = null) {
        preference.sharedPreferences
            ?.apply { set(key, value).apply() }
            ?: throw IllegalStateException("Preference ${preference.title} data store could not be null.")
    }
}