package org.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.github.vladislavsevruk.generator.annotation.GqlField;
import com.github.vladislavsevruk.generator.annotation.GqlIgnore;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
public class Person implements Serializable {

	@GqlIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@GqlField
	private String name;

	@GqlField
	private String lastName;

	@GqlField(withSelectionSet = true)
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSON_ID")
	private List<Car> cars = new ArrayList<>();

	public Person() {

	}

	public Person(String name, String lastName) {
		super();
		this.name = name;
		this.lastName = lastName;
	}

	@GraphQLQuery(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@GraphQLQuery(name = "lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

}
