package br.com.lucaswagner.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

	// private List<Projeto> projetos;

	private LazyDataModel<Projeto> projetos;

	private String tipoProjeto;
	
	private boolean aprovado;
	
	private boolean emProducao;
	
	private Empresa empresaResponsavel;

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

	public Empresa getEmpresaResponsavel() {
		return empresaResponsavel;
	}

	public void setEmpresaResponsavel(Empresa empresaResponsavel) {
		this.empresaResponsavel = empresaResponsavel;
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

	public void AtualizarProjetoEmEdicao() {

		projeto = projetoSelecionado;
		
		if(projetoSelecionado instanceof ProcessoSeletivo){
			ProcessoSeletivo ps = (ProcessoSeletivo) projeto;
			aprovado = ps.isAprovado();
			empresaResponsavel = ps.getEmpresaResponsavel();
			tipoProjeto = "Seletivo";
		}
		
		if(projetoSelecionado instanceof Pessoal){
			Pessoal pessoal = (Pessoal) projeto;
			emProducao = pessoal.isEmProducao();
			tipoProjeto = "Pessoal";
		}
		
		atualizar = true;
	}

	/*public void ProjetoSeletivoEmEdicao() {

		projeto = new ProcessoSeletivo();
	}

	public void ProjetoPessoalEmEdicao() {

		projeto = new Pessoal();
	}*/

	public void Salvar() {

		try {

			if (atualizar == true) {
				
				if(tipoProjeto.equals("Pessoal")){
					Pessoal pessoal = new Pessoal();
					pessoal.setNome(projeto.getNome());
					pessoal.setDataInicio(projeto.getDataInicio());
					pessoal.setFinalizado(projeto.isFinalizado());
					pessoal.setDataFinalizado(projeto.getDataFinalizado());
					pessoal.setEmProducao(emProducao);
					pessoal.setUsuario(u);
					
					pm.Atualizar(pessoal);
					
				}else{
					ProcessoSeletivo seletivo = new ProcessoSeletivo();
					seletivo.setNome(projeto.getNome());
					seletivo.setDataInicio(projeto.getDataInicio());
					seletivo.setFinalizado(projeto.isFinalizado());
					seletivo.setDataFinalizado(projeto.getDataFinalizado());
					seletivo.setEmpresaResponsavel(empresaResponsavel);
					seletivo.setAprovado(aprovado);
					seletivo.setUsuario(u);
					
					pm.Atualizar(seletivo);
					
				}

				//pm.Atualizar(projeto);

				messages.info("Projeto Atualizado com sucesso!");

			} else {
				
				if(tipoProjeto.equals("Pessoal")){
					Pessoal pessoal = new Pessoal();
					pessoal.setNome(projeto.getNome());
					pessoal.setDataInicio(projeto.getDataInicio());
					pessoal.setFinalizado(projeto.isFinalizado());
					pessoal.setDataFinalizado(projeto.getDataFinalizado());
					pessoal.setEmProducao(emProducao);
					pessoal.setUsuario(u);
					
					pm.Cadastrar(pessoal);
					
				}else{
					ProcessoSeletivo seletivo = new ProcessoSeletivo();
					seletivo.setNome(projeto.getNome());
					seletivo.setDataInicio(projeto.getDataInicio());
					seletivo.setFinalizado(projeto.isFinalizado());
					seletivo.setDataFinalizado(projeto.getDataFinalizado());
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
			Inicializar();
		}

	}

	public void ExcluirProjeto() {

		try {

			pm.Remover(projetoParaExcluir.getId());

			messages.info("Porjeto Excluído com Sucesso!");

		} catch (Exception ex) {
			ex.printStackTrace();
			messages.error("Erro ao Excluir o Projeto");
		} finally {
			Inicializar();
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
