package br.com.telefonica.scheduling.job.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.telefonica.scheduling.job.dataprovider.DataProvider;
import br.com.telefonica.scheduling.job.dataprovider.entity.JobEntity;

@EnableScheduling
@Component
public class JobService {
	
	@Autowired
    private DataProvider jobDataProvider;

    @Value("${jobMaxTimeInicial.localDate}")
    private String janelaDeExecucaoInicialVariavel;

    @Value("${jobMaxTimeFinal.localDate}")
    private String janelaDeExecucaoFinalVariavel;

    private int horaMaximaSomagetDataMaximaConclusao = 8;
    private int controle = 0;
    private JobEntity auxJob;

    @Scheduled(cron = "1 * * * * *")
    public void SchedulerJob() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime janelaDeExecucaoInicial = LocalDateTime.parse(janelaDeExecucaoInicialVariavel, formatter);
        LocalDateTime janelaDeExecucaoFinal = LocalDateTime.parse(janelaDeExecucaoFinalVariavel, formatter);

        List<JobEntity> listaDeJobsJaExecutados = new ArrayList<>();
        List<List<Integer>> listaComIdsJobsProcessados = new ArrayList<>();
        List<Integer> listaDeIds = new ArrayList<>();

        List<JobEntity> listaEntities = jobDataProvider.findAllJobs();

        while(!listaEntities.isEmpty()) {
            listaEntities.stream().anyMatch(job -> {
                if (job.getDataMaximaConclusao().isAfter(janelaDeExecucaoFinal) ) {
                    listaDeJobsJaExecutados.add(job);
                    return true;
                }
                listaEntities.stream().anyMatch(nextJob -> {
                    if (job.getTempoEstimado() + nextJob.getTempoEstimado() == horaMaximaSomagetDataMaximaConclusao
                            && job.getId() != nextJob.getId()) {
                        listaDeIds.add(job.getId());
                        listaDeIds.add(nextJob.getId());
                        listaDeJobsJaExecutados.add(nextJob);
                        return true;
                    } else if (job.getTempoEstimado() + nextJob.getTempoEstimado() > controle
                            && job.getTempoEstimado() + nextJob.getTempoEstimado() < 9
                            && job.getId() != nextJob.getId()) {
                        auxJob = nextJob;
                        controle = job.getTempoEstimado() + nextJob.getTempoEstimado();
                    }
                    return false;
                });
                if (listaDeIds.isEmpty() && auxJob != null) {
                    listaDeIds.add(auxJob.getId());
                    listaDeIds.add(job.getId());
                    listaDeJobsJaExecutados.add(auxJob);
                    listaDeJobsJaExecutados.add(job);
                } else if (listaDeIds.isEmpty() && auxJob == null) {
                    listaDeIds.add(job.getId());
                    listaDeJobsJaExecutados.add(job);
                    return true;
                }
                listaDeJobsJaExecutados.add(job);

                return true;
            });
            if(!listaDeIds.isEmpty()) {
                listaComIdsJobsProcessados.add(new ArrayList<>(listaDeIds));
            }
            listaEntities.removeAll(listaDeJobsJaExecutados);
            listaDeIds.clear();
            listaDeJobsJaExecutados.clear();
            controle = 0;
            auxJob = null;
        }
        System.out.println(listaComIdsJobsProcessados);

    }

}
