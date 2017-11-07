package com.restfully.zoo.domain;

public class Animal {
   private String name;
   private String kind;
   private int id;
   
   
   
   
   public Animal(String name, String kind) {
	   this.id = -1;
	   this.name = name;
	   this.kind = kind;
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

   public void setId(int id) {
	   this.id = id;
   }
   
}
