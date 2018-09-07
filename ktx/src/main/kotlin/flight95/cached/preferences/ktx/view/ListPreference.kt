package flight95.cached.preferences.ktx.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.TypedArrayUtils
import flight95.cached.preferences.ktx.R

@Suppress("unused", "Recycle")
class ListPreference(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : androidx.preference.ListPreference(context, attrs, defStyleAttr, defStyleRes) {

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

    private lateinit var persistType: PersistType
    private var autoSummary = false
    private var default: String? = null

    init {
        context
            .obtainStyledAttributes(attrs, R.styleable.CachedPreference, defStyleAttr, defStyleRes)
            .apply {
                setDefaultValue(entryValues[getInt(R.styleable.CachedPreference_defaultIndex, 0)])
                autoSummary = getBoolean(R.styleable.CachedPreference_autoSummary, false)
                persistType = getInt(R.styleable.CachedPreference_persistType, PersistType.String.value).getPersistType()
            }
            .recycle()

        if (autoSummary) setSummaryFromValue(default)
    }

    override fun persistString(value: String?): Boolean =
        when (persistType) {
            PersistType.Boolean -> throw IllegalStateException("ListPreference was not supported Boolean persist type.")
            PersistType.Int -> persistInt(value?.toInt() ?: 0)
            PersistType.Long -> persistLong(value?.toLong() ?: 0L)
            PersistType.Float -> persistFloat(value?.toFloat() ?: 0f)
            PersistType.String -> super.persistString(value ?: "")
            PersistType.Set -> throw IllegalStateException("ListPreference was not supported Set persist type.")
        }

    override fun getPersistedString(default: String?): String =
        when (persistType) {
            PersistType.Boolean -> throw IllegalStateException("ListPreference was not supported Boolean persist type.")
            PersistType.Int -> getPersistedInt(default?.toInt() ?: 0).toString()
            PersistType.Long -> getPersistedLong(default?.toLong() ?: 0L).toString()
            PersistType.Float -> getPersistedFloat(default?.toFloat() ?: 0f).toString()
            PersistType.String -> super.getPersistedString(default ?: "")
            PersistType.Set -> throw IllegalStateException("ListPreference was not supported Set persist type.")
        }

    override fun setDefaultValue(defaultValue: Any?) {
        super.setDefaultValue(defaultValue)
        default = defaultValue as String?
    }

    override fun callChangeListener(newValue: Any?): Boolean =
        super.callChangeListener(newValue).apply {
            if (this && autoSummary) setSummaryFromValue(newValue as String)
        }

    private fun setSummaryFromValue(value: String? = null) {
        summary = entries[findIndexOfValue(value)] ?: " "
    }
}