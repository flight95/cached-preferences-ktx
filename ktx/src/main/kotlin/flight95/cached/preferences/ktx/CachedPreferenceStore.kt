package flight95.cached.preferences.ktx

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.preference.PreferenceManager
import flight95.cached.preferences.ktx.extensions.getStore

@Suppress("unused")
data class CachedPreferenceStore(val categories: Set<CachedPreferenceCategory>) {

    companion object {

        @SuppressLint("RestrictedApi")
        fun make(context: Context, @XmlRes resId: Int) =
            PreferenceManager(context)
                .run { inflateFromResource(context, resId, null) }
                .getStore()

        fun make(preferenceManager: PreferenceManager) = preferenceManager.preferenceScreen.getStore()
    }

    fun getCategory(@StringRes id: Int): CachedPreferenceCategory =
        categories.firstOrNull { it.id == id }
            ?: throw IllegalStateException("Could not find $id preference category.")

    fun getPreference(@StringRes id: Int): CachedPreference {
        categories.forEach { category ->
            category.getPreference(id)?.let {
                return it
            }
        }
        throw IllegalStateException("Could not find $id preference.")
    }
}