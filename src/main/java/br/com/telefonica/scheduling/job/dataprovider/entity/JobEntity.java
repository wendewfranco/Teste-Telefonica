package br.com.telefonica.scheduling.job.dataprovider.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobEntity {
	
	@Id
	private UUID id;
	
	private String descricao;
	
	private Date dataMaximaConclusao;
	
	private Date tempoEstimado;
}
