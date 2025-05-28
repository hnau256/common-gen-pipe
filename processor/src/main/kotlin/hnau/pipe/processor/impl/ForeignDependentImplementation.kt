package hnau.pipe.processor.impl

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSName
import hnau.pipe.processor.data.Argument
import hnau.pipe.processor.ext.argumants
import hnau.pipe.processor.ext.implementationName
import hnau.pipe.processor.ext.log
import hnau.pipe.processor.ext.qualifiedNameOrThrow

internal data class ForeignDependentImplementation(
    override val impl: KSName,
    override val arguments: List<Argument>,
) : Dependent {

    companion object {

        fun create(
            resolver: Resolver,
            logger: KSPLogger,
            interfaceDeclaration: KSClassDeclaration,
        ): ForeignDependentImplementation {
            val interfaceLog = interfaceDeclaration.qualifiedNameOrThrow.log
            val implementationName = interfaceDeclaration.implementationName
            logger.info("Getting implementation: ${implementationName.log} for pipe segment $interfaceLog")
            val implementation = resolver.getClassDeclarationByName(implementationName)
                ?: error("Unable find implementation (${implementationName.log}) of ${interfaceDeclaration.log}")
            val constructor = implementation
                .primaryConstructor
                ?: error("${implementation.log} has no primary constructor")
            return ForeignDependentImplementation(
                impl = implementationName,
                arguments = constructor.argumants,
            )
        }
    }
}