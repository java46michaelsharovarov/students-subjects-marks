package telran.spring.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.data.entities.MarkEntity;
import telran.spring.data.proj.*;

public interface MarkRepository extends JpaRepository<MarkEntity, Long>{	

	String FROM_JOIN = "from marks "
			+ "join students on students.stid = marks.stid "
			+ "join subjects on subjects.suid = marks.suid ";

	List<MarkProj> findByStudentNameAndSubjectSubject(String name, String subject);
	
	@Query(value = "select name, subject, mark "
			+ FROM_JOIN
			+ "where name = :name ", nativeQuery = true)
	List<StudentSubjectMark> findByStudentName(String name);
	
	@Query(value = "select name, cast(avg(mark) as DECIMAL(7,2)) "
			+ FROM_JOIN
			+ "group by students.stid", nativeQuery = true)
	List<StudentAvgMark> studentsAvgMark();
	
	@Query(value = "select name from (select name, cast(avg(mark) as DECIMAL(7,2)) as avg "
			+ FROM_JOIN
			+ "group by students.stid) as a "
			+ "where avg > (select avg(mark) from marks)", nativeQuery = true)
	List<StudentName> bestStudents();
	
	@Query(value = "select distinct name "
			+ FROM_JOIN
			+ "where marks.stid in (select stid from marks "
			+ "group by stid order by avg(mark) desc limit :quantity) "
			+ "order by 1", nativeQuery = true)
	List<StudentName> topBestStudents(int quantity);
	
	@Query(value = "select name "
			+ FROM_JOIN
			+ "where marks.suid in (select suid from subjects "
			+ "where subject like :subject) "
			+ "order by mark desc "
			+ "limit :quantity", nativeQuery = true)
	List<StudentName> topBestStudentsSubject(int quantity, String subject);
	
	@Query(value = "select name, subject, mark "
			+ FROM_JOIN
			+ "where marks.stid in (select stid from marks "
			+ "group by stid order by avg(mark) limit :quantity) "
			+ "order by 1", nativeQuery = true)
	List<StudentSubjectMark> marksOfWorstStudents(int quantity);
	
	@Query(value = "select floor(mark/10) * 10 as min, "
			+ "floor(mark/10) * 10 + 9 as max, "
			+ "count(mark) as occurrences "
			+ "from marks "
			+ "group by 1 "
			+ "order by 1", nativeQuery = true)
	List<IntervalMarksCount> marksDistribution(int interval);
	
}
