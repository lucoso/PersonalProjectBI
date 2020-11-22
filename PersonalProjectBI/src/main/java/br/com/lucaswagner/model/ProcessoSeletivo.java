package br.com.lucaswagner.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProcessoSeletivo extends Projeto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="Projeto_Seletivo_Aprovado", nullable=true)
	private boolean aprovado;
	
	@Column(name="Projeto_Seletivo_Cidade", nullable=true)
	private String cidade;
	
	@ManyToOne
	@JoinColumn(name = "Projeto_Seletivo_EmpresaResponsavel_ID")
	private Empresa empresaResponsavel;

	public ProcessoSeletivo(Long id, String nome, LocalDate dataInicio, LocalDate dataFinalizado, boolean finalizado,
			Status status, Usuario usuario, String tipo, boolean aprovado, String cidade, Empresa empresaResponsavel) {
		super(id, nome, dataInicio, dataFinalizado, finalizado, status, usuario, tipo);
		this.aprovado = aprovado;
		this.cidade = cidade;
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	

}
