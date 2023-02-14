package telran.spring.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class StudentEntity {

	@Id
	long id;
	
	@Column(unique = true)
	String name;
	
	public StudentEntity() {
	}

	public StudentEntity(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
