package org.jboss.resteasy.katacoda_example_zoo.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.jboss.resteasy.katacoda_example_zoo.client.RequestSender;
import org.jboss.resteasy.katacoda_example_zoo.server.domain.Animal;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnimalTest {

    private static RequestSender sender;

    @BeforeClass
    public static void initSender() {
        sender = new RequestSender();
        Animal cat = new Animal(1, "Kitty", "cat");
        Animal dog = new Animal(2, "Jake", "dog");
        sender.put(cat);
        sender.put(dog);
    }

    @AfterClass
    public static void closeSender() {
        sender = null;
    }

    @Test
    public void testHello() throws Exception {
        String hello = sender.hello();
        System.out.println(hello);
        assertEquals("welcome", hello);
    }

    @Test
    public void testPost() throws Exception {
        Animal animal = new Animal("Tomie", "fish");
        Animal response = sender.post(animal);
        response.setId(animal.getId());
        assertEquals(animal, response);
    }

    @Test
    public void testGet() throws Exception {
        Collection<Animal> response1 = sender.get(1);
        assertEquals(1, response1.size());
        Collection<Animal> response0 = sender.get(0);
        assertTrue(response0.size() > 0);
    }

    @Test
    public void testModify() throws Exception {
        Animal response = sender.modify(1, "Kate");
        assertEquals("Kate", response.getName());
    }

    @Test
    public void testPut() throws Exception {
        Animal animal = new Animal(1, "Luna", "cat");
        Animal response = sender.put(animal);
        assertEquals(animal, response);
    }

    @Test
    public void testDelete() throws Exception {
        Animal response = sender.delete(2);
        assertNotNull(response);
    }
}
