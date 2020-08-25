package io.github.pielarz.admin;

import io.github.pielarz.dao.ExerciseDao;
import io.github.pielarz.dao.SolutionDao;
import io.github.pielarz.dao.UserDao;
import io.github.pielarz.model.Exercise;
import io.github.pielarz.model.Solution;
import io.github.pielarz.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SolutionApp {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String action = null;
        do {
            action = printInvitation();
            switch (action) {
                case "add":
                    printAdd();
                    break;
                case "view":
                    printView();
                    break;
            }
        } while (!("quit").equals(action));
    }

    private static String printInvitation() {
        String action = null;
        do {
            System.out.println("Wybierz jedną z opcji: [a]dd, [v]iew, [q]uit :");
            String response = scan.nextLine();
            switch (response.charAt(0)) {
                case 'a':
                    action = "add";
                    break;
                case 'v':
                    action = "view";
                    break;
                case 'q':
                    action = "quit";
                    break;
                default:
                    System.out.println("błędna odpowiedz: " + response);
            }
        } while (action == null);
        return action;
    }

    private static void printAdd() {
        System.out.println("Lista użytkowników:");
        User[] users = UserDao.findAll();
        for (User user : users)
            System.out.println("[" + user.getId() + "]" + user.getUserName());

        Integer userId = null;
        do {
            System.out.println("Któremu użytkownikowi chesz dodać zadanie?");
            try {
                userId = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (userId == null);
        System.out.println("Lista zadań:");
        Exercise[] exercises = ExerciseDao.findAll();
        for (Exercise exercise : exercises)
            System.out.println("[" + exercise.getId() + "]" + exercise.getTitle());
        Integer exerciseId = null;
        do {
            System.out.println("Które zadanie chesz dodać?");
            try {
                exerciseId = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (userId == null);
        SolutionDao.create(new Solution(UserDao.read(userId), ExerciseDao.read(exerciseId)));
        System.out.println("Przypisano zadanie!");
    }

    private static void printView() {
        System.out.println("Lista użytkowników:");
        User[] users = UserDao.findAll();
        for (User user : users)
            System.out.println("[" + user.getId() + "]" + user.getUserName());
        Integer id = null;
        do {
            System.out.println("Które użytkownika zadania chcesz zobaczyć?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                Solution[] solutions = SolutionDao.findAllByUserId(id);
                for (Solution solution : solutions)
                    System.out.println("["+solution.getId()+"]" + ExerciseDao.read(solution.getExercise().getId()).getTitle() + ": " + solution.getDescription());
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }
}
