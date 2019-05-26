package com.github.johnnysc.spacex.data

import com.github.johnnysc.domain.validator.Validator
import com.github.johnnysc.domain.validator.YearValidator
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

/**
 * @author Asatryan on 20.05.19
 */
@RunWith(BlockJUnit4ClassRunner::class)
class YearValidatorTest {

    private lateinit var validator: Validator<String?>

    @Before
    fun setUp() {
        validator = YearValidator()
    }

    @Test
    fun test_validator_not_matching_cases() {
        val list = notMatchingCasesList()
        list.forEach {
            assertNull(validator.isValid(it))
        }
    }

    @Test
    fun test_validator_negative() {
        val list = invalidCasesList()
        list.forEach {
            assertTrue(validator.isValid(it) == false)
        }
    }

    @Test
    fun test_validator_positive() {
        val list = validCasesList()
        list.forEach {
            assertTrue(validator.isValid(it) == true)
        }

    }

    private fun notMatchingCasesList() = listOf(null as String?, "", "1", "12", "123", "a", "wf", "22f", "_$@")

    private fun invalidCasesList() =
        listOf("-20200", "123456", "not a number", "door", "302.3", "2.32", "2.4f", "null", "123L", "234,23", "2020")

    private fun validCasesList() =
        listOf("2001", "1234", "2019", "2007", "1991", "1999", "2014", "2004", "1000", "1040", "1912", "2010")
}