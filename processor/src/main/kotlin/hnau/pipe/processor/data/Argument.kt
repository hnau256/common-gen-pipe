package hnau.pipe.processor.data

import com.google.devtools.ksp.symbol.KSType

internal data class Argument(
    val name: String,
    val type: KSType,
)