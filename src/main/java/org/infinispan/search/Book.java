package org.infinispan.search;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Decimal;
import org.infinispan.api.annotations.indexing.Embedded;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.api.annotations.indexing.option.Structure;
import org.infinispan.api.annotations.indexing.option.TermVector;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoName;

import java.util.List;

public class Book {

   private String title;
   private Integer yearOfPublication;
   private String description;
   private Float price;
   private Author author;
   private List<Review> reviews;

   @ProtoFactory
   public Book(String title, Integer yearOfPublication, String description, Float price, Author author, List<Review> reviews) {
      this.title = title;
      this.yearOfPublication = yearOfPublication;
      this.description = description;
      this.price = price;
      this.author = author;
      this.reviews = reviews;
   }

   @ProtoField(value = 1)
   public String getTitle() {
      return title;
   }

   @ProtoField(value = 2)
   public Integer getYearOfPublication() {
      return yearOfPublication;
   }

   @ProtoField(value = 3)
   public String getDescription() {
      return description;
   }

   @ProtoField(value = 4)
   public Float getPrice() {
      return price;
   }

   @ProtoField(value = 5)
   public Author getAuthor() {
      return author;
   }

   @ProtoField(value = 6)
   public List<Review> getReviews() {
      return reviews;
   }

   @Override
   public String toString() {
      return "Book{" +
            "title='" + title + '\'' +
            ", yearOfPublication=" + yearOfPublication +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", author=" + author +
            ", reviews=" + reviews +
            '}';
   }

}
