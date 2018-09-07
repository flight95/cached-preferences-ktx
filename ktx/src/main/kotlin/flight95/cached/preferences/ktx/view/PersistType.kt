package flight95.cached.preferences.ktx.view

enum class PersistType(val value: kotlin.Int) {
    Boolean(0),
    Int(1),
    Long(2),
    Float(3),
    String(4),
    Set(5)
}

fun kotlin.Int.getPersistType(): PersistType =
    PersistType.values().firstOrNull { it.value == this } ?: PersistType.String