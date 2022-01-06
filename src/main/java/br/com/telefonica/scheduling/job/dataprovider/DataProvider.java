package br.com.telefonica.scheduling.job.dataprovider;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.telefonica.scheduling.job.dataprovider.entity.JobEntity;
import br.com.telefonica.scheduling.job.dataprovider.repository.JobRepository;

@Component
public class DataProvider {

	@Autowired
	private JobRepository jobRepository;

	public List<JobEntity> findAllJobs(){

		Comparator<JobEntity> porData =
				(JobEntity time1, JobEntity time2) -> time1.getDataMaximaConclusao().compareTo( time2.getDataMaximaConclusao());
		List<JobEntity> listaEntities = jobRepository.findAll();
		listaEntities.sort(porData);
		return listaEntities;
	}
}