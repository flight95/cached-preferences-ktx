package flight95.cached.preferences.ktx.extensions

import androidx.preference.Preference
import androidx.preference.PreferenceGroup

@Suppress("unused")
fun PreferenceGroup.getPreferences(): Set<Preference> =
    mutableSetOf<Preference>().apply {
        for (index in 0 until preferenceCount) {
            add(getPreference(index))
        }
    }