package io.github.pielarz.admin;

import io.github.pielarz.dao.GroupDao;
import io.github.pielarz.dao.UserDao;
import io.github.pielarz.model.Group;
import io.github.pielarz.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserApp {
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
        System.out.println("Lista użytkowników:");
        User[] users = UserDao.findAll();
        for (User user : users)
            System.out.println("["+user.getId()+"] " + user.getUserName() + " [" + user.getEmail() + "]");

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
                    System.out.println("błędna odpowiedz: " + response);
            }
        } while (action == null);
        return action;
    }

    private static void printAdd() {
        System.out.println("Podaj dane użytkownika");
        System.out.println("Podaj nazwę:");
        String name = scan.nextLine();
        System.out.println("Podaj e-mail:");
        String email = scan.nextLine();
        System.out.println("Podaj hasło:");
        String password = scan.nextLine();
        Integer id = null;
        do {
            System.out.println("Do której grupy ma należeć:");
            for (Group g : GroupDao.findAll())
                System.out.println("["+g.getId()+"] "+g.getName());
            try {
                id = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
        Group group = GroupDao.read(id);
        UserDao.create(new User(name, email, password, group));
        System.out.println("Dodano użytkownika!");
    }

    private static void printEdit() {
        Integer id = null;
        do {
            System.out.println("Którego użytkownika chcesz edytować?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                System.out.println("Podaj nazwę:");
                String name = scan.nextLine();
                System.out.println("Podaj e-mail:");
                String email = scan.nextLine();
                System.out.println("Podaj hasło:");
                String password = scan.nextLine();
                User u = UserDao.read(id);
                u.setUserName(name);
                u.setEmail(email);
                u.setPassword(password);
                UserDao.update(u);
                System.out.println("Zaktualizowano dane użytkownika!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete() {
        Integer id = null;
        do {
            System.out.println("Którego użytkownika chcesz usunąć?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                UserDao.delete(id);
                System.out.println("Usunięto użytkownika!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }
}
