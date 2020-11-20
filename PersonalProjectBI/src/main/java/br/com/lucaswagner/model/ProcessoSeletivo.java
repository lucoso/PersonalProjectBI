package br.com.lucaswagner.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ProcessoSeletivo extends Projeto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="Projeto_Seletivo_Aprovado", nullable=true)
	private boolean aprovado;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "Projeto_Seletivo_EmpresaResponsavel_ID")
	private Empresa empresaResponsavel;

	public ProcessoSeletivo(Long id, String nome, LocalDate dataInicio, LocalDate dataFinalizado, boolean finalizado,
			Usuario usuario, String tipo, boolean aprovado, Empresa empresaResponsavel) {
		super(id, nome, dataInicio, dataFinalizado, finalizado, usuario, tipo);
		this.aprovado = aprovado;
		this.empresaResponsavel = empresaResponsavel;
	}

	public ProcessoSeletivo(){
		
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Empresa getEmpresaResponsavel() {
		return empresaResponsavel;
	}

	public void setEmpresaResponsavel(Empresa empresaResponsavel) {
		this.empresaResponsavel = empresaResponsavel;
	}
	
	

}
