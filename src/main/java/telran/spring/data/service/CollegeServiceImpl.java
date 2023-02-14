package telran.spring.data.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.data.entities.*;
import telran.spring.data.model.Mark;
import telran.spring.data.model.Student;
import telran.spring.data.model.Subject;
import telran.spring.data.repo.*;

@Service
@Transactional(readOnly = true)
public class CollegeServiceImpl implements CollegeService {

	StudentRepository studentRepository;
	SubjectRepository subjectRepository;
	MarkRepository markRepository;
	
	public CollegeServiceImpl(StudentRepository studentRepository, SubjectRepository subjectRepository,
			MarkRepository markRepository) {
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
		this.markRepository = markRepository;
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

}
