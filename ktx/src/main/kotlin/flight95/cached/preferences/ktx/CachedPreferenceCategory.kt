package flight95.cached.preferences.ktx

import androidx.annotation.StringRes
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceGroup
import flight95.cached.preferences.ktx.extensions.getKeyId
import flight95.cached.preferences.ktx.extensions.getPreferences

@Suppress("unused")
data class CachedPreferenceCategory(val preference: PreferenceCategory) {

    @StringRes val id: Int = preference.getKeyId()

    val key: String = preference.key
        ?: throw IllegalStateException("Preference ${preference.title} key was not valid.")

    val preferences: Set<CachedPreference> =
        mutableSetOf<CachedPreference>()
            .apply {
                preference.getPreferences()
                    .forEach { preference ->
                        when {
                            preference.key == null -> throw IllegalStateException("Preference ${preference.title} key was not valid.")
                            preference is PreferenceGroup -> throw IllegalStateException("PreferenceGroup ${preference.title} could not convert CachedPreferenceStore.")
                            else -> add(CachedPreference(preference))
                        }
                    }
            }
            .run {
                when (isNotEmpty()) {
                    true -> this
                    false -> throw IllegalStateException("PreferenceGroup ${preference.title} have no child.")
                }
            }

    fun getPreference(@StringRes id: Int): CachedPreference? = preferences.firstOrNull { it.id == id }
}