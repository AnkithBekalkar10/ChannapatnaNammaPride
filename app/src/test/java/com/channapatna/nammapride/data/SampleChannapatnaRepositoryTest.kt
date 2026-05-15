package com.channapatna.nammapride.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import kotlinx.coroutines.test.runTest

class SampleChannapatnaRepositoryTest {
    private val repository = SampleChannapatnaRepository()

    @Test
    fun verifyToyReturnsProfileForKnownSixDigitId() = runTest {
        val result = repository.verifyToy("123456")

        assertNotNull(result)
        assertEquals("Rainbow Galloper", result?.toy?.name)
        assertEquals("Rangappa K.M.", result?.artisan?.name)
    }

    @Test
    fun verifyToyReturnsNullForUnknownId() = runTest {
        assertNull(repository.verifyToy("999999"))
    }
}
