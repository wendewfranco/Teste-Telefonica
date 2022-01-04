package br.com.telefonica.scheduling.job.dataprovider.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobEntity {
	
	@Id
	private Integer id;
	
	private String descricao;
	
	private LocalDateTime dataMaximaConclusao;
	
	private LocalDateTime tempoEstimado;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataMaximaConclusao() {
		return dataMaximaConclusao;
	}

	public void setDataMaximaConclusao(LocalDateTime dataMaximaConclusao) {
		this.dataMaximaConclusao = dataMaximaConclusao;
	}

	public LocalDateTime getTempoEstimado() {
		return tempoEstimado;
	}

	public void setTempoEstimado(LocalDateTime tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}
}
