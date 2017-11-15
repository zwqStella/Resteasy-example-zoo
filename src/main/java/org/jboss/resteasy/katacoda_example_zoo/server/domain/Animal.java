package org.jboss.resteasy.katacoda_example_zoo.server.domain;

public class Animal {

    private String name;
    private String kind;
    private int id;

    public Animal() {
        super();
    }

    public Animal(String name, String kind) {
        this.id = -1;
        this.name = name;
        this.kind = kind;
    }

    public Animal(int id, String name, String kind) {
        super();
        this.name = name;
        this.kind = kind;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public int getId() {
        return id;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Animal ID: " + this.id + ", name=" + this.name + ", kind=" + this.kind;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Animal) {
            Animal animal = (Animal) obj;
            if (this.id == animal.getId() && this.name.equals(animal.getName()) && this.kind.equals(animal.getKind())) {
                return true;
            }
        }
        return false;
    }

}
