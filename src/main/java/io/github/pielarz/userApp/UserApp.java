package io.github.pielarz.userApp;

import io.github.pielarz.dao.SolutionDao;
import io.github.pielarz.dao.UserDao;
import io.github.pielarz.model.Solution;
import io.github.pielarz.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserApp
{
    private static Scanner scan = new Scanner(System.in);
    public static void main( String[] args )
    {
        if (args.length < 1) {
            System.out.println("Usage: UserApp [id]");
            System.out.println(" id - id użytkownika");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println(args[1] + " - nie jest poprawnym id");
            System.out.println("Usage: Main [id]");
            System.out.println(" id - id użytkownika");
            return;
        }
        User user = UserDao.read(id);
        if (user == null) {
            System.out.println(args[0] + " - nie jest poprawnym id");
            System.out.println("Usage: Main [id]");
            System.out.println(" id - id użytkownika");
            return;
        }

        String action = null;
        do {
            action = printInvitation();
            switch (action) {
                case "add":
                    printAdd(user);
                    break;
                case "view":
                    printView(user);
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

    private static void printAdd(User user) {
        System.out.println("Lista zadań bez rozwiązań");
        Solution[] solutions = SolutionDao.findAllByUserId(user.getId());
        for (Solution solution : solutions)
            if (solution.getDescription() == null || solution.getDescription().isEmpty())
                System.out.println("[" + solution.getId() + "]" + solution.getExercise().getTitle());
        Integer solutionId = null;
        do {
            System.out.println("Które zadanie chcesz rozwiązać?");
            try {
                solutionId = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (solutionId == null);
        Solution solution = SolutionDao.read(solutionId);
        System.out.println("Podaj rozwiązanie dla zadania: " + solution.getId() + " " + solution.getExercise().getTitle());
        System.out.println(solution.getExercise().getDescription());
        String description = scan.nextLine();
        solution.setDescription(description);
        SolutionDao.update(solution);
        System.out.println("Zapisano rozwiązanie!");
    }

    private static void printView(User user) {
        Solution[] solutions = SolutionDao.findAllByUserId(user.getId());
        for(Solution solution : solutions) {
            System.out.println("Zadanie: " + "[" + solution.getExercise().getId() + "]" + solution.getExercise().getTitle() + ": " + solution.getExercise().getDescription());
            System.out.println("Rozwiązanie: " + solution.getDescription());
        }
    }
}
