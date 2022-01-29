package com.ipsoft.wordguess

import com.ipsoft.wordguess.domain.core.extension.removeAccents
import org.junit.Assert
import org.junit.jupiter.api.Test

class StringTest {

    @Test
    fun `when given a accented string should return his no accent version`() {

        val strings = listOf(
            "média" to "media",
            "cása" to "casa",
            "seção" to "secao",
            "avé" to "ave",
            "árvore" to "arvore",
            "ä" to "a",
            "peão" to "peao",
            "leão" to "leao",
            "lista" to "lista",
            "lápis" to "lapis"
        )

        strings.forEach {
            Assert.assertEquals(it.second, it.first.removeAccents())
        }


    }

}