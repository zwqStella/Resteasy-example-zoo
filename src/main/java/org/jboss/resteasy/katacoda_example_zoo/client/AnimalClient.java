package org.jboss.resteasy.katacoda_example_zoo.client;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.jboss.resteasy.katacoda_example_zoo.server.domain.Animal;

public class AnimalClient {

    private static RequestSender sender;
    private static Scanner input;

    public static void main(String args[]) {
        sender = new RequestSender();
        while (true) {
            System.out.println();
            System.out.println("Menu: ");
            System.out.println("--------------------------------");
            System.out.println("1. Add Animal");
            System.out.println("2. Query Animal");
            System.out.println("3. Delete Animal");
            System.out.println("4. Modify Animal name");
            System.out.println("5. Replace or Add Animal on Certain ID");
            System.out.println("6. My method");
            System.out.println("Enter any other number to quit...");
            try {
                input = new Scanner(System.in);
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        addAnimal();
                        break;
                    case 2:
                        queryAnimal();
                        break;
                    case 3:
                        deleteAnimal();
                        break;
                    case 4:
                        rename();
                        break;
                    case 5:
                        replace();
                        break;
                    case 6:
                        hello();
                        break;
                    default:
                        System.out.println("Quit... ");
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Illegal input, please try again");
            } catch (Exception e) {
                System.out.println("Connection failed... ");
            }
        }
    }

    public static void addAnimal() {
        System.out.println("Please enter animal name: ");
        String name = input.next();
        System.out.println("Please enter animal kind: ");
        String kind = input.next();
        Animal animal = new Animal(name, kind);
        Animal response = sender.post(animal);
        System.out.println("Add Sucessfully.");
        System.out.println(response);
    }

    public static void queryAnimal() {
        System.out.println("Please enter the id of the animal you want to query (enter 0 for all animals): ");
        int id = input.nextInt();
        Collection<Animal> collection = sender.get(id);
        if (collection.isEmpty()) {
            System.out.println("Animal not found");
        } else {
            for (Animal animal : collection) {
                System.out.println(animal);
            }
        }
    }

    public static void deleteAnimal() {
        System.out.println("Please enter the id of the animal you want to delete: ");
        int id = input.nextInt();
        Animal response = sender.delete(id);
        if (null != response) {
            System.out.println("Delete Sucessfully.");
            System.out.println(response);
        } else {
            System.out.println("Animal not found");
        }
    }

    public static void rename() {
        System.out.println("Please enter the id of the animal you want to rename: ");
        int id = input.nextInt();
        System.out.println("Please enter the new name: ");
        String name = input.next();
        Animal response = sender.modify(id, name);
        if (null != response) {
            System.out.println("Rename Sucessfully.");
            System.out.println(response);
        } else {
            System.out.println("Animal not found");
        }
    }

    public static void replace() {
        System.out.println("Please enter the id of the animal you want to put or replace: ");
        int id = input.nextInt();
        System.out.println("Please enter the name of new animal: ");
        String name = input.next();
        System.out.println("Please enter the kind of new animal: ");
        String kind = input.next();
        Animal animal = new Animal(id, name, kind);
        Animal response = sender.put(animal);
        System.out.println("Replace Sucessfully.");
        System.out.println(response);
    }
    

    public static void hello() {
        String hello = sender.hello();
        System.out.println(hello);
    }

}
