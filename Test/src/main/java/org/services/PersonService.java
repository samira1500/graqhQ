package org.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.entities.Person;
import org.facade.PersonFacade;

import graphql.schema.DataFetcher;

@RequestScoped
public class PersonService {

	@Inject
	private PersonFacade personFacade;

	public DataFetcher<Person> getPerson() {
		return env -> {
			int personId = env.getArgument("id");
			return personFacade.find(personId);
		};
	}

	public DataFetcher<List<Person>> getPersons() {
		return env -> {
			return personFacade.findAll();
		};
	}

}
