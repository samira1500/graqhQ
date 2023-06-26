package org.ws.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.PersonGraphQlApi;
import org.services.PersonService;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;

@RequestScoped
public class GraphqlService {

	@Inject
	private PersonGraphQlApi personGraphQlApi;
	@Inject
	private PersonService personService;

	public GraphqlService() {
		// TODO Auto-generated constructor stub
	}

	public GraphQL graphQLSpqr() throws FileNotFoundException {
		
		GraphQLSchema schema = new GraphQLSchemaGenerator().withBasePackages("org.facade")
				.withOperationsFromSingleton(personGraphQlApi,PersonGraphQlApi.class).generate();
		return new GraphQL.Builder(schema).build();
		        
//		GraphQLSchema schema = new GraphQLSchemaGenerator()
//				.withResolverBuilders(new AnnotatedResolverBuilder().withDefaultFilters())
//				.withOperationsFromSingleton(personGraphQlApi, PersonGraphQlApi.class).generate();
//		return GraphQL.newGraphQL(schema).build();
		
		
	}

	public GraphQL graphQL() throws FileNotFoundException {

		SchemaParser schemaParser = new SchemaParser();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("schema.graphql").getFile());
		InputStream in = new FileInputStream(file);
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(in);
		RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getPerson", personService.getPerson()))
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getPersons", personService.getPersons()))
				.build();

		SchemaGenerator generator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = generator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		return GraphQL.newGraphQL(graphQLSchema).build();

	}

	public void defineSchemaProgramatically() {

	}
}
