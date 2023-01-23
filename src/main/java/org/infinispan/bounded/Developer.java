package org.infinispan.bounded;

import java.util.Objects;

public class Developer {
   public String firstName;
   public String lastName;
   public String project;

   public Developer() {
   }

   public Developer(String firstName, String lastName, String project) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.project = project;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

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
