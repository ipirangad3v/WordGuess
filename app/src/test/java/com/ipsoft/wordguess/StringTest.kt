package com.ipsoft.wordguess

import com.ipsoft.wordguess.domain.core.extension.removeAccents
import org.junit.Assert
import org.junit.jupiter.api.Test

class StringTest {

    @Test
    fun `when passed a accented string should return his no accent version`() {

        val accentString = "média"
        val expectedString = "media"

        Assert.assertEquals(accentString.removeAccents(), expectedString)
    }

}