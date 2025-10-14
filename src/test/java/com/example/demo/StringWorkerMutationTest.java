package com.example.demo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;
public class StringWorkerMutationTest {
        private StringWorker worker;

        @BeforeEach
        void setUp() {
            worker = new StringWorker();
        }

        // ===============================================
        // МУТАЦИИ ДЛЯ isPalindrome()
        // ===============================================

        /**
         * МУТАЦИЯ 1.1: Инверсия условия сравнения
         * Раскомментируйте код в StringWorker.isPalindrome()
         * if(str.charAt(i) == str.charAt(right)) { return false; }
         */
        @Test
        @Disabled("Раскомментируйте мутацию 1.1 в StringWorker")
        void mutationTestIsPalindromeInvertedCondition() {
            // Эти тесты должны УПАСТЬ при мутации
            assertTrue(worker.isPalindrome("madam"));  // Должен быть true, но вернет false
            assertTrue(worker.isPalindrome("abba"));

            // Этот тест должен пройти
            assertFalse(worker.isPalindrome("hello"));
        }

        /**
         * МУТАЦИЯ 1.2: Всегда возвращает true
         * Раскомментируйте в StringWorker.isPalindrome(): return true;
         */
        @Test
        @Disabled("Раскомментируйте мутацию 1.2 в StringWorker")
        void mutationTestIsPalindromeAlwaysTrue() {
            // Этот тест должен УПАСТЬ
            assertFalse(worker.isPalindrome("hello"));  // Должен быть false, но вернет true
        }

        // ===============================================
        // МУТАЦИИ ДЛЯ quantityOfSymbols()
        // ===============================================

        /**
         * МУТАЦИЯ 2.1: +1 к длине
         * Раскомментируйте в StringWorker.quantityOfSymbols(): return str.length() + 1;
         */
        @Test
        @Disabled("Раскомментируйте мутацию 2.1 в StringWorker")
        void mutationTestQuantityPlusOne() {
            assertEquals(5, worker.quantityOfSymbols("hello"));  // Должен упасть: вернет 6
            assertEquals(0, worker.quantityOfSymbols(""));       // Должен упасть: вернет 1
        }

        /**
         * МУТАЦИЯ 2.2: -1 от длины
         * Раскомментируйте в StringWorker.quantityOfSymbols(): return Math.max(0, str.length() - 1);
         */
        @Test
        @Disabled("Раскомментируйте мутацию 2.2 в StringWorker")
        void mutationTestQuantityMinusOne() {
            assertEquals(5, worker.quantityOfSymbols("hello"));  // Должен упасть: вернет 4
        }

        // ===============================================
        // МУТАЦИИ ДЛЯ reverseWords()
        // ===============================================

        /**
         * МУТАЦИЯ 3.1: Прямой порядок слов (НЕ реверс)
         * Раскомментируйте в StringWorker.reverseWords(): for(int i = 0; i < words.length; i++)
         */
        @Test
        @Disabled("Раскомментируйте мутацию 3.1 в StringWorker")
        void mutationTestReverseWordsNoReverse() {
            assertEquals("world hello", worker.reverseWords("hello world"));  // Упадет: вернет "hello world"
        }

        /**
         * МУТАЦИЯ 3.2: Без trim()
         * Раскомментируйте в StringWorker.reverseWords(): String[] words = s.split("\\s+");
         */
        @Test
        @Disabled("Раскомментируйте мутацию 3.2 в StringWorker")
        void mutationTestReverseWordsNoTrim() {
            assertEquals("world hello", worker.reverseWords("  hello   world  "));  // Упадет из-за лишних пробелов
        }

        /**
         * МУТАЦИЯ 3.3: Лишний пробел в конце
         * Раскомментируйте в StringWorker.reverseWords(): res.append(" "); в конце цикла
         */
        @Test
        @Disabled("Раскомментируйте мутацию 3.3 в StringWorker")
        void mutationTestReverseWordsExtraSpace() {
            assertEquals("world hello", worker.reverseWords("hello world"));  // Упадет: вернет "world hello "
        }

