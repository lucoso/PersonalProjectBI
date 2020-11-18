package br.com.lucaswagner.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.lucaswagner.manager.UsuarioManager;
import br.com.lucaswagner.model.Usuario;
import br.com.lucaswagner.util.FacesMessages;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario user;

	private Usuario usuarioSelecionado;

	private List<Usuario> usuarios;
	
	private boolean atualizar = false;

	private FacesMessages messages = new FacesMessages();

	private UsuarioManager um = new UsuarioManager();
	
	

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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
		usuarios = new ArrayList<Usuario>();
		usuarios = um.BuscarTodosUsuarios();
	}

	public void AtualizarEmEdicao() {
		
		user = usuarioSelecionado;
		atualizar = true;

	}

	public void UsuarioEmEdicao() {

		user = new Usuario();
	}

	public void Salvar() {
		
		try{
			
			if(atualizar == true){
				um.AtualizarUsuario(user);
				messages.info("Usuário Atualizado com Sucesso!");
			}else{
				um.CadastrarUsuario(user);
				messages.info("Usuário Cadastrado com Sucesso!");
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			messages.fatal("Erro ao Salvar os dados do Usuário");
		}finally{
			Inicializar();
		}
	}
	
	public void Remover(){
		
		try{
			um.RemoverUsuario(usuarioSelecionado.getId());
			messages.info("Usuario Excluido com Sucesso");
		}catch(Exception ex){
			ex.printStackTrace();
			messages.fatal("Erro ao Excluir o Usuario");
		}finally{
			Inicializar();
		}
	}

}
