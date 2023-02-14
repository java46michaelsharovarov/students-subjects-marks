package telran.spring.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name="marks")
public class MarkEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	StudentEntity student;
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	SubjectEntity subject;
	
	int mark;
	
	public MarkEntity() {
	}

	public MarkEntity(StudentEntity student, SubjectEntity subject, int mark) {
		this.student = student;
		this.subject = subject;
		this.mark = mark;
	}

	public long getId() {
		return id;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public int getMark() {
		return mark;
	}
	
}
