package telran.spring.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.data.entities.MarkEntity;
import telran.spring.data.proj.*;

public interface MarkRepository extends JpaRepository<MarkEntity, Long>{	

	static final String FROM_STUDENTS_MARKS = "from students st join marks ms on st.stid = ms.stid ";
	static final String FROM_STUDENTS_SUBJECTS_MARKS = FROM_STUDENTS_MARKS + "join subjects sb on sb.suid = ms.suid ";
	static final String MIN_INTERVAL = " floor(mark/:interval) * :interval ";

	List<MarkProj> findByStudentNameAndSubjectSubject(String name, String subject);
	
	@Query(value = "select name, subject, mark "
			+ FROM_STUDENTS_SUBJECTS_MARKS
			+ "where name = :name ", nativeQuery = true)
	List<StudentSubjectMark> findByStudentName(String name);
	
	@Query(value="select name, round(avg(mark)) as avg " 
			+ FROM_STUDENTS_MARKS 
			+ "group by name", nativeQuery = true)
	List<StudentAvgMark> studentsAvgMark();
	
//	@Query(value="select name " 
//			+ FROM_STUDENTS_MARKS 
//			+ "group by name having avg(mark) >"
//			+ " (select avg(mark) from marks)",	nativeQuery = true)
	@Query("select student.name as name from MarkEntity group by student.name "
			+ "having avg(mark) > (select avg(mark) from MarkEntity)")
	List<StudentName> bestStudents();
	
	@Query(value="select name " 
			+ FROM_STUDENTS_MARKS 
			+ "group by name order by avg(mark) desc "
			+ "limit :quantity", nativeQuery = true)
	List<StudentName> topBestStudents(int quantity);
	
	@Query(value="select name " 
			+ FROM_STUDENTS_SUBJECTS_MARKS 
			+ "where subject = :subject "
			+ "group by name "
			+ "order by avg(mark) desc "
			+ "limit :quantity", nativeQuery = true)
	List<StudentName> topBestStudentsSubject(int quantity, String subject);
	
	@Query(value = "select name, subject, mark "
			+ FROM_STUDENTS_SUBJECTS_MARKS
			+ "where ms.stid in (select stid from marks "
			+ "group by stid order by avg(mark) limit :quantity) "
			+ "order by 1", nativeQuery = true)
	List<StudentSubjectMark> marksOfWorstStudents(int quantity);
	
//	@Query("select " + MIN_INTERVAL + "as min, "
//			+ MIN_INTERVAL + "+ :interval - 1 as max, "
//			+ "count(mark) as occurrences "
//			+ "from marks "
//			+ "group by min, max "
//			+ "order by 1")
	@Query("select " + MIN_INTERVAL + "as min, "
			+ MIN_INTERVAL + "+ :interval - 1 as max, "
			+ "count(mark) as occurrences "
			+ "from MarkEntity "
			+ "group by min, max "
			+ "order by 1")
	List<IntervalMarksCount> marksDistribution(int interval);

	List<MarkEntity> findByStudentId(long id);
	
}
