package hnau.pipe.processor.impl

import com.google.devtools.ksp.symbol.KSName
import hnau.pipe.processor.data.Argument

internal sealed interface Dependent {

    val impl: KSName

    val arguments: List<Argument>
}