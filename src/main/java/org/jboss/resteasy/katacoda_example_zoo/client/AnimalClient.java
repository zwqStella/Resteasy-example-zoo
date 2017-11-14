package org.jboss.resteasy.katacoda_example_zoo.client;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.katacoda_example_zoo.server.domain.Animal;

public class AnimalClient {

    private static Client client;
    private static Scanner input;

    public static void main(String args[]) {
        client = ClientBuilder.newClient();
        while (true) {
            System.out.println();
            System.out.println("Menu: ");
            System.out.println("--------------------------------");
            System.out.println("0. Say Hello");
            System.out.println("1. Add Animal");
            System.out.println("2. Query Animal");
            System.out.println("3. Delete Animal");
            System.out.println("4. Modify Animal name");
            System.out.println("5. Replace or Add Animal on Certain ID");
            System.out.println("Enter any other number to quit...");
            try {
                input = new Scanner(System.in);
                int choice = input.nextInt();
                switch (choice) {
                    case 0:
                        hello();
                        break;
                    case 1:
                        post();
                        break;
                    case 2:
                        get();
                        break;
                    case 3:
                        delete();
                        break;
                    case 4:
                        rename();
                        break;
                    case 5:
                        replace();
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

    public static void hello() {
        String hello = client.target("http://localhost:8080/zoo/animals/hello").request().get(String.class);
        System.out.println(hello);
    }

    public static void post() {
        System.out.println("Please enter animal name: ");
        String name = input.next();
        System.out.println("Please enter animal kind: ");
        String kind = input.next();
        Animal animal = new Animal(name, kind);
        WebTarget target = client.target("http://localhost:8080/zoo/animals");
        Animal response = target.request().post(Entity.entity(animal, MediaType.APPLICATION_JSON), Animal.class);
        System.out.println("Add Sucessfully.");
        System.out.println(response);
    }

    public static void get() {
        System.out.println("Please enter the id of the animal you want to query (enter 0 for all animals): ");
        int id = input.nextInt();
        if (id == 0) {
            WebTarget target = client.target("http://localhost:8080/zoo/animals/all");
            Collection<Animal> response = target.request().get(new GenericType<Collection<Animal>>() {
            });
            for (Animal animal : response) {
                System.out.println(animal);
            }
            return;
        }
        WebTarget target = client.target("http://localhost:8080/zoo/animals/{id}");
        Animal response;
        try {
            response = target.resolveTemplate("id", id).request().get(Animal.class);
            System.out.println("Query Result");
            System.out.println(response);
        } catch (NotFoundException e) {
            System.out.println("Animal not found");
        }
    }

    public static void delete() {
        System.out.println("Please enter the id of the animal you want to delete: ");
        int id = input.nextInt();
        WebTarget target = client.target("http://localhost:8080/zoo/animals");
        try {
            Animal response = target.queryParam("id", id).request().delete(Animal.class);
            System.out.println("Delete Sucessfully.");
            System.out.println(response);
        } catch (NotFoundException e) {
            System.out.println("Animal not found");
        }
    }

    public static void rename() {
        System.out.println("Please enter the id of the animal you want to rename: ");
        int id = input.nextInt();
        System.out.println("Please enter the new name: ");
        String name = input.next();
        WebTarget target = client.target("http://localhost:8080/zoo/animals");
        try {
            Animal response = target.matrixParam("id", id).matrixParam("name", name).request().put(null, Animal.class);
            System.out.println("Modify Sucessfully.");
            System.out.println(response);
        } catch (NotFoundException e) {
            System.out.println("Animal not found");
        }
    }

    public static void replace() {
        System.out.println("Please enter the id of the animal you want to replace: ");
        int id = input.nextInt();
        System.out.println("Please enter the name of new animal: ");
        String name = input.next();
        System.out.println("Please enter the kind of new animal: ");
        String kind = input.next();
        Animal animal = new Animal(id, name, kind);
        WebTarget target = client.target("http://localhost:8080/zoo/animals");
        try {
            Animal response = target.request().put(Entity.entity(animal, MediaType.APPLICATION_JSON), Animal.class);
            System.out.println("Modify Sucessfully.");
            System.out.println(response);
        } catch (NotFoundException e) {
            System.out.println("Animal not found");
        }
    }

}
