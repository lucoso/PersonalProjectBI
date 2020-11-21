package br.com.lucaswagner.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.lucaswagner.manager.EmpresaManager;
import br.com.lucaswagner.model.Empresa;
import br.com.lucaswagner.util.FacesMessages;

@ManagedBean
@ViewScoped
public class EmpresaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Empresa empresa;

	private Empresa empresaSelecionada;

	private LazyDataModel<Empresa> empresas;

	private boolean atualizar = false;

	private EmpresaManager em = new EmpresaManager();

	private FacesMessages messages = new FacesMessages();
	
	

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public LazyDataModel<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(LazyDataModel<Empresa> empresas) {
		this.empresas = empresas;
	}

	public boolean isAtualizar() {
		return atualizar;
	}

	public void setAtualizar(boolean atualizar) {
		this.atualizar = atualizar;
	}

	/**
	 * Métodos
	 * 
	 * @author Lucas Manhães
	 */

	public void Inicializar() {

		empresas = new LazyDataModel<Empresa>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			List<Empresa> empresasList;

			public List<Empresa> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				empresasList = em.BuscarTodasEmpresasLazy(first, pageSize, filters);

				setRowCount(em.ContarTodasEmpresaLazy());

				return empresasList;

			}

			@Override
			public Object getRowKey(Empresa empresa) {
				return empresa.getId();
			}

			@Override
			public Empresa getRowData(String empresaid) {

				Long id = Long.valueOf(empresaid);

				for (Empresa e : empresasList) {
					if (id.equals(e.getId())) {
						return e;
					}
				}

				return null;
			}
		};
	}

	public void CadastrarEmEdicao() {

		empresa = new Empresa();
	}

	public void AtualizarEmEdicao() {
		
		empresa = empresaSelecionada;
		atualizar = true;

	}
	
	public void Salvar(){
		
		try{
			
			if(atualizar == true){
				em.Atualizar(empresa);
				messages.info("Empresa Atualizada com Sucesso!");
			}else{
				em.Cadastrar(empresa);
				messages.info("Empresa Cadastrada com Sucesso!");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			messages.fatal("Erro ao Salvar os dados da Empresa");
		}finally{
			empresaSelecionada = null;
			Inicializar();
		}
	}
	
	

}
