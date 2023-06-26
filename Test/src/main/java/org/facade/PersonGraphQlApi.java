package org.facade;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.entities.Person;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@RequestScoped
public class PersonGraphQlApi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersonFacade personFacade;

	@GraphQLQuery(name = "getPersons")
	public List<Person> getAllPersons() throws Exception {
		return personFacade.findAll();
	}

	@GraphQLQuery(name = "getPerson")
	public Person find(@GraphQLArgument(name = "id") int id) {
		return personFacade.find(id);
	}

}
