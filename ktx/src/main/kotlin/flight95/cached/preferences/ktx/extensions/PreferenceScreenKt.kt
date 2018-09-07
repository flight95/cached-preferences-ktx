package flight95.cached.preferences.ktx.extensions

import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceScreen
import flight95.cached.preferences.ktx.CachedPreferenceCategory
import flight95.cached.preferences.ktx.CachedPreferenceStore

@Suppress("unused")
fun PreferenceScreen.getStore() = CachedPreferenceStore(getCategories())

@Suppress("unused")
fun PreferenceScreen.getCategories(): Set<CachedPreferenceCategory> =
    mutableSetOf<CachedPreferenceCategory>()
        .apply {
            getPreferences().forEach { preference ->
                when (preference) {
                    is PreferenceCategory -> add(preference.getCategory())
                    else -> throw IllegalStateException("Preference ${preference.title} was not PreferenceCategory.")
                }
            }
        }
        .run {
            when (isNotEmpty()) {
                true -> this
                false -> throw IllegalStateException("PreferenceScreen have no child.")
            }
        }