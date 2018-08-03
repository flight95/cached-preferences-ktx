package flight95.cached.preferences.ktx

import android.content.Context

interface CachedPreferencesCategory {
    val value: String
    val keys: Set<CachedPreferencesKey>

    fun make(context: Context) = CachedPreferences.make(context, this)
}