        // ===============================================
        // МУТАЦИИ ДЛЯ repeatedSubstringPattern()
        // ===============================================

        /**
         * МУТАЦИЯ 4.1: Инверсия результата
         * Раскомментируйте в StringWorker.repeatedSubstringPattern(): return !sub.contains(s);
         */
        @Test
        @Disabled("Раскомментируйте мутацию 4.1 в StringWorker")
        void mutationTestPatternInvertedResult() {
            assertTrue(worker.repeatedSubstringPattern("abab"));   // Упадет: вернет false
            assertFalse(worker.repeatedSubstringPattern("abcd"));  // Упадет: вернет true
        }

        /**
         * МУТАЦИЯ 4.2: Неправильная подстрока
         * Раскомментируйте в StringWorker: String sub = doubled.substring(0, 2 * n);
         */
        @Test
        @Disabled("Раскомментируйте мутацию 4.2 в StringWorker")
        void mutationTestPatternWrongSubstring() {
            assertTrue(worker.repeatedSubstringPattern("abcabcabc"));  // Может упасть
        }

        /**
         * МУТАЦИЯ 4.3: Изменение условия длины
         * Раскомментируйте: if(n < 1) return false; // только для пустой строки
         */
        @Test
        @Disabled("Раскомментируйте мутацию 4.3 в StringWorker")
        void mutationTestPatternLengthCondition() {
            assertFalse(worker.repeatedSubstringPattern("a"));  // Может неожиданно пройти
        }

        // ===============================================
        // МУТАЦИИ ДЛЯ stringIsNUll()
        // ===============================================

        /**
         * МУТАЦИЯ 5.1: Инверсия логики
         * Раскомментируйте в StringWorker.stringIsNUll(): return false; в if
         */
        @Test
        @Disabled("Раскомментируйте мутацию 5.1 в StringWorker")
        void mutationTestStringIsNullInverted() {
            assertTrue(worker.stringIsNUll(null));     // Упадет: вернет false
            assertTrue(worker.stringIsNUll(""));       // Упадет: вернет false
            assertFalse(worker.stringIsNUll("hello")); // Упадет: вернет true
        }

        /**
         * МУТАЦИЯ 5.2: Без проверки null
         * Раскомментируйте: if(str.isEmpty()) return true; без null проверки
         */
        @Test
        @Disabled("Раскомментируйте мутацию 5.2 в StringWorker")
        void mutationTestStringIsNullNoNullCheck() {
            // Этот тест должен вызвать NullPointerException
            assertTrue(worker.stringIsNUll(null));
        }

        // ===============================================
        // КАК ПРОВОДИТЬ ТЕСТИРОВАНИЕ
        // ===============================================

        /**
         * ИНСТРУКЦИЯ ПО ТЕСТИРОВАНИЮ:
         *
         * 1. Раскомментируйте ОДНУ мутацию в StringWorker.java
         * 2. Раскомментируйте соответствующий тест в этом классе
         * 3. Запустите тест
         * 4. ОЖИДАЕМЫЙ РЕЗУЛЬТАТ: ТЕСТ ДОЛЖЕН УПАСТЬ (killed mutant)
         * 5. Если тест ПРОШЕЛ - это "выживший мутант" (survived mutant)
         * 6. Верните исходный код и переходите к следующей мутации
         *
         * ПРИМЕР МУТАЦИИ В StringWorker.isPalindrome():
         *
         * public boolean isPalindrome(String str) {
         *     int right = str.length() - 1;
         *     // МУТАЦИЯ 1.1 - РАСКОММЕНТИРУЙТЕ:
         *     // if(str.charAt(i) == str.charAt(right)) { return false; }
         *
         *     for(int i = 0; i < str.length(); i++) {
         *         if(str.charAt(i) != str.charAt(right)) {  // ИСХОДНЫЙ КОД
         *             return false;
         *         }
         *         right--;
         *     }
         *     return true;
         * }
         */

    }

