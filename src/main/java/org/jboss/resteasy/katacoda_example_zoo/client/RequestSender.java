package org.jboss.resteasy.katacoda_example_zoo.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.katacoda_example_zoo.server.domain.Animal;

public class RequestSender {

    private Client client;
    private String pathBase;

    public RequestSender() {
        this.client = ClientBuilder.newClient();
        // this.client.register(new RequestFilter());
        // this.client.register(new ResponseFilter());
        this.pathBase = "http://localhost:8080/";
    }

    public RequestSender(String pathBase) {
        this.client = ClientBuilder.newClient();
        // this.client.register(new RequestFilter());
        // this.client.register(new ResponseFilter());
        this.pathBase = pathBase;
    }

    @Override
    protected void finalize() throws Throwable {
        this.client.close();
    }

    public Animal post(Animal animal) {
        WebTarget target = client.target(pathBase + "zoo/animals");
        Animal response = target.request().post(Entity.entity(animal, MediaType.APPLICATION_JSON), Animal.class);
        return response;
    }

    public Collection<Animal> get(int id) {
        if (id == 0) {
            WebTarget target = client.target(pathBase + "zoo/animals/all");
            Collection<Animal> collection = target.request().get(new GenericType<Collection<Animal>>() {
            });
            return collection;
        }
        WebTarget target = client.target(pathBase + "zoo/animals/{id}");
        Animal response;
        try {
            response = target.resolveTemplate("id", id).request().get(Animal.class);
            Collection<Animal> collection = new ArrayList<Animal>(1);
            collection.add(response);
            return collection;
        } catch (NotFoundException e) {
            return Collections.emptyList();
        }
    }

    public Animal delete(int id) {
        WebTarget target = client.target(pathBase + "zoo/animals");
        try {
            Animal response = target.queryParam("id", id).request().delete(Animal.class);
            return response;
        } catch (NotFoundException e) {
            return null;
        }
    }

    public Animal modify(int id, String name) {
        WebTarget target = client.target(pathBase + "zoo/animals");
        try {
            Animal response = target.matrixParam("id", id).matrixParam("name", name).request().put(null, Animal.class);
            return response;
        } catch (NotFoundException e) {
            return null;
        }
    }

    public Animal put(Animal animal) {
        WebTarget target = client.target(pathBase + "zoo/animals");
        Animal response = target.request().put(Entity.entity(animal, MediaType.APPLICATION_JSON), Animal.class);
        return response;
    }

    public String hello() {
        // generate your own method here
        return null;
    }

}
