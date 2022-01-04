package br.com.telefonica.scheduling.job.dataprovider.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.telefonica.scheduling.job.dataprovider.entity.JobEntity;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {
	
	List<JobEntity> findAll();
	
}
