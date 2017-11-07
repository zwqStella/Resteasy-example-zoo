package com.restfully.zoo.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnimalTest {

	private static Client client;

	@BeforeClass
	public static void initClient() {
	   client = ClientBuilder.newClient();
	}

	@AfterClass
	public static void closeClient() {
	   client.close();
	}
	
	@Test
	public void testHello() throws Exception {
		String hello = client.target("http://localhost:8080/zoo/animals/hello").request().get(String.class);
		System.out.println(hello);
		assertEquals("welcome",hello);
	}
}
