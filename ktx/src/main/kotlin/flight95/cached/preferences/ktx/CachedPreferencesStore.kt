package flight95.cached.preferences.ktx

import android.content.Context

@Suppress("unused")
interface CachedPreferencesStore {
    val categories: Set<CachedPreferencesCategory>

    fun clear(context: Context) =
        categories.forEach {
            CachedPreferences.make(context, it).clear().apply()
        }
}