package br.com.lucaswagner.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.lucaswagner.manager.UsuarioManager;
import br.com.lucaswagner.model.Usuario;

@ManagedBean
@SessionScoped
public class AtivaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String usuarionome;

	public String getUsuarionome() {
		return usuarionome;
	}

	public void setUsuarionome(String usuarionome) {
		this.usuarionome = usuarionome;
	}

	public void Buscar(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String str = external.getRemoteUser();
		UsuarioManager um = new UsuarioManager();
		Usuario u = um.BuscarUmUsuarioPorLogin(str);
		String username = u.getNome();
		String singlename[] = username.split(" ");
		usuarionome = singlename[0];	
	}

}
