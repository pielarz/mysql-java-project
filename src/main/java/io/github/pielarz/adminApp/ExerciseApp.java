package io.github.pielarz.adminApp;

import io.github.pielarz.dao.ExerciseDao;
import io.github.pielarz.model.Exercise;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExerciseApp {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String action = null;
        do {
            action = printInvitation();
            switch (action) {
                case "add":
                    printAdd();
                    break;
                case "edit":
                    printEdit();
                    break;
                case "delete":
                    printDelete();
                    break;
            }
        } while (!("quit").equals(action));
    }

    private static String printInvitation() {
        String action = null;
        System.out.println("Lista zadań:");
        Exercise[] exercises = ExerciseDao.findAll();
        for (Exercise exercise : exercises)
            System.out.println("["+exercise.getId()+"] " + exercise.getTitle());

        do {
            System.out.println("Wybierz jedną z opcji: [a]dd, [e]dit, [d]elete, [q]uit :");
            String response = scan.nextLine();
            switch (response.charAt(0)) {
                case 'a':
                    action = "add";
                    break;
                case 'e':
                    action = "edit";
                    break;
                case 'd':
                    action = "delete";
                    break;
                case 'q':
                    action = "quit";
                    break;
                default:
                    System.out.println("Błędna odpowiedz: " + response);
            }
        } while (action == null);
        return action;
    }

    private static void printAdd() {
        System.out.println("Podaj dane zadania");
        System.out.println("Podaj tytuł:");
        String title = scan.nextLine();
        System.out.println("Podaj opis:");
        String description = scan.nextLine();
        ExerciseDao.create(new Exercise(title, description));
        System.out.println("Dodano zadanie!");
    }

    private static void printEdit() {
        Integer id = null;
        do {
            System.out.println("Które zadanie chesz edytować?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                System.out.println("Podaj tytuł:");
                String title = scan.nextLine();
                System.out.println("Podaj opis:");
                String description = scan.nextLine();
                Exercise e = ExerciseDao.read(id);
                e.setTitle(title);
                e.setDescription(description);
                ExerciseDao.update(e);
                System.out.println("Zaktualizowano dane zadania!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete() {
        Integer id = null;
        do {
            System.out.println("Które zadanie chcesz usunąć?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                ExerciseDao.delete(id);
                System.out.println("Usunięto zadanie");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }
}
