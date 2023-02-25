package telran.spring.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.data.entities.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>{

	@Query("select subjects from SubjectEntity subjects "
			+ "where subject in (select ms.subject "
			+ "from MarkEntity me "
			+ "right join me.subject ms "
			+ "group by ms.subject "
			+ "having count(mark) < :marksThreshold)")
	List<SubjectEntity> leastPopularSubject(int marksThreshold);

}
