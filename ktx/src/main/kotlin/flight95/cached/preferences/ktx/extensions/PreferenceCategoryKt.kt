package flight95.cached.preferences.ktx.extensions

import androidx.preference.PreferenceCategory
import flight95.cached.preferences.ktx.CachedPreferenceCategory

@Suppress("unused")
fun PreferenceCategory.getCategory(): CachedPreferenceCategory =
    when (key) {
        null -> throw IllegalStateException("Preference $title key was not valid.")
        else -> CachedPreferenceCategory(this)
    }