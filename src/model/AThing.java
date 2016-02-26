package model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import jpa.JpaManager;

/**
 * Entity implementation class for Entity: Scenario
 *
 */
//@Entity
//@Table(name="AThing")
//@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public abstract class AThing implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false, unique = true)
	public String name;

	public long getId() {
		return id;
	}

	public AThing() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void persist(){
		JpaManager.persist(this);
	}
	
	public void update(){
		JpaManager.update(this);
	}
	
	@Override
	public String toString(){
		return name;
	}

}
