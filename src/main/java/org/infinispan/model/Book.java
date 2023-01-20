package org.infinispan.model;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Decimal;
import org.infinispan.api.annotations.indexing.Embedded;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.api.annotations.indexing.option.Structure;
import org.infinispan.api.annotations.indexing.option.TermVector;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoName;

import java.util.List;

@Indexed
@ProtoName("book")
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

   @Keyword(normalizer = "lowercase", projectable = true)
   @ProtoField(value = 1)
   public String getTitle() {
      return title;
   }

   @Basic
   @ProtoField(value = 2)
   public Integer getYearOfPublication() {
      return yearOfPublication;
   }

   @Text(termVector = TermVector.WITH_POSITIONS_OFFSETS_PAYLOADS, norms = false)
   @ProtoField(value = 3)
   public String getDescription() {
      return description;
   }

   @Decimal(decimalScale = 2, aggregable = true)
   @ProtoField(value = 4)
   public Float getPrice() {
      return price;
   }

   @Embedded(structure = Structure.FLATTENED)
   @ProtoField(value = 5)
   public Author getAuthor() {
      return author;
   }

   @Embedded(structure = Structure.NESTED)
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
