package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        StringWorker worker = new StringWorker();

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 - Проверить, является ли строка палиндромом");
            System.out.println("2 - Посчитать количество символов в строке");
            System.out.println("3 - Развернуть порядок слов в строке");
            System.out.println("4 - Проверить повторяющийся шаблон подстроки");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    System.out.print("Введите строку: ");
                    String input = scanner.nextLine();
                    boolean result = worker.isPalindrome(input);
                    System.out.println(result ? "Строка является палиндромом" : "Строка не является палиндромом");
                    break;
                }
                case "2": {
                    System.out.print("Введите строку: ");
                    String input = scanner.nextLine();
                    int count = worker.quantityOfSymbols(input);
                    System.out.println("Количество символов: " + count);
                    break;
                }
                case "3": {
                    System.out.print("Введите строку: ");
                    String input = scanner.nextLine();
                    String reversed = worker.reverseWords(input);
                    System.out.println("Результат: " + reversed);
                    break;
                }
                case "4": {
                    System.out.print("Введите строку: ");
                    String input = scanner.nextLine();
                    boolean pattern = worker.repeatedSubstringPattern(input);
                    System.out.println(pattern ? "Строка состоит из повторяющегося шаблона" : "Шаблон повторов не обнаружен");
                    break;
                }
                case "0": {
                    System.out.println("Выход...");
                    scanner.close();
                    System.exit(0);
                    return;
                }
                default: {
                    System.out.println("Некорректный выбор. Повторите попытку.");
                }
            }
        }
    }
}
