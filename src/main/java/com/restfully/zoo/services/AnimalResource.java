package com.restfully.zoo.services;

import java.io.InputStream;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.restfully.zoo.domain.Animal;

import org.json.simple.JSONObject;

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
	@Consumes("application/xml")
	@Produces("application/json")
	public String addAnimal(InputStream is) {
		try {
			Animal animal = readAnimal(is);
			int id = idCounter.incrementAndGet();
			animals.put(id, animal);
			return toJSON(id, animal);
		} catch (Exception e) {
			e.printStackTrace();
			return "wrong input format!";
		}
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getAnimal(@PathParam("id")int id) {
		Animal animal = animals.get(id);
		if(animal == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}else {
			return toJSON(id, animal);
		}
	}
	
	@DELETE
	@Path("delete")
	@Produces("application/json")
	public String deleteAnimal(@QueryParam("id") int id) {
		Animal animal = animals.get(id);
		if(animal != null) {
			return toJSON(id, animal);
		}else {
			return error("Animal does not exist.");
		}
	}
	
	@PUT
	@Path("edit")
	@Produces("application/json")
	public String modAnimal(@MatrixParam("id") int id, @MatrixParam("name") String name) {
		Animal animal = animals.get(id);
		if(animal != null) {
			animal.setName(name);
			return toJSON(id, animal);
		}else {
			return error("Animal does not exist.");
		}
	}
	
	@SuppressWarnings("unchecked")
	protected String error(String message) {
		JSONObject jo = new JSONObject();
		jo.put("error", message);
		return jo.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	protected String toJSON(int id, Animal animal) {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("name", animal.getName());
		jo.put("kind", animal.getKind());
		return jo.toJSONString();
	}
	
	protected Animal readAnimal(InputStream is) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(is);
        Element root = doc.getDocumentElement();
        Animal animal = new Animal();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
        	if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        		 Element element = (Element) nodes.item(i);
        		 if (element.getTagName().equals("name")) {
        			 animal.setName(element.getTextContent());
        		 }
        		 if (element.getTagName().equals("kind")) {
        			 animal.setKind(element.getTextContent());
        		 }
        	}
        }
        return animal;
	}
}
