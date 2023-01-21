package org.infinispan.search;

import io.quarkus.logging.Log;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ModelGenerator {

   public static final String EFFECTIVE = "Effective Java - Third Edition";
   public static final String PRACTICE = "Java Concurrency in Practice";
   public static final String UTIL = "Java Threads and the Concurrency Utilities";
   public static final String MEMORY = "Java Memory Management: A comprehensive guide to garbage collection and JVM tuning";
   public static final String MULTIPROCESSOR = "The Art of Multiprocessor Programming";
   public static final String PERFORMANCE = "Java Performance: In-Depth Advice for Tuning and Programming Java 8, 11, and Beyond";
   public static final String MICROSERVICES = "Pro Java Microservices with Quarkus and Kubernetes: A Hands-on Guide";

   // EFFECTIVE, PRACTICE, UTIL, MEMORY, MULTIPROCESSOR, PERFORMANCE, MICROSERVICES
   private static final Object[][] BOOKS_DATA = {
         {"effective-java", EFFECTIVE, 2017, "Joshua", "Bloch", 25, 40.3f,
               "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java ..."},
         {"concurrency-in-practice", PRACTICE, 2006, "Brian", "Goetz", 10, 80.15f,
               "Java Concurrency in Practice provides you with the concepts and techniques needed to write safe and scalable Java programs for today's ..."},
         {"threads-concurrency-util", UTIL, 2015, "Jeff", "Friesen", 5, 35.06f,
               "This concise book empowers all Java developers to master the complexity of the Java thread APIs and concurrency utilities. ..."},
         {"memory-management", MEMORY, 2022, "Maaike", "van Putten", 20, 120.00f,
               "Understanding how Java organizes memory is important for every Java professional, but this particular topic is a common knowledge gap for many software professionals., ..."},
         {"multiprocessor-programming", MULTIPROCESSOR, 2020, "Maurice", "Herlihy", 12, 72.1f,
               "The Art of Multiprocessor Programming, Second Edition, provides users with an authoritative guide to multicore programming."},
         {"java-performance", PERFORMANCE, 2020, "Scott", "Oaks", 18, 38.63f,
               "Coding and testing are generally considered separate areas of expertise. In this practical book, Java expert Scott Oaks takes the approach that anyone who works with Java should be adept at understanding how code behaves in the Java Virtual Machine&;including the tunings likely to help performance."},
         {"java-microservice-quarkus", MICROSERVICES, 2021, "Nebrass", "Lamouchi", 12, 46.32f,
               "This book will help you quickly get started with the features and concerns of a microservices architecture. It will introduce Docker and Kubernetes to help you deploy your microservices."}
   };

   private static final String[] REVIEWS_CONTENTS = {
         "This is a very great book",
         "Definitely useful and nice book",
         "Great to have in your library",
   };

   private static final Date[] REVIEW_DATES = {
         Date.from(Instant.parse("2023-12-03T10:15:30.00Z")),
         Date.from(Instant.parse("2022-11-21T10:15:30.00Z")),
         Date.from(Instant.parse("2022-08-07T10:15:30.00Z")),
   };

   private static final Float[] REVIEW_SCORES = {
         9.5f,
         7.25f,
         8.75f,
   };

   private static List<Review> generateReviews(Random random) {
      return Arrays.stream(REVIEWS_CONTENTS)
            // adding reviews randomly, they are all great books :)
            .filter(unused -> random.nextBoolean())
            .map(content -> {
               Date date = REVIEW_DATES[random.nextInt(REVIEW_DATES.length)];
               Float score = REVIEW_SCORES[random.nextInt(REVIEW_SCORES.length)];
               return new Review(date, content, score);
            })
            .toList();
   }

   public static final Map<String, Book> generateBooks() {
      Random random = new Random(739); // fixed-seed random

      return Arrays.stream(BOOKS_DATA)
            .collect(Collectors.toMap(bookData -> (String) bookData[0], bookData -> {
               Object key = bookData[0];
               String title = (String) bookData[1];
               Integer yearOfPublication = (Integer) bookData[2];
               String authorFirstname = (String) bookData[3];
               String authorSurname = (String) bookData[4];
               Integer numberOfPublishedBooks = (Integer) bookData[5];
               Float price = (Float) bookData[6];
               String description = (String) bookData[7];

               List<Review> reviews = generateReviews(random);
               Log.info(key + " --> " + reviews);
               return new Book(title, yearOfPublication, description, price,
                     new Author(authorFirstname, authorSurname, numberOfPublishedBooks), reviews);
            }));
   }
}
