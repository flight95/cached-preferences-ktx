package flight95.cached.preferences.ktx.extensions

import androidx.annotation.StringRes
import androidx.preference.Preference

@Suppress("unused")
@StringRes
fun Preference.getKeyId(): Int =
    when (key) {
        null -> throw IllegalStateException("Preference $title key was not valid.")
        else -> context.resources.getIdentifier(key, "string", context.applicationContext.packageName)
    }.run {
        when (this) {
            0 -> throw IllegalStateException("Preference $title key was not string resource.")
            else -> this
        }
    }
