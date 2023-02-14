package telran.spring.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.data.entities.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>{

}
