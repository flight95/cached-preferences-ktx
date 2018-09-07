package flight95.cached.preferences.ktx.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.content.res.TypedArrayUtils
import androidx.preference.AndroidResources
import flight95.cached.preferences.ktx.R

@Suppress("unused", "Recycle")
class EditTextPreference(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : androidx.preference.EditTextPreference(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context) : this(context, null)

    @Suppress("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?) :
        this(
            context,
            attrs,
            TypedArrayUtils.getAttr(
                context,
                androidx.preference.R.attr.editTextPreferenceStyle,
                AndroidResources.ANDROID_R_EDITTEXT_PREFERENCE_STYLE))

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)

    private lateinit var persistType: PersistType
    private var autoSummary = false
    private var default: String? = null

    init {
        context
            .obtainStyledAttributes(attrs, R.styleable.CachedPreference, defStyleAttr, defStyleRes)
            .apply {
                autoSummary = getBoolean(R.styleable.CachedPreference_autoSummary, false)
                persistType = getInt(R.styleable.CachedPreference_persistType, PersistType.String.value).getPersistType()
            }
            .recycle()

        if (autoSummary) setSummaryFromValue(default)
    }

    override fun persistString(value: String?): Boolean =
        when (persistType) {
            PersistType.Boolean -> throw IllegalStateException("EditTextPreference was not supported Boolean persist type.")
            PersistType.Int -> persistInt(value?.toInt() ?: 0)
            PersistType.Long -> persistLong(value?.toLong() ?: 0L)
            PersistType.Float -> persistFloat(value?.toFloat() ?: 0f)
            PersistType.String -> super.persistString(value ?: "")
            PersistType.Set -> throw IllegalStateException("EditTextPreference was not supported Set persist type.")
        }

    override fun getPersistedString(default: String?): String =
        when (persistType) {
            PersistType.Boolean -> throw IllegalStateException("EditTextPreference was not supported Boolean persist type.")
            PersistType.Int -> getPersistedInt(default?.toInt() ?: 0).toString()
            PersistType.Long -> getPersistedLong(default?.toLong() ?: 0L).toString()
            PersistType.Float -> getPersistedFloat(default?.toFloat() ?: 0f).toString()
            PersistType.String -> super.getPersistedString(default ?: "")
            PersistType.Set -> throw IllegalStateException("EditTextPreference was not supported Set persist type.")
        }

    override fun setDialogLayoutResource(dialogLayoutResId: Int) {
        throw IllegalStateException("EditTextPreference was not supported set dialog layout resource.")
    }

    override fun getDialogLayoutResource(): Int =
        when (persistType) {
            PersistType.Boolean -> throw IllegalStateException("EditTextPreference was not supported Boolean persist type.")
            PersistType.Int,
            PersistType.Long -> R.layout.preference_dialog_edit_text_number_signed
            PersistType.Float -> R.layout.preference_dialog_edit_text_number_decimal
            PersistType.String -> super.getDialogLayoutResource()
            PersistType.Set -> throw IllegalStateException("EditTextPreference was not supported Set persist type.")
        }

    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any =
        super.onGetDefaultValue(a, index).apply { default = this as String }

    override fun callChangeListener(newValue: Any?): Boolean =
        super.callChangeListener(newValue).apply {
            if (this && autoSummary) setSummaryFromValue(newValue as String)
        }

    private fun setSummaryFromValue(value: String? = null) {
        summary = value ?: " "
    }
}