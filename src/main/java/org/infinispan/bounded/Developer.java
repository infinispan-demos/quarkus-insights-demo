package org.infinispan.bounded;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

public class Developer {

   private String firstname;
   private String surname;
   private Integer numberOfPublishedBooks;

   @ProtoFactory
   public Developer(String firstname, String surname, Integer numberOfPublishedBooks) {
      this.firstname = firstname;
      this.surname = surname;
      this.numberOfPublishedBooks = numberOfPublishedBooks;
   }

   @ProtoField(value = 1)
   public String getFirstname() {
      return firstname;
   }

   @ProtoField(value = 2)
   public String getSurname() {
      return surname;
   }

   @ProtoField(value = 3)
   public Integer getNumberOfPublishedBooks() {
      return numberOfPublishedBooks;
   }
}
