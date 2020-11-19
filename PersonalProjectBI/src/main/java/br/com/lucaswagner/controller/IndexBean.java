package br.com.lucaswagner.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.lucaswagner.manager.UsuarioManager;
import br.com.lucaswagner.model.Papel;
import br.com.lucaswagner.model.Usuario;
import br.com.lucaswagner.util.EmailUtil;
import br.com.lucaswagner.util.FacesMessages;

@ManagedBean
@ViewScoped
public class IndexBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer progress;
	
	private String username;
	
	private FacesMessages messages = new FacesMessages();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public void BuscaInicial(){
		try{
			UsuarioManager um = new UsuarioManager();
			int i = um.BuscarUsuarioParaInstalacao();
			if(i == 0){
				RequestContext.getCurrentInstance().execute("PF('progresso-Dialog').show();");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Erro ao fazer busca inicial");
		}
	}
	
	public void AoCompletar(){
		RequestContext.getCurrentInstance().execute("PF('progresso-Dialog').hide();");
		messages.info("Instalação Efetuada com Sucesso!");
	}
	
	 public Integer getProgress() {
	        if(progress == null) {
	            progress = 0;
	        }
	        else {
	            progress = progress + (int)(Math.random() * 35);
	             
	            if(progress > 100)
	            	CadastrarUsuarioPadrao();
	                progress = 100;
	        }
	         
	        return progress;
	    }
	
	private void CadastrarUsuarioPadrao(){
		try{
			Usuario u = new Usuario();
			UsuarioManager um = new UsuarioManager();
			
			u.setAtivo(true);
			u.setLogin("Administrador Geral");
			u.setNome("Lucas");
			u.setPapel(Papel.Administrador);
			u.setSenha("B1928374655");
			
			um.CadastrarUsuario(u);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Erro ao Criar Usuario Padrao");
		}
	}
	
	public void Limpar(){
		username = null;
	}
	
	public void Recuperar(){
		try{
			UsuarioManager um = new UsuarioManager();
			List<Usuario> usuarios = um.BuscarUsuarioPorLoginParaRecuperarSenha(username);
			if(usuarios.isEmpty()){
				Limpar();
				messages.error("Atenção! Este e-mail NÃO esta cadastrado no sistema. Digite o e-mail novamente....");
				RequestContext.getCurrentInstance().update("dialogs:msg-senha");
				RequestContext.getCurrentInstance().update("dialogs:painel-recuperar");
			}else{
				String senha = null;
				String email = null;
				for(Usuario u : usuarios){
					senha = u.getSenha();
					email = u.getLogin();
				}
				
				String titulo = "Recuperação de Senha";
				String mensagem = "Sua solicitação de recuperação de senha foi efetuada com sucesso! "
						+ "\n" + "Sua Senha é: " + senha + "\n" + "Acesse o Sistema de Projetos e faça seu login."
								+ "\n\n\n" + "Este é um e-mail automático e NÃO é necessário respondê-lo."; 
						
				EmailUtil.enviaEmail(titulo, email, mensagem);
				messages.info("Senha Recuperada com Sucesso! Um e-mail foi enviado para " + email + " com a senha recuperada!");
				RequestContext.getCurrentInstance().execute("PF('recuperarsenha-Dialog').hide();");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			messages.fatal("Erro ao recuperar a senha");
		}
	}

}
