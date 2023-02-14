package telran.spring.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import telran.spring.data.model.*;
import telran.spring.data.service.CollegeService;

@Component
public class RandomDbCreation {

	Logger LOG = LoggerFactory.getLogger(RandomDbCreation.class);
	
	@Value("${app.marks.amount : 100}")
	int numberOfMarks;	
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	String hibernate;
	
	@Autowired
	CollegeService collegeService;
	
	String names[] = { "Abraham", "Sarah", "Itshak", "Rahel", "Asaf", "Yacob", "Rivka", "Yosef", "Benyanim",
			"Dan","Ruben", "Moshe", "Aron", "Yehashua", "David", "Salomon", "Nefertity", "Naftaly", "Natan", "Asher" };
	
	String subjects[] = { "Java core", "Java Technologies", "Spring Data", "Spring Security",
			"Spring Cloud", "CSS", "HTML", "JS", "React", "Material-UI" }; 
	
	HashMap<Long, HashSet<Long>> subjectStudents = new HashMap<>();
	
	@PostConstruct
	void dbCreation() {
		if(hibernate.equalsIgnoreCase("create")) {
			studentsCreation();
			subjectsCreation();
			marksCreation();
			LOG.info("database was created successfully");
		} else {
			LOG.warn("it is not possible to create a database, the value of the Hibernate property is '{}', but it should be 'create'", hibernate);
		}		
	}

	private void studentsCreation() {
		IntStream.range(0, names.length)
		.forEach(i -> collegeService.addStudent(new Student(i + 1, names[i])));	
		LOG.info("all students are added");
	}

	private void subjectsCreation() {
		IntStream.range(0, subjects.length)
		.forEach(i -> collegeService.addSubject(new Subject(i + 1, subjects[i])));
		LOG.info("all subjects are added");
	}

	private void marksCreation() {
		IntStream.rangeClosed(1, numberOfMarks)
		.forEach(i -> addMark());	
		LOG.info("all marks are added");
	}

/**** one subject many marks ****/
//	private void addMark() {
//		collegeService.addMark(new Mark(getRandomNumber(1, names.length),
//				getRandomNumber(1, subjects.length),
//				getRandomNumber(1, 100)));
//	}
	
/**** one subject one mark ****/	
	private void addMark() {
		long subjectId = getRandomNumber(1, subjects.length);
		long studentId;
		do {
			studentId = getRandomNumber(1, names.length);
		} while(!subjectStudents.computeIfAbsent(subjectId, k -> new HashSet<Long>()).add(studentId));		
		collegeService.addMark(new Mark(studentId,	subjectId, getRandomNumber(1, 100)));
	}

	private int getRandomNumber(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}
}
