package org.ws.resources;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.entities.GraphQLRequestBody;
import org.entities.Person;
import org.facade.PersonFacade;

import com.github.vladislavsevruk.generator.GqlRequestBodyGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

@RequestScoped
@Path("/rest")
@Produces("application/json")
@Consumes("application/json")
public class RestEndPoint {

	@Inject
	private GraphqlService graphqlService;

	@Inject
	private PersonFacade personFacade;
	private List<Person> personlist;

	@GET
	@Produces("application/json")
	public List<Person> hello() {
		try {
			personlist = personFacade.findAll();
			return personlist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Person findById(@PathParam("id") int id) {
		try {
			Person p = personFacade.find(id);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@POST
	@Consumes("application/json")
	public void addPerson(Person p) {
		try {

			personFacade.save(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Path("/graph")
	@POST
	public Response excuteResult(String jsonQuery)
			throws FileNotFoundException, InterruptedException, ExecutionException {

		Gson gson = new GsonBuilder().create();
		GraphQLRequestBody body = gson.fromJson(jsonQuery, GraphQLRequestBody.class);

		GraphQL graphQL = graphqlService.graphQL();

		CompletableFuture<ExecutionResult> future = graphQL
				.executeAsync(ExecutionInput.newExecutionInput().query(body.getQuery())
						.operationName(body.getOperationName()).variables(body.getVaraibles()).build())
				.toCompletableFuture();

		ExecutionResult result = future.get();
		if (!result.getErrors().isEmpty()) {
			return Response.ok(result.getErrors(), MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(result.getData(), MediaType.APPLICATION_JSON).build();
	}

	@Path("/graph")
	@GET
	public Response GqlRequestBody() throws FileNotFoundException, InterruptedException, ExecutionException {

		GraphQL graphQL = graphqlService.graphQL();
		String query =GqlRequestBodyGenerator.unwrapped().query("getPersons").selectionSet(Person.class).generate();
		CompletableFuture<ExecutionResult> future = graphQL
				.executeAsync(ExecutionInput.newExecutionInput().query(query).build()).toCompletableFuture();

		ExecutionResult result = future.get();
		if (!result.getErrors().isEmpty()) {
			return Response.ok(result.getErrors(), MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(result.getData(), MediaType.APPLICATION_JSON).build();
	}

	public Response excute(String jsonQuery) throws FileNotFoundException {
		Gson gson = new GsonBuilder().create();
		GraphQLRequestBody body = gson.fromJson(jsonQuery, GraphQLRequestBody.class);

		GraphQL graphQL = graphqlService.graphQL();

		CompletableFuture<ExecutionResult> future = graphQL
				.executeAsync(ExecutionInput.newExecutionInput().query(body.getQuery())
						.operationName(body.getOperationName()).variables(body.getVaraibles()).build())
				.toCompletableFuture();

		return Response.ok(future, MediaType.APPLICATION_JSON).build();
	}

}
