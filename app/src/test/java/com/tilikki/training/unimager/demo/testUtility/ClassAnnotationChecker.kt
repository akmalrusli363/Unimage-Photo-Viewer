package com.tilikki.training.unimager.demo.testUtility

import org.junit.runner.RunWith
import org.junit.runner.Runner
import kotlin.reflect.KClass

class ClassAnnotationChecker<T : Any>(
    private val superclass: Class<T>,
    private val clazz: Class<out T>
) {
    private val runWithClass = RunWith::class.java

    fun isSubclass(): Boolean {
        return superclass == clazz.superclass
    }

    fun hasRunWithAnnotation(): Boolean {
        return clazz.isAnnotationPresent(runWithClass)
    }

    fun hasRunWithAnnotationParameter(runner: KClass<out Runner>): Boolean {
        return clazz.getAnnotation(runWithClass).value == runner
    }
}

