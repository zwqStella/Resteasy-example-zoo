package com.restfully.zoo.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class ManageApplication extends Application {
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();

   public ManageApplication() {
      singletons.add(new AnimalResource());
   }

   @Override
   public Set<Object> getSingletons() {
      return singletons;
   }

   @Override
   public Set<Class<?>> getClasses() {
	   return empty;
   }
   
}
