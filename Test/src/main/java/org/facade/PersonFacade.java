package org.facade;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.entities.Person;

import io.leangen.graphql.annotations.GraphQLArgument;

/**
 * Session Bean implementation class PersonFacade
 */
@Stateless
@LocalBean
public class PersonFacade implements PersonFacadeLocal {

	@PersistenceContext(name = "test-pu")
	private EntityManager em;

	public PersonFacade() {
	}

	public Person save(Person p) throws Exception {
		return em.merge(p);
	}

	public List<Person> saveAll(List<Person> persons) throws Exception {
		for (int i = 0; i < persons.size(); i++) {
			persons.set(i, save(persons.get(i)));
		}
		return persons;
	}


	public List<Person> findAll() throws Exception {
		CriteriaBuilder cbuilder = em.getCriteriaBuilder();
		CriteriaQuery<Person> cQuery = cbuilder.createQuery(Person.class);
		Root<Person> root = cQuery.from(Person.class);
		cQuery.select(root);
		TypedQuery<Person> query = em.createQuery(cQuery);
		return query.getResultList();
	}
	
	public Person find(@GraphQLArgument(name = "id") int id) {
		return em.find(Person.class, id);
	}

}
