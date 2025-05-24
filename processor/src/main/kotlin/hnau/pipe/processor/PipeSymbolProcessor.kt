package hnau.pipe.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import hnau.pipe.processor.impl.InterfaceToImplement
import hnau.pipe.processor.impl.create
import hnau.pipe.processor.impl.implement
import hnau.pipe.processor.utils.PipeAnnotationClassInfo

class PipeSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(
        resolver: Resolver,
    ): List<KSAnnotated> {

        val annotateds = resolver
            .getSymbolsWithAnnotation(PipeAnnotationClassInfo.nameWithPackage)
            .groupBy { it.validate() }

        val interfacesToImplement = annotateds[true]
            ?.map { annotated ->
                InterfaceToImplement.create(
                    annotated = annotated,
                )
            }
            ?: emptyList()

        interfacesToImplement.implement(
            codeGenerator = codeGenerator,
            resolver = resolver,
            logger = logger,
        )

        return annotateds[false] ?: emptyList()
    }
}