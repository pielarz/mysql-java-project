package io.github.pielarz.admin;

import io.github.pielarz.dao.GroupDao;
import io.github.pielarz.model.Group;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GroupApp {
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
        System.out.println("Lista grup:");
        Group[] groups = GroupDao.findAll();
        for (Group group : groups)
            System.out.println("["+group.getId()+"] " + group.getName());

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
        System.out.println("Podaj dane grupy");
        System.out.println("Podaj nazwę:");
        String name = scan.nextLine();
        Group group = new Group();
        group.setName(name);
        GroupDao.create(group);
        System.out.println("Dodano grupę!");
    }

    private static void printEdit() {
        Integer id = null;
        do {
            System.out.println("Którą grupę chesz edytować?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                System.out.println("Podaj nazwę:");
                String name = scan.nextLine();
                Group g = GroupDao.read(id);
                g.setName(name);
                GroupDao.update(g);
                System.out.println("Zaktualizowano dane grupy!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete() {
        Integer id = null;
        do {
            System.out.println("Którę grupę chcesz usunąć?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                GroupDao.delete(id);
                System.out.println("Usunięto grupę!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }
}
