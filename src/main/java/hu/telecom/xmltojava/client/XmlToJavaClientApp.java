package hu.telecom.xmltojava.client;

import hu.telecom.xmltojava.client.service.UserService;
import hu.telecom.xmltojava.client.service.impl.UserServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class XmlToJavaClientApp {

    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        startWithMenu();
    }

    private static void startWithMenu(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            writeMenu();

            int choice = readChoiceNumber(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userService.addUser(scanner);
                    break;
                case 2:
                    userService.listUsers();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void writeMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Add User");
        System.out.println("2. List Users");
        System.out.println("3. Exit");
    }

    private static int readChoiceNumber(Scanner scanner){
        try{
            return scanner.nextInt();
        }
        catch (InputMismatchException e){
            return 0;
        }
    }

}