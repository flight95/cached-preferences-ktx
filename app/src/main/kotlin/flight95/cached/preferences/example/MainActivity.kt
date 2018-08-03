package flight95.cached.preferences.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import flight95.cached.preferences.example.cache.preferences.Store
import flight95.cached.preferences.ktx.CachedPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO: DI was the best way.
    private val cachedCategoryA: CachedPreferences by lazy {
        Store.Category.A.make(this)
    }

    // TODO: DI was the best way.
    private val cachedCategoryB: CachedPreferences by lazy {
        Store.Category.B.make(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Store.clear(this)
        loadKeys()

        store.setOnClickListener {
            Store.clear(this)
            loadKeys()
        }

        with(key_a1_text) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) return@setOnFocusChangeListener
                cachedCategoryA.set(Store.Category.A.Key.A1, text.toString()).apply()
            }
        }

        with(key_a2_text) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) return@setOnFocusChangeListener
                cachedCategoryA.set(Store.Category.A.Key.A2, text.toString().toIntOrNull()).apply()
            }
        }

        category_a.setOnClickListener {
            cachedCategoryA.clear().apply()
            loadKeys()
        }

        with(key_b1_text) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) return@setOnFocusChangeListener
                cachedCategoryB.set(Store.Category.B.Key.B1, text.toString()).apply()
            }
        }

        with(key_b2_text) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) return@setOnFocusChangeListener
                cachedCategoryB.set(Store.Category.B.Key.B2, text.toString().toBooleanOrNull()).apply()
            }
        }

        category_b.setOnClickListener {
            cachedCategoryB.clear().apply()
            loadKeys()
        }
    }

    private fun loadKeys() {
        loadKeyA1()
        loadKeyA2()
        loadKeyB1()
        loadKeyB2()
    }

    private fun loadKeyA1() =
        key_a1_text.setText(cachedCategoryA.get<String>(Store.Category.A.Key.A1) ?: "")

    private fun loadKeyA2() =
        key_a2_text.setText(cachedCategoryA.get<Int>(Store.Category.A.Key.A2)?.toString() ?: "")

    private fun loadKeyB1() =
        key_b1_text.setText(cachedCategoryB.get<String>(Store.Category.B.Key.B1) ?: "")

    private fun loadKeyB2() =
        key_b2_text.setText(cachedCategoryB.get<Boolean>(Store.Category.B.Key.B2)?.toString() ?: "")
}

private fun String.toBooleanOrNull(): Boolean? =
    try {
        toBoolean()
    } catch (e: Throwable) {
        null
    }
