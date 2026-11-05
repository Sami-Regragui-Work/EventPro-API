package com.eventpro.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CodeGeneratorTest {

    @Test
    void randomCodeUsesPrefixAndLength() {
        String code = CodeGenerator.randomCode("TKT-", 10);

        assertEquals(14, code.length());
        assertTrue(code.startsWith("TKT-"));
    }

    @Test
    void randomCodeUsesOnlyAlphabet() {
        String code = CodeGenerator.randomCode("RSV-", 10);
        String suffix = code.substring("RSV-".length());
        Set<Character> alphabetCharacters = CodeGenerator.ALPHABET.chars()
                .mapToObj(alphabetCharacter -> (char) alphabetCharacter)
                .collect(Collectors.toSet());

        for (char codeCharacter : suffix.toCharArray()) {
            assertTrue(alphabetCharacters.contains(codeCharacter), "unexpected character " + codeCharacter);
        }
    }
}
