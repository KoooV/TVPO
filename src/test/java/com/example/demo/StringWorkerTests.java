package com.example.demo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class StringWorkerTests {
    private StringWorker worker;

    @BeforeEach
    void setUp(){
        worker = new StringWorker();
    }

    @Test
    void testIsPalindromeTrue() {
        assertTrue(worker.isPalindrome("madam"));
        assertTrue(worker.isPalindrome("abba"));
    }

    @Test
    void testIsPalindromeFalse() {
        assertFalse(worker.isPalindrome("hello"));
        assertFalse(worker.isPalindrome("java"));
    }

    // --- quantityOfSymbols() ---
    @Test
    void testQuantityOfSymbols() {
        assertEquals(5, worker.quantityOfSymbols("hello"));
        assertEquals(0, worker.quantityOfSymbols(""));
    }

    // --- reverseWords() ---
    @Test
    void testReverseWords() {
        assertEquals("world hello", worker.reverseWords("hello world"));
        assertEquals("coding love I", worker.reverseWords("I love coding"));
    }

    @Test
    void testReverseWordsWithExtraSpaces() {
        assertEquals("world hello", worker.reverseWords("  hello   world  "));
    }

    // --- repeatedSubstringPattern() ---
    @Test
    void testRepeatedSubstringPatternTrue() {
        assertTrue(worker.repeatedSubstringPattern("abab"));
        assertTrue(worker.repeatedSubstringPattern("abcabcabc"));
    }

    @Test
    void testRepeatedSubstringPatternFalse() {
        assertFalse(worker.repeatedSubstringPattern("aba"));
        assertFalse(worker.repeatedSubstringPattern("abcd"));
        assertFalse(worker.repeatedSubstringPattern("a"));
    }

    // --- stringIsNUll() ---
    @Test
    void testStringIsNullOrEmpty() {
        assertTrue(worker.stringIsNUll(null));
        assertTrue(worker.stringIsNUll(""));
    }

    @Test
    void testStringIsNotNull() {
        assertFalse(worker.stringIsNUll("hello"));
    }


}
