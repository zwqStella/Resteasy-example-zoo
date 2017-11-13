package org.jboss.resteasy.katacoda_example_zoo.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.katacoda_example_zoo.server.domain.Animal;

@Path("/animals")
public class AnimalResource {
	
	private Map<Integer, Animal> animals = new ConcurrentHashMap<Integer, Animal>();
	private AtomicInteger idCounter = new AtomicInteger();
 
	@GET
	@Path("hello")
	@Produces("text/plain")
	public String sayHello() {
		return "welcome";
	}
   
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Animal addAnimal(Animal animal) {
		try {
			int id = idCounter.incrementAndGet();
			animal.setId(id);
			animals.put(id, animal);
			return animal;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}
	
	@GET
	@Path("{id : \\d+}")
	@Produces("application/json")
	public Animal getAnimal(@PathParam("id")int id) {
		Animal animal = animals.get(id);
		if(animal == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}else {
			return animal;
		}
	}
	
	@GET
	@Path("all")
	@Produces("application/json")
	public Collection<Animal> getAll() {
		return animals.values();
	}
	
	@DELETE
	@Produces("application/json")
	public Animal deleteAnimal(@QueryParam("id") int id) {
		Animal animal = animals.get(id);
		if(animal != null) {
			animals.remove(id);
			return animal;
		}else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Animal replaceAnimal(Animal animal) {
        int id = animal.getId();
        if(id > 0) {
            animals.put(id, animal);
            return animal;
        }else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
	
	@PUT
	@Produces("application/json")
	public Animal modAnimal(@MatrixParam("id") int id, @MatrixParam("name") String name) {
		Animal animal = animals.get(id);
		if(animal != null) {
			animal.setName(name);
			return animal;
		}else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
}
