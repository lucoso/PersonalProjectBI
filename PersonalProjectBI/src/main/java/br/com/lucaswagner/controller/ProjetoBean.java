package br.com.lucaswagner.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lucaswagner.manager.EmpresaManager;
import br.com.lucaswagner.manager.ProjetoManager;
import br.com.lucaswagner.manager.UsuarioManager;
import br.com.lucaswagner.model.Empresa;
import br.com.lucaswagner.model.Pessoal;
import br.com.lucaswagner.model.ProcessoSeletivo;
import br.com.lucaswagner.model.Projeto;
import br.com.lucaswagner.model.Usuario;
import br.com.lucaswagner.util.FacesMessages;
import br.com.lucaswagner.util.JavaUtil;

@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Projeto projeto;

	private Projeto projetoSelecionado;

	private Projeto projetoParaExcluir;

	private Usuario u;

	private boolean atualizar = false;

	private LazyDataModel<Projeto> projetos;

	private String tipoProjeto;
	
	private boolean aprovado;
	
	private boolean emProducao;
	
	private boolean aprovadoFinalizar;
	
	private boolean emProducaoFinalizar;
	
	private Empresa empresaResponsavel;
	
	private Date dataInicio;
	
	private Date dataFinalizado;
	
	private String extraEmProducao;
	
	private String extraAprovado;
	
	private String extraEmpresa;
	
	private String cor;
	
	private boolean pessoal;
	
	private boolean seletivo;
	
	private ProcessoSeletivo seletivoParaAtualizar;
	
	private Pessoal pessoalParaAtualizar;

	private ProjetoManager pm = new ProjetoManager();
	
	private EmpresaManager em = new EmpresaManager();

	private FacesMessages messages = new FacesMessages();

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public Projeto getProjetoParaExcluir() {
		return projetoParaExcluir;
	}

	public void setProjetoParaExcluir(Projeto projetoParaExcluir) {
		this.projetoParaExcluir = projetoParaExcluir;
	}

	public Usuario getU() {
		return u;
	}

	public void setU(Usuario u) {
		this.u = u;
	}

	public boolean isAtualizar() {
		return atualizar;
	}

	public void setAtualizar(boolean atualizar) {
		this.atualizar = atualizar;
	}

	public LazyDataModel<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(LazyDataModel<Projeto> projetos) {
		this.projetos = projetos;
	}

	public String getTipoProjeto() {
		return tipoProjeto;
	}

	public void setTipoProjeto(String tipoProjeto) {
		this.tipoProjeto = tipoProjeto;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public boolean isEmProducao() {
		return emProducao;
	}

	public void setEmProducao(boolean emProducao) {
		this.emProducao = emProducao;
	}

	public boolean isAprovadoFinalizar() {
		return aprovadoFinalizar;
	}

	public void setAprovadoFinalizar(boolean aprovadoFinalizar) {
		this.aprovadoFinalizar = aprovadoFinalizar;
	}

	public boolean isEmProducaoFinalizar() {
		return emProducaoFinalizar;
	}

	public void setEmProducaoFinalizar(boolean emProducaoFinalizar) {
		this.emProducaoFinalizar = emProducaoFinalizar;
	}

	public Empresa getEmpresaResponsavel() {
		return empresaResponsavel;
	}

	public void setEmpresaResponsavel(Empresa empresaResponsavel) {
		this.empresaResponsavel = empresaResponsavel;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinalizado() {
		return dataFinalizado;
	}

	public String getExtraEmProducao() {
		return extraEmProducao;
	}

	public void setExtraEmProducao(String extraEmProducao) {
		this.extraEmProducao = extraEmProducao;
	}

	public String getExtraAprovado() {
		return extraAprovado;
	}

	public void setExtraAprovado(String extraAprovado) {
		this.extraAprovado = extraAprovado;
	}

	public String getExtraEmpresa() {
		return extraEmpresa;
	}

	public void setExtraEmpresa(String extraEmpresa) {
		this.extraEmpresa = extraEmpresa;
	}

	public void setDataFinalizado(Date dataFinalizado) {
		this.dataFinalizado = dataFinalizado;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public boolean isPessoal() {
		return pessoal;
	}

	public void setPessoal(boolean pessoal) {
		this.pessoal = pessoal;
	}

	public boolean isSeletivo() {
		return seletivo;
	}

	public void setSeletivo(boolean seletivo) {
		this.seletivo = seletivo;
	}

	public ProcessoSeletivo getSeletivoParaAtualizar() {
		return seletivoParaAtualizar;
	}

	public void setSeletivoParaAtualizar(ProcessoSeletivo seletivoParaAtualizar) {
		this.seletivoParaAtualizar = seletivoParaAtualizar;
	}

	public Pessoal getPessoalParaAtualizar() {
		return pessoalParaAtualizar;
	}

	public void setPessoalParaAtualizar(Pessoal pessoalParaAtualizar) {
		this.pessoalParaAtualizar = pessoalParaAtualizar;
	}

	/**
	 * Métodos
	 * 
	 * @author Lucas Manhães
	 */

	public void BuscarUsuario() {
		String str = SecurityContextHolder.getContext().getAuthentication().getName();
		UsuarioManager um = new UsuarioManager();
		u = um.BuscarUmUsuarioPorLogin(str);
	}

	public void Inicializar() {

		projetos = new LazyDataModel<Projeto>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			List<Projeto> projetosList;

			public List<Projeto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				projetosList = pm.BuscarTodosProjetosLazy(first, pageSize, filters);
				
				for(Projeto p : projetosList){
					if(p instanceof Pessoal){
						p.setTipo("Pessoal");
					}else{
						p.setTipo("Seletivo");
					}
				}

				setRowCount(pm.ContarTodosProjetos());

				return projetosList;

			}

			@Override
			public Object getRowKey(Projeto projeto) {
				return projeto.getId();
			}

			@Override
			public Projeto getRowData(String projetoid) {

				Long id = Long.valueOf(projetoid);

				for (Projeto p : projetosList) {
					if (id.equals(p.getId())) {
						return p;
					}
				}

				return null;
			}
		};

	}
	
	private void Limpar(){
		tipoProjeto = null;
		dataInicio = null;
		dataFinalizado = null;
		empresaResponsavel = null;
		projetoSelecionado = null;
	}

	public void AtualizarProjetoEmEdicao() {

		projeto = projetoSelecionado;
		
		if(projetoSelecionado instanceof ProcessoSeletivo){
			seletivoParaAtualizar = (ProcessoSeletivo) projeto;
			aprovado = seletivoParaAtualizar.isAprovado();
			empresaResponsavel = seletivoParaAtualizar.getEmpresaResponsavel();
			dataInicio = JavaUtil.convertToDate(seletivoParaAtualizar.getDataInicio());
			if(seletivoParaAtualizar.getDataFinalizado() != null){
				dataFinalizado = JavaUtil.convertToDate(seletivoParaAtualizar.getDataFinalizado());
			}else{
				dataFinalizado = null;
			}
			
			tipoProjeto = "Seletivo";
		}
		
		if(projetoSelecionado instanceof Pessoal){
			pessoalParaAtualizar = (Pessoal) projeto;
			emProducao = pessoalParaAtualizar.isEmProducao();
			dataInicio = JavaUtil.convertToDate(pessoalParaAtualizar.getDataInicio());
			if(pessoalParaAtualizar.getDataFinalizado() != null){
				dataFinalizado = JavaUtil.convertToDate(pessoalParaAtualizar.getDataFinalizado());
			}else{
				dataFinalizado = null;
			}
			
			tipoProjeto = "Pessoal";
		}
		
		atualizar = true;
	}

	public void ProjetoSeletivoEmEdicao() {

		projeto = new ProcessoSeletivo();
		tipoProjeto = "Seletivo";
	}

	public void ProjetoPessoalEmEdicao() {

		projeto = new Pessoal();
		tipoProjeto = "Pessoal";
	}
	
	public void Salvar() {

		try {
			
			LocalDate datainicio = JavaUtil.convertToLocalDate(dataInicio);
			
			if (atualizar == true) {
				
				if(tipoProjeto.equals("Pessoal")){
					
					pessoalParaAtualizar.setDataInicio(datainicio);
					
					if(dataFinalizado != null){
						LocalDate datafinal = JavaUtil.convertToLocalDate(dataFinalizado);
						pessoalParaAtualizar.setDataFinalizado(datafinal);
					}
										
					pessoalParaAtualizar.setEmProducao(emProducao);
					
					pm.Atualizar(pessoalParaAtualizar);
					
				}else{
					
					seletivoParaAtualizar.setDataInicio(datainicio);
					
					if(dataFinalizado != null){
						LocalDate datafinal = JavaUtil.convertToLocalDate(dataFinalizado);
						seletivoParaAtualizar.setDataFinalizado(datafinal);
					}
										
					seletivoParaAtualizar.setEmpresaResponsavel(empresaResponsavel);
					seletivoParaAtualizar.setAprovado(aprovado);
					
					pm.Atualizar(seletivoParaAtualizar);
					
				}

				messages.info("Projeto Atualizado com sucesso!");

			} else {
				
				if(tipoProjeto.equals("Pessoal")){
					Pessoal pessoal = new Pessoal();
					pessoal.setNome(projeto.getNome());
					pessoal.setDataInicio(datainicio);
					pessoal.setFinalizado(projeto.isFinalizado());
					
					if(dataFinalizado != null){
						LocalDate datafinal = JavaUtil.convertToLocalDate(dataFinalizado);
						pessoal.setDataFinalizado(datafinal);
					}
					
					
					pessoal.setEmProducao(emProducao);
					pessoal.setUsuario(u);
					
					pm.Cadastrar(pessoal);
					
				}else{
					ProcessoSeletivo seletivo = new ProcessoSeletivo();
					seletivo.setNome(projeto.getNome());
					seletivo.setDataInicio(datainicio);
					seletivo.setFinalizado(projeto.isFinalizado());
					
					if(dataFinalizado != null){
						LocalDate datafinal = JavaUtil.convertToLocalDate(dataFinalizado);
						seletivo.setDataFinalizado(datafinal);
					}
					
					seletivo.setEmpresaResponsavel(empresaResponsavel);
					seletivo.setAprovado(aprovado);
					seletivo.setUsuario(u);
					
					pm.Cadastrar(seletivo);
					
				}
				
				messages.info("Projeto Cadastrado com sucesso!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			messages.fatal("Erro ao Salvar dados do Projeto");
		} finally {
			Limpar();
			Inicializar();
		}
	}

	public void ExcluirProjeto() {

		try {

			pm.Remover(projetoParaExcluir.getId());

			messages.info("Projeto Excluído com Sucesso!");

		} catch (Exception ex) {
			ex.printStackTrace();
			messages.error("Erro ao Excluir o Projeto");
		} finally {
			Limpar();
			Inicializar();
		}
	}
	
	public void FinalizarEmEdicao(){
		
		if(projetoSelecionado.isFinalizado() == true){
			messages.error("Atenção! Este Projeto já está Finalizado e por isso NÃO é possível Finalizá-lo Novamente!");
		}else{
			if(projetoSelecionado instanceof Pessoal){
				tipoProjeto = "Pessoal";
			}else{
				tipoProjeto = "Seletivo";
			}
			
			RequestContext.getCurrentInstance().execute("PF('projetofinal-Dialog').show();");
		}
	}
	
	public void Finalizar(){
		
		try{
			
			if(tipoProjeto.equals("Pessoal")){
				Pessoal p = (Pessoal) projetoSelecionado;
				p.setDataFinalizado(LocalDate.now());
				p.setEmProducao(emProducaoFinalizar);
				p.setFinalizado(true);
				
				pm.Atualizar(p);
				
			}else{
				ProcessoSeletivo ps = (ProcessoSeletivo) projetoSelecionado;
				ps.setDataFinalizado(LocalDate.now());
				ps.setAprovado(aprovadoFinalizar);
				ps.setFinalizado(true);
				
				pm.Atualizar(ps);
			}
			
			messages.info("Projeto Finalizado com Sucesso!");
			
		}catch(Exception ex){
			ex.printStackTrace();
			messages.fatal("Erro ao Finalizar o Projeto");
		}finally{
			Limpar();
			Inicializar();
		}
	}
	
	public void MostrarExtrasProjeto(){
		
		if(projetoSelecionado instanceof Pessoal){
			extraEmpresa = "Este Projeto é Pessoal e por isso não possui Informações de Empresa!";
			extraAprovado = "Este Projeto é Pessoal e por isso não possui Informações de Aprovado!";
			Pessoal p = (Pessoal) projetoSelecionado;
			
			if(p.isEmProducao() == true){
				extraEmProducao = "SIM";
			}else{
				extraEmProducao = "NÃO";
			}
			
			cor = "Pessoal";
			
		}else{
			
			extraEmProducao = "Este Projeto é um Processo Seletivo e por isso não possui Informações de Produção!";
			ProcessoSeletivo ps = (ProcessoSeletivo) projetoSelecionado;
			
			extraEmpresa = ps.getEmpresaResponsavel().getNome();
			
			if(ps.isAprovado() == true){
				extraAprovado = "SIM";
			}else{
				extraAprovado = "NÃO";
			}
			
			cor = "Seletivo";
			
		}
		
	}

	public List<Empresa> CompletarNomeEmpresa(String query) {

		List<Empresa> todasEmpresas = em.BuscarTodasEmpresas();
		List<Empresa> filtros = new ArrayList<Empresa>();

		for (int i = 0; i < todasEmpresas.size(); i++) {
			Empresa e = todasEmpresas.get(i);
			if (e.getNome().toLowerCase().startsWith(query.toLowerCase())) {
				filtros.add(e);
			}
		}

		return filtros;
	}

}
