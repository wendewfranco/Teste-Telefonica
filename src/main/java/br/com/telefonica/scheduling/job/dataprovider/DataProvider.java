package br.com.telefonica.scheduling.job.dataprovider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.telefonica.scheduling.job.dataprovider.entity.JobEntity;
import br.com.telefonica.scheduling.job.dataprovider.repository.JobRepository;

@EnableScheduling
public class DataProvider {

	@Autowired
	private JobRepository jobRepository;
	
	private int horaMaximaSomagetDataMaximaConclusao = 8;
	
	@Scheduled(cron = "* * * * * *")
	List<List<Integer>>executarJob(LocalDate JanelaExecucao){
		
		
		
		List<List<Integer>> ListaDeJobsProcessados = null;
		
		List<JobEntity> findAllJobs = jobRepository.findAll();
	
		findAllJobs.forEach(job -> {
			for (int i = 0; i < findAllJobs.size(); i++) {
				List<Integer> listaDeIds = new ArrayList<>();
				if(job.getDataMaximaConclusao().getHour() + findAllJobs.get(i).getTempoEstimado().getHour() == horaMaximaSomagetDataMaximaConclusao
						&& job.getId() != findAllJobs.get(i).getId() || job.getTempoEstimado().getHour() == 8) {
					listaDeIds.add(job.getId());
					listaDeIds.add(findAllJobs.get(i).getId());	
					ListaDeJobsProcessados.add(listaDeIds);
					return;
				} 
				
				if(findAllJobs.size() == findAllJobs.size()) {
					listaDeIds.add(job.getId());
					listaDeIds.add(findAllJobs.get(i).getId());	
					ListaDeJobsProcessados.add(listaDeIds);
					horaMaximaSomagetDataMaximaConclusao = horaMaximaSomagetDataMaximaConclusao -1 ;
					i = 0;
				}
				if(horaMaximaSomagetDataMaximaConclusao == 1) {
					listaDeIds.add(job.getId());
					listaDeIds.add(findAllJobs.get(i).getId());	
					ListaDeJobsProcessados.add(listaDeIds);
					return;
				}
			}
		});
			 
		return ListaDeJobsProcessados;
	}
	
}