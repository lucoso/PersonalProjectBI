package br.com.lucaswagner.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.lucaswagner.model.Usuario;
import br.com.lucaswagner.util.JpaUtil;

public class UsuarioManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void CadastrarUsuario(Usuario u) {

		EntityManager em = JpaUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

	}

	public List<Usuario> BuscarTodosUsuarios() {

		EntityManager em = JpaUtil.getEntityManager();

		List<Usuario> resultado = new ArrayList<Usuario>();

		try {
			String consulta = "select u from Usuario u";
			TypedQuery<Usuario> query = em.createQuery(consulta, Usuario.class);
			resultado = query.getResultList();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

		return resultado;
	}
	
	public int BuscarUsuarioParaInstalacao(){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		Number resultado = 0;
		
		try{
			String consulta = "select COUNT(u) from Usuario u";
			TypedQuery<Number> query = em.createQuery(consulta, Number.class);
			resultado = query.getSingleResult();
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado.intValue();
		
	}

	public List<Usuario> BuscarUsuarioPorID(Long id){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Usuario> resultado = new ArrayList<Usuario>();
		
		try{
			String consulta = "select u from Usuario u where u.id = :id";
			TypedQuery<Usuario> query = em.createQuery(consulta, Usuario.class);
			query.setParameter("id", id);
			resultado = query.getResultList();
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado;
		
	}
	
	public List<Usuario> BuscarUsuarioPorLoginParaRecuperarSenha(String login){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Usuario> resultado = new ArrayList<Usuario>();
		
		try{
			String consulta = "select u from Usuario u where u.login = :login";
			TypedQuery<Usuario> query = em.createQuery(consulta, Usuario.class);
			query.setParameter("login", login);
			resultado = query.getResultList();
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado;
	}
	
	public Usuario BuscarUmUsuarioPorLogin(String login){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Usuario> resultado = new ArrayList<Usuario>();
		Usuario user = null;
		
		try{
			String consulta = "select u from Usuario u where u.login = :login";
			TypedQuery<Usuario> query = em.createQuery(consulta, Usuario.class);
			query.setParameter("login", login);
			resultado = query.getResultList();
			
			if(!resultado.isEmpty()){
				for(Usuario u : resultado){
					user = u;
				}
			}
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return user;
	}
	
	public void AtualizarUsuario(Usuario u) {

		EntityManager em = JpaUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(u);
			em.getTransaction().commit();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

	}
	
	public void RemoverUsuario(long id){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		Usuario u = em.find(Usuario.class, id);
		
		try{
			em.getTransaction().begin();
			em.remove(u);
			em.getTransaction().commit();
			

		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}	
	}

}
