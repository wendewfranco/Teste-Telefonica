package br.com.telefonica.scheduling.job.dataprovider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.telefonica.scheduling.job.dataprovider.entity.JobEntity;
import br.com.telefonica.scheduling.job.dataprovider.repository.JobRepository;

@EnableScheduling
@Component
public class DataProvider {

	@Autowired
	private JobRepository jobRepository;
	
	private boolean controler = false;
	
	private int horaMaximaSomagetDataMaximaConclusao = 8;
	
	@Scheduled(cron = "1 * * * * *")
	public void executarJob(){

		LocalDateTime JanelaDeExecucaoInicial = LocalDateTime.of(2019, 11, 10, 9, 00, 00);
		LocalDateTime JanelaDeExecucaoFinal = LocalDateTime.of(2019, 11, 11, 12, 00, 00);

		List<Integer> listaDeJobsJaExecutados = new ArrayList<>();
		List<List<Integer>> ListaDeJobsProcessados = new ArrayList<>();


		Comparator <JobEntity> porData =
				(JobEntity time1, JobEntity time2) -> time1.getDataMaximaConclusao().compareTo( time2.getDataMaximaConclusao());

		List<JobEntity> findAllJobs = jobRepository.findAll();
		findAllJobs.sort(porData);

		System.out.println("TESTE");

		findAllJobs.forEach(job -> {
			horaMaximaSomagetDataMaximaConclusao = 8;


			List<Integer> listaDeIds = new ArrayList<>();
			while(horaMaximaSomagetDataMaximaConclusao != 0) {
				findAllJobs.forEach(nextJob -> {
					System.out.println("PARANDO FOREACH");
					if(job.getTempoEstimado() + nextJob.getTempoEstimado() == horaMaximaSomagetDataMaximaConclusao
						&& job.getId() != nextJob.getId()) {

						System.out.println("TESTE");

						listaDeIds.add(job.getId());
						listaDeIds.add(nextJob.getId());
						ListaDeJobsProcessados.add(listaDeIds);

						controler = true;
						return;
					}
					if(horaMaximaSomagetDataMaximaConclusao == 0) {
						listaDeIds.add(job.getId());
						controler = true;
					}
			});
				if(controler == true) {
					return;
				}
				horaMaximaSomagetDataMaximaConclusao--;
			}
		});

		System.out.println(ListaDeJobsProcessados);
	}
	
}