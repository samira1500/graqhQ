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
import org.entities.Car;

import io.leangen.graphql.annotations.GraphQLArgument;

@Stateless
@LocalBean
public class CarFacade implements PersonFacadeLocal{
	
	@PersistenceContext(name = "test-pu")
	private EntityManager em;

	public CarFacade() {
	}

	public Car save(Car p) throws Exception {
		return em.merge(p);
	}

	public List<Car> saveAll(List<Car> cars) throws Exception {
		for (int i = 0; i < cars.size(); i++) {
			cars.set(i, save(cars.get(i)));
		}
		return cars;
	}

	public List<Car> findAll() throws Exception {
		CriteriaBuilder cbuilder = em.getCriteriaBuilder();
		CriteriaQuery<Car> cQuery = cbuilder.createQuery(Car.class);
		Root<Car> root = cQuery.from(Car.class);
		cQuery.select(root);
		TypedQuery<Car> query = em.createQuery(cQuery);
		return query.getResultList();
	}

	public Car find(@GraphQLArgument(name = "id") int id) {
		return em.find(Car.class, id);
	}
}
