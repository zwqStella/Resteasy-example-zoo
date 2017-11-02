package com.restfully.zoo.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.restfully.zoo.domain.Animal;

@Path("/animals")
public class AnimalResource {
	
	private Map<Integer, Animal> animals = new ConcurrentHashMap<Integer, Animal>();
	private AtomicInteger idCounter = new AtomicInteger();
 
	@GET
	@Path("hello")
	@Produces("text/plain")
	public StreamingOutput sayHello() {
		return outputStream -> {
				PrintStream writer = new PrintStream(outputStream);
				writer.println("welcome to hello world zoo");
			};
	}
   
	@POST
	@Path("add")
	@Consumes("application/xml")
	@Produces("text/plain")
	public StreamingOutput addAnimal(InputStream is) {
		try {
			final Animal animal = readAnimal(is);
			final int id = idCounter.incrementAndGet();
			animals.put(id, animal);
			return new StreamingOutput() {
		         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
		            PrintStream writer = new PrintStream(outputStream);
		      	    writer.println("add successfully \r\nid: "+id+"  name: "+animal.getName()+"  kind: "+animal.getKind());
		         }
		      };
		} catch (Exception e) {
			e.printStackTrace();
			return new StreamingOutput() {
		         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
		            PrintStream writer = new PrintStream(outputStream);
		      	    writer.println("wrong input format!");
		         }
		      };
		}
	}
	
	@GET
	@Path("{id : \\d+}")
	@Produces("text/plain")
	public StreamingOutput getAnimal(@PathParam("id") final int id) {
		final Animal animal = animals.get(id);
		if(animal == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}else {
			return new StreamingOutput() {
		         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
		            PrintStream writer = new PrintStream(outputStream);
		      	    writer.println("id: "+id+"  name: "+animal.getName()+"  kind: "+animal.getKind());
		         }
		      };
		}
	}
	
	@GET
	@Path("{name : [a-zA-Z]+}")
	@Produces("text/plain")
	public StreamingOutput getFirstAnimal(@PathParam("name") final String name) {
		for(Integer cid : animals.keySet()) {
			Animal a = animals.get(cid);
			if(a.getName().equals(name)){
				final Animal animal = a;
				final int id = cid;
				return new StreamingOutput() {
			         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
			            PrintStream writer = new PrintStream(outputStream);
			      	    writer.println("id: "+id+"  name: "+animal.getName()+"  kind: "+animal.getKind());
			         }
			      };
			}
		}
		throw new WebApplicationException(Response.Status.NOT_FOUND);
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
