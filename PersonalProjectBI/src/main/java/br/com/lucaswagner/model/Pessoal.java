package br.com.lucaswagner.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Pessoal extends Projeto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="Projeto_Pessoal_EmProducao", nullable=true)
	private boolean emProducao;

	public Pessoal(Long id, String nome, LocalDate dataInicio, LocalDate dataFinalizado, boolean finalizado,
			Status status, Usuario usuario, String tipo, boolean emProducao) {
		super(id, nome, dataInicio, dataFinalizado, finalizado, status, usuario, tipo);
		this.emProducao = emProducao;
	}

	public Pessoal(){
		
	}

	public boolean isEmProducao() {
		return emProducao;
	}

	public void setEmProducao(boolean emProducao) {
		this.emProducao = emProducao;
	}

}
