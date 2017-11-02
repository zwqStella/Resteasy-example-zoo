package com.restfully.zoo.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.StreamingOutput;

@Path("/shop")
public class ShopResource {
	
	@GET
	@Path("hello")
	@Produces("text/plain")
	public StreamingOutput sayHello() {
		return outputStream -> {
				PrintStream writer = new PrintStream(outputStream);
				writer.println("welcome to hello world zoo souvenir shop");
			};
	}
	
	@GET
	@Path("buy/{product}")
	@Produces("text/plain")
	public StreamingOutput buy(@PathParam("product") PathSegment product) {
		Map<String, List<String>> map = product.getMatrixParameters();
		String o = "";
		for(String key : map.keySet()) {
			o += key + ": " + map.get(key).get(0);
		}
		final String name = product.getPath();
		final String attr = o;
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
			PrintStream writer = new PrintStream(outputStream);
			writer.println("product: "+ name + "\n" + attr);
			}
		};
	}
	
	@GET
	@Path("menu")
	@Produces("text/plain")
	public StreamingOutput menu(@QueryParam("pageNo") int no, @QueryParam("pageSize") int size) {
		String items = "";
		for(int i = 0; i < no; i ++) {
			for(int j = 0; j < size; j ++) {
				items += "item"+i+j+"\n";
			}
		}
		final String menu = items;
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
			PrintStream writer = new PrintStream(outputStream);
			writer.println("menu: "+menu);
			}
		};
	}
}
