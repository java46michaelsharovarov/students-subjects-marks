package telran.spring.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.data.entities.MarkEntity;
import telran.spring.data.proj.*;

public interface MarkRepository extends JpaRepository<MarkEntity, Long>{

	List<MarkProj> findByStudentNameAndSubjectSubject(String name, String subject);
	@Query(value = "select name, subject, mark "
			+ "from students "
			+ "join marks on students.stid = marks.stid "
			+ "join subjects on subjects.suid = marks.suid "
			+ "where name = :name ", nativeQuery = true)
	List<StudentSubjectMark> findByStudentName(String name);
	
}
