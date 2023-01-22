package org.infinispan.bounded;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

import java.util.Objects;

public class Developer {
   public String firstName;
   public String lastName;
   public String project;

   public Developer() {
   }

   @ProtoFactory
   public Developer(String firstName, String lastName, String project) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.project = project;
   }

   @ProtoField(value = 1)
   public String getFirstName() {
      return firstName;
   }

   @ProtoField(value = 2)
   public String getLastName() {
      return lastName;
   }

   @ProtoField(value = 3)
   public String getProject() {
      return project;
   }

   @Override
   public String toString() {
      return "Developer{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", project='"
            + project + '\'' + '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      Developer developer = (Developer) o;
      return Objects.equals(firstName, developer.firstName) && Objects.equals(lastName, developer.lastName)
            && Objects.equals(project, developer.project);
   }

   @Override
   public int hashCode() {
      return Objects.hash(firstName, lastName, project);
   }
}
