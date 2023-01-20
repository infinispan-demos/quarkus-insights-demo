package org.infinispan.model;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(includeClasses = {Book.class, Author.class, Review.class},
      schemaFileName = "books-schema.proto",
      schemaPackageName = "insights")
interface BooksSchema extends GeneratedSchema {

}
