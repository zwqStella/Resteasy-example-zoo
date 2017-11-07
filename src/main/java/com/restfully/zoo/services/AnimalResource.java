package com.restfully.zoo.services;

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

import com.restfully.zoo.domain.Animal;

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
	@Path("add")
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
	@Path("{id}")
	@Produces("application/json")
	public Animal getAnimal(@PathParam("id")int id) {
		Animal animal = animals.get(id);
		if(animal == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}else {
			return animal;
		}
	}
	
	@DELETE
	@Path("delete")
	@Produces("application/json")
	public Animal deleteAnimal(@QueryParam("id") int id) {
		Animal animal = animals.get(id);
		if(animal != null) {
			return animal;
		}else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@PUT
	@Path("edit")
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
