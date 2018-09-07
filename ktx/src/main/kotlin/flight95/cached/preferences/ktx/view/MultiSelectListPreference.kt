package flight95.cached.preferences.ktx.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.TypedArrayUtils
import flight95.cached.preferences.ktx.R

@Suppress("unused", "Recycle")
class MultiSelectListPreference(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : androidx.preference.MultiSelectListPreference(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val DELIMITERS = "|"
    }

    constructor(context: Context) : this(context, null)

    @Suppress("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?) :
        this(
            context,
            attrs,
            TypedArrayUtils.getAttr(
                context,
                androidx.preference.R.attr.dialogPreferenceStyle,
                android.R.attr.dialogPreferenceStyle))

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)

    private var autoSummary = false
    private var default: Set<String>? = null

    init {
        context
            .obtainStyledAttributes(attrs, R.styleable.CachedPreference, defStyleAttr, defStyleRes)
            .apply {
                setDefaultValue(
                    mutableSetOf<String>()
                        .apply {
                            getString(R.styleable.CachedPreference_defaultIndices)
                                ?.split(DELIMITERS)
                                ?.forEach { add(entryValues[it.toInt()].toString()) }
                        })
                autoSummary = getBoolean(R.styleable.CachedPreference_autoSummary, false)
            }
            .recycle()

        if (autoSummary) setSummaryFromValue(default)
    }

    @Suppress("UNCHECKED_CAST")
    override fun setDefaultValue(defaultValue: Any?) {
        super.setDefaultValue(defaultValue)
        default = defaultValue as Set<String>?
    }

    @Suppress("UNCHECKED_CAST")
    override fun callChangeListener(newValue: Any?): Boolean =
        super.callChangeListener(newValue).apply {
            if (this && autoSummary) setSummaryFromValue(newValue as Set<String>?)
        }

    private fun setSummaryFromValue(value: Set<String>? = null) {
        summary = value
            ?.sorted()
            ?.map { findIndexOfValue(it) }
            ?.joinToString(" $DELIMITERS ") { entries[it] }
            ?: " "
    }
}