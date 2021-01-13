package com.company.thilaka;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
        // this is default, no need to
// mention this here. But if you want to change it to class level, then you can do it here.
class MathUtilsTest {
    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    static void beforeAllInit() {
        //Before all and after all methods have to be a static method,
        // because these methods are run before the test class instance is created.
        System.out.println("This needs to run before all");
    }

    @AfterAll
    static void afterAllInit() {
        System.out.println("This needs to run after all");
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();
    }

    @Test
    @DisplayName("Testing add method")
    @Tag("Math")
    void add() {
        int actual = mathUtils.add(1, 1);
        int expected = 2;
        assertEquals(expected, actual, "The add method should add two numbers");
    }

    @Test
    @DisplayName("multiply method")
    @Tag("Math")
    void testMultiply() {
        testReporter.publishEntry("Running " + testInfo.getDisplayName()
                + " with tags " + testInfo.getTags());
        assertAll(
                () -> assertEquals(4, mathUtils.multiply(2, 2)),
                () -> assertEquals(0, mathUtils.multiply(2, 0)),
                () -> assertEquals(-2, mathUtils.multiply(2, -1))
        );
    }

    @RepeatedTest(3) //repeats the test 3 times.
    @Tag("Circle")
    void calculateCircleArea(RepetitionInfo repetitionInfo) {
        double actual = mathUtils.calculateCircleArea(10);
        double expected = 314.1592653589793;
        assertEquals(expected, actual, // sending a lamda expression as an optimization
                () -> "Should return right circle area " + expected
                        + " but returned " + actual + " Failed in "
                        + repetitionInfo.getCurrentRepetition() + "test of "
                        + repetitionInfo.getTotalRepetitions() + " failed");
    }

    @Test
    @Tag("Math")
    void divide() {
        boolean isServerUp = true;
        assumeTrue(isServerUp);// if the value is false, the test case would skip.
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0),
                "Divide by zero should throw");
    }

    @Test
    @Disabled
    @DisplayName("TDD method. Should not run")
    void testDisabled() {
        fail("This test should be disabled");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up....");
    }

    @Nested
    @DisplayName("add method")
    @Tag("Math")
    class AddTest {

        @Test
        @DisplayName("when adding two positive numbers")
        void addPositive() {
            assertEquals(2, mathUtils.add(1, 1), "should return right sum");
        }

        @Test
        @DisplayName("when adding two negative numbers")
        void addNegative() {
            assertEquals(-2, mathUtils.add(-1, -1), "should return right sum");
        }
    }


}