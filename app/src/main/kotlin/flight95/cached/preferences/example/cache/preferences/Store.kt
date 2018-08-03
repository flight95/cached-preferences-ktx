package flight95.cached.preferences.example.cache.preferences

import flight95.cached.preferences.ktx.CachedPreferencesCategory
import flight95.cached.preferences.ktx.CachedPreferencesKey
import flight95.cached.preferences.ktx.CachedPreferencesStore

object Store : CachedPreferencesStore {

    // TODO: Add code when cached preference category will be added.
    override val categories: Set<CachedPreferencesCategory> = setOf(
        Category.A,
        Category.B)

    // Using sealed class, because nested enum class was deprecated.
    sealed class Category(override val value: String) : CachedPreferencesCategory {

        object A : Category("A") {

            override val keys: Set<CachedPreferencesKey> = A.Key.values().toSet()

            // TODO: Add code when cached preference key of category A will be added.
            enum class Key(override val value: String) : CachedPreferencesKey {
                A1("A1"),
                A2("A2")
            }
        }

        object B : Category("B") {

            override val keys: Set<CachedPreferencesKey> = B.Key.values().toSet()

            // TODO: Add code when cached preference key of category B will be added.
            enum class Key(override val value: String) : CachedPreferencesKey {
                B1("B1"),
                B2("B2")
            }
        }
    }
}
