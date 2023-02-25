package telran.spring.data.service;

import java.util.List;

import telran.spring.data.model.*;
import telran.spring.data.proj.*;

public interface CollegeService {

	void addStudent(Student student);
	void addSubject(Subject subject);
	void addMark(Mark mark);
	List<MarkProj> getMarksByNameAndSubject(String name, String subject);
	List<StudentSubjectMark> getMarksByName(String name);
	List<StudentAvgMark> getStudentsAvgMark();
	List<StudentName> getBestStudent();
	List<StudentName> getTopBestStudents(int nStudents);
	List<StudentName> getTopBestStudentsSubject(int nStudents, String subject);
	List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents);
	List<IntervalMarksCount> marksDistribution(int interval);
	List<String> getSqlQuery(String sqlQuery);
	List<String> getJpqlQuery(String jpqlQuery);
	List<String> removeStudents(double markCountLess); //removing all students having avg(mark) * count(mark) less then given value
	List<String> removeLeastPopularSubject(int marksThreshold);
	void increaseMarksStudent(long id, int delta);
	
}
