package telran.spring.data.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import telran.spring.data.entities.*;
import telran.spring.data.model.Mark;
import telran.spring.data.model.Student;
import telran.spring.data.model.Subject;
import telran.spring.data.proj.IntervalMarksCount;
import telran.spring.data.proj.MarkProj;
import telran.spring.data.proj.StudentAvgMark;
import telran.spring.data.proj.StudentName;
import telran.spring.data.proj.StudentSubjectMark;
import telran.spring.data.repo.*;

@Service
@Transactional(readOnly = true)
public class CollegeServiceImpl implements CollegeService {

	StudentRepository studentRepository;
	SubjectRepository subjectRepository;
	MarkRepository markRepository;
	EntityManager em;
	
	public CollegeServiceImpl(StudentRepository studentRepository, SubjectRepository subjectRepository,
			MarkRepository markRepository, EntityManager em) {
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
		this.markRepository = markRepository;
		this.em = em;
	}

	@Override
	@Transactional
	public void addStudent(Student student) {
		if(studentRepository.existsById(student.id)) {
			throw new IllegalStateException(String.format("Student with id %d already exist", student.id));
		}
		studentRepository.save(new StudentEntity(student.id, student.name));
	}

	@Override
	@Transactional
	public void addSubject(Subject subject) {
		if(subjectRepository.existsById(subject.id)) {
			throw new IllegalStateException(String.format("Subject with id %d already exist", subject.id));
		}
		subjectRepository.save(new SubjectEntity(subject.id, subject.subject));
	}

	@Override
	@Transactional
	public void addMark(Mark mark) {
		StudentEntity student = studentRepository.findById(mark.studId).orElse(null);
		if(student == null) {
			throw new NoSuchElementException(String.format("Student with id %d does not exist", mark.studId));
		}
		SubjectEntity subject = subjectRepository.findById(mark.subjId).orElse(null);
		if(subject == null) {
			throw new NoSuchElementException(String.format("Subject with id %d does not exist", mark.subjId));
		}
		MarkEntity markEntity = new MarkEntity(student, subject, mark.mark);
		markRepository.save(markEntity);
	}

	@Override
	@Transactional
	public List<MarkProj> getMarksByNameAndSubject(String name, String subject) {
		return markRepository.findByStudentNameAndSubjectSubject(name, subject);
	}

	@Override
	public List<StudentSubjectMark> getMarksByName(String name) {
		return markRepository.findByStudentName(name);
	}

	@Override
	public List<StudentAvgMark> getStudentsAvgMark() {
		return markRepository.studentsAvgMark();
	}

	@Override
	public List<StudentName> getBestStudent() {
		return markRepository.bestStudents();
	}

	@Override
	public List<StudentName> getTopBestStudents(int nStudents) {
		return markRepository.topBestStudents(nStudents);
	}

	@Override
	public List<StudentName> getTopBestStudentsSubject(int nStudents, String subject) {
		return markRepository.topBestStudentsSubject(nStudents, subject);
	}

	@Override
	public List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents) {
		return markRepository.marksOfWorstStudents(nStudents);
	}

	@Override
	public List<IntervalMarksCount> marksDistribution(int interval) {
		return markRepository.marksDistribution(interval);
	}

	@Override
	public List<String> getSqlQuery(String sqlQuery) {
		var query = em.createNativeQuery(sqlQuery);
		return getResult(query);
	}

	@Override
	public List<String> getJpqlQuery(String jpqlQuery) {
		var query = em.createQuery(jpqlQuery);
		return getResult(query);
	}

	@SuppressWarnings("unchecked")
	private List<String> getResult(Query query) {
		var list = query.getResultList();
		List<String> res = Collections.emptyList();
		if(!list.isEmpty()) {
			res = list.get(0).getClass().isArray() 
					? multiProjectionResult(list)
							: singleProjectionResult(list);			
		}
		return res;
	}

	private List<String> multiProjectionResult(List<Object[]> list) {
		return list.stream().map(Arrays::deepToString).toList();
	}

	private List<String> singleProjectionResult(List<Object> list) {
		return list.stream().map(Object::toString).toList();
	}

	@Override
	@Transactional
	public List<String> removeStudents(double markCountLess) {
		List<StudentEntity> studentsToRemove = studentRepository.worstStudents(markCountLess);
		studentsToRemove.forEach(studentRepository::delete);
		List<String> removedStudentNames = studentsToRemove.stream().map(StudentEntity::getName).toList();
		return removedStudentNames;
	}

	@Override
	@Transactional
	public List<String> removeLeastPopularSubject(int marksThreshold) {
		List<SubjectEntity> subjectsToRemove = subjectRepository.leastPopularSubject(marksThreshold);
		subjectsToRemove.forEach(subjectRepository::delete);
		List<String> removedSubjects = subjectsToRemove.stream().map(SubjectEntity::getSubject).toList();
		return removedSubjects;
	}

}
