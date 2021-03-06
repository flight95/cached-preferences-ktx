package flight95.cached.preferences.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import flight95.cached.preferences.ktx.CachedPreferenceStore

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    // TODO: DI was the best way.
    private val store: CachedPreferenceStore by lazy {
        CachedPreferenceStore.make(this, R.xml.preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log()

        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment.make())
            .commit()
    }

    private fun log() {

        // check all properties loaded from xml/preferences.xml
        store.categories
            .forEach {
                Log.e(TAG, "load category ${it.key}")
                it.preferences.forEach { preference ->
                    when (preference.id) {
                        R.string.preference_category_edit_text_int,
                        R.string.preference_category_drop_down_int,
                        R.string.preference_category_list_int ->
                            Log.e(TAG, "load preference ${preference.key}: ${preference.get<Int>()}")
                        R.string.preference_category_edit_text_long,
                        R.string.preference_category_drop_down_long,
                        R.string.preference_category_list_long ->
                            Log.e(TAG, "load preference ${preference.key}: ${preference.get<Long>()}")
                        R.string.preference_category_edit_text_float,
                        R.string.preference_category_drop_down_float,
                        R.string.preference_category_list_float ->
                            Log.e(TAG, "load preference ${preference.key}: ${preference.get<Float>()}")
                        R.string.preference_category_edit_text_string,
                        R.string.preference_category_drop_down_string,
                        R.string.preference_category_list_string ->
                            Log.e(TAG, "load preference ${preference.key}: ${preference.get<String>()}")
                        R.string.preference_category_multi_select_list ->
                            Log.e(TAG, "load preference ${preference.key}: ${preference.get<Set<String>>()}")
                        R.string.preference_category_key_f ->
                            Log.e(TAG, "load preference ${preference.key}: ${preference.get<Int>()}")
                        else -> Log.e(TAG, "load preference ${preference.key}: ${preference.get<Boolean>()}")
                    }
                }
            }

        // check get category from string resource.
        store.getCategory(R.string.preference_visible).let { category ->
            Log.e(TAG, "get category ${category.key}")
        }

        // check get property from string resource.
        store.getPreference(R.string.preference_visible_check_box).let { preference ->
            Log.e(TAG, "get preference ${preference.key}: ${preference.get<Boolean>()}")
        }

        // force set value of preference_category_switch.
        store.getPreference(R.string.preference_category_switch).set(true)
    }
}
