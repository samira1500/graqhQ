package org.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.entities.Person;
import org.facade.PersonFacade;

@Named("personcontroller")
@ViewScoped
public class PersonController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Person person;
	private List<Person> persons = new ArrayList<>();

	@Inject
	private PersonFacade personFacade;

	@PostConstruct
	public void init() {
		try {
			persons = personFacade.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillListOfPerson() {
		persons = Arrays.asList(new Person("samira", "khashab"), new Person("ali", "ahmad"),
				new Person("hussein", "hamieh"), new Person("rami", "demerji"));
	}

	public void saveAll() {
		try {
			persons = personFacade.saveAll(persons);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
