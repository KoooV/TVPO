package com.example.demo;

public class StringWorker {

    public boolean isPalindrome(String str) {
        int right = str.length() - 1;

        // МУТАЦИЯ 1.1: Замените условие в цикле ниже
        // ИСХОДНОЕ: if(str.charAt(i) != str.charAt(right))
        // МУТАЦИЯ:   if(str.charAt(i) == str.charAt(right))

        for(int i = 0; i < str.length(); i++) {
            // ===== АКТИВНАЯ МУТАЦИЯ 1.1 =====
            // if(str.charAt(i) == str.charAt(right)) {  // ИНВЕРСИЯ!
            if(str.charAt(i) != str.charAt(right)) {
                // ================================
                return false;
            }
            right--;
        }

        // МУТАЦИЯ 1.2: Замените return true; на return false;
        // МУТАЦИЯ 1.3: Удалите весь цикл и оставьте return true;

        return true;
    }

    public int quantityOfSymbols(String str) {
        // МУТАЦИЯ 2.1: Замените на return str.length() + 1;
        // МУТАЦИЯ 2.2: Замените на return Math.max(0, str.length() - 1);

        // ===== АКТИВНАЯ МУТАЦИЯ 2.X =====
        // return str.length() + 1;  // +1 ко всем
        return str.length();
        // ================================
    }

    public String reverseWords(String s) {
        StringBuilder res = new StringBuilder();

        // МУТАЦИЯ 3.1: Удалите .trim()
        String[] words = /*s.*/s.trim().split("\\s+");
        // String[] words = s.split("\\s+");  // БЕЗ TRIM

        // МУТАЦИЯ 3.2: Измените цикл на прямой порядок
        // ИСХОДНОЕ: for(int i = words.length - 1; i >= 0; i--)
        // МУТАЦИЯ:   for(int i = 0; i < words.length; i++)

        // ===== АКТИВНАЯ МУТАЦИЯ 3.2 =====
        // for(int i = 0; i < words.length; i++) {  // ПРЯМОЙ ПОРЯДОК!
        for(int i = words.length - 1; i >= 0; i--) {
            // ================================
            res.append(words[i]);

            // МУТАЦИЯ 3.3: Всегда добавляйте пробел
            // res.append(" ");  // ЛИШНИЙ ПРОБЕЛ В КОНЦЕ

            if(i > 0){
                res.append(" ");
            }
        }
        return res.toString();
    }

    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();

        // МУТАЦИЯ 4.1: Измените условие
        // ИСХОДНОЕ: if(n <= 1)
        // МУТАЦИЯ:   if(n == 0)

        // ===== АКТИВНАЯ МУТАЦИЯ 4.1 =====
        // if(n == 0) { return false; }  // Только для пустой строки
        if(n <= 1){
            return false;
        }
        // ================================

        String doubled = s + s;

        // МУТАЦИЯ 4.2: Измените подстроку
        // ИСХОДНОЕ: doubled.substring(1, 2 * n - 1)
        // МУТАЦИЯ:   doubled.substring(0, 2 * n)

        // ===== АКТИВНАЯ МУТАЦИЯ 4.2 =====
        // String sub = doubled.substring(0, 2 * n);  // НЕПРАВИЛЬНАЯ ПОДСТРОКА
        String sub = doubled.substring(1, 2 * n - 1);
        // ================================

        // МУТАЦИЯ 4.3: Инвертируйте результат
        // return !sub.contains(s);

        // ===== АКТИВНАЯ МУТАЦИЯ 4.3 =====
        // return !sub.contains(s);  // ИНВЕРСИЯ!
        return sub.contains(s);
        // ================================
    }

    public boolean stringIsNUll(String str) {
        // МУТАЦИЯ 5.1: Инвертируйте логику
        // ИСХОДНОЕ: return true;  в if
        // МУТАЦИЯ:   return false; в if

        // ===== АКТИВНАЯ МУТАЦИЯ 5.1 =====
        // if(str == null || str.isEmpty()) {
        //     return false;  // ИНВЕРСИЯ ЛОГИКИ!
        // }
        // return true;
        // ================================

        // МУТАЦИЯ 5.2: Удалите проверку null
        // if(str.isEmpty()) { return true; }
        // return false;

        if(str == null || str.isEmpty()){
            return true;
        }
        return false;
    }
}