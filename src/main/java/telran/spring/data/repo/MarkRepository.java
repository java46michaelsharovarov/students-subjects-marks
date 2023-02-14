package telran.spring.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.data.entities.MarkEntity;

public interface MarkRepository extends JpaRepository<MarkEntity, Long>{

}
