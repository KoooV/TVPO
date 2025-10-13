package com.example.demo;

public class StringWorker{
    public boolean isPalindrome(String str) {
        int right = str.length() - 1;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != str.charAt(right)) {
                return false;
            }
            right--;
        }
        return true;
    }

    public int quantityOfSymbols(String str) {
        return str.length();
    }

    public String reverseWords(String s) {
        String[] words = s.trim().split("\\s+");
        StringBuilder res = new StringBuilder();
        for(int i = words.length - 1; i >= 0; i--){
            res.append(words[i]);
            if(i > 0){
                res.append(" ");
            }
        }
        return res.toString();
    }

    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        if(n <= 1){
            return false;
        }

        String doubled = s + s;
        String sub = doubled.substring(1, 2 * n - 1);
        return sub.contains(s);
    }

    public boolean stringIsNUll(String str) {
        if(str == null || str.isEmpty()){
            return true;
        }
        return false;
    }
}

