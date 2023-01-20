package org.infinispan.search;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoName;

@Indexed
@ProtoName("author")
public class Author {

   private String firstname;
   private String surname;
   private Integer numberOfPublishedBooks;

   @ProtoFactory
   public Author(String firstname, String surname, Integer numberOfPublishedBooks) {
      this.firstname = firstname;
      this.surname = surname;
      this.numberOfPublishedBooks = numberOfPublishedBooks;
   }

   @Keyword(sortable = true)
   @ProtoField(value = 1)
   public String getFirstname() {
      return firstname;
   }

   @Keyword
   @ProtoField(value = 2)
   public String getSurname() {
      return surname;
   }

   @Basic
   @ProtoField(value = 3)
   public Integer getNumberOfPublishedBooks() {
      return numberOfPublishedBooks;
   }

   @Override
   public String toString() {
      return "Author{" +
            "firstname='" + firstname + '\'' +
            ", surname='" + surname + '\'' +
            ", numberOfPublishedBooks=" + numberOfPublishedBooks +
            '}';
   }
}
