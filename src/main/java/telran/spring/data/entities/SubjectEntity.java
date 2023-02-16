package telran.spring.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name="subjects")
public class SubjectEntity {

	@Id
	@Column(name="suid")
	long id;
	
	@Column(unique = true)
	String subject;
	
	public SubjectEntity() {
	}

	public SubjectEntity(long id, String subject) {
		this.id = id;
		this.subject = subject;
	}

	public long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}
	
}
