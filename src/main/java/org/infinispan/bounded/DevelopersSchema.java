package org.infinispan.bounded;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.infinispan.search.Author;
import org.infinispan.search.Book;
import org.infinispan.search.Review;

@AutoProtoSchemaBuilder(includeClasses = { Developer.class },
      schemaFileName = "developers-schema.proto",
      schemaPackageName = "insights")
interface DevelopersSchema extends GeneratedSchema {

}
