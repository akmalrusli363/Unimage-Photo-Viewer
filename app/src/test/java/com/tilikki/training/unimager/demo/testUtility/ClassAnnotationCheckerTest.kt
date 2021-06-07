package com.tilikki.training.unimager.demo.testUtility

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnitRunner

class ClassAnnotationCheckerTest {
    @Test
    fun testIsSubclass_subClass_true() {
        val classChecker: ClassAnnotationChecker<MyUnitTest> = ClassAnnotationChecker(
            MyUnitTest::class.java,
            DemoNoRunWithUnitTest::class.java
        )
        Assert.assertTrue(classChecker.isSubclass())
    }

    @Test
    fun testIsSubclass_superClass_false() {
        val classChecker: ClassAnnotationChecker<MyUnitTest> = ClassAnnotationChecker(
            MyUnitTest::class.java,
            MyUnitTest::class.java
        )
        Assert.assertFalse(classChecker.isSubclass())
    }

    @Test
    fun hasRunWithAnnotation_noRunWith() {
        val classChecker: ClassAnnotationChecker<MyUnitTest> = ClassAnnotationChecker(
            MyUnitTest::class.java,
            DemoNoRunWithUnitTest::class.java
        )
        Assert.assertFalse(classChecker.hasRunWithAnnotation())
    }

    @Test
    fun hasRunWithAnnotation_includeRunWith() {
        val classChecker: ClassAnnotationChecker<MyUnitTest> = ClassAnnotationChecker(
            MyUnitTest::class.java,
            DemoJUnit4UnitTest::class.java
        )
        Assert.assertTrue(classChecker.hasRunWithAnnotation())
    }

    @Test
    fun hasRunWithAnnotationParameter_wrongParameter() {
        val classChecker: ClassAnnotationChecker<MyUnitTest> = ClassAnnotationChecker(
            MyUnitTest::class.java,
            DemoJUnit4UnitTest::class.java
        )
        Assert.assertFalse(classChecker.hasRunWithAnnotationParameter(MockitoJUnitRunner::class))
    }

    @Test
    fun hasRunWithAnnotationParameter_correctParameter() {
        val classChecker: ClassAnnotationChecker<MyUnitTest> = ClassAnnotationChecker(
            MyUnitTest::class.java,
            DemoJUnit4UnitTest::class.java
        )
        Assert.assertTrue(classChecker.hasRunWithAnnotationParameter(JUnit4::class))
    }

    private abstract class MyUnitTest

    @RunWith(JUnit4::class)
    private class DemoJUnit4UnitTest : MyUnitTest()

    private class DemoNoRunWithUnitTest : MyUnitTest()
}