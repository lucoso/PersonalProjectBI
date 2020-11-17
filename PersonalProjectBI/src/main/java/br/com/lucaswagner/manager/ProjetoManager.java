package br.com.lucaswagner.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.lucaswagner.model.Pessoal;
import br.com.lucaswagner.model.ProcessoSeletivo;
import br.com.lucaswagner.model.Projeto;
import br.com.lucaswagner.util.JpaUtil;

public class ProjetoManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void CadastrarPessoa(Projeto p) {

		EntityManager em = JpaUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Projeto> BuscarTodosProjetosLazy(int first, int maxpage, Map filtros){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Projeto> resultado = new ArrayList<Projeto>();
		
		try{
			Session s = em.unwrap(Session.class);
			Criteria criteria = s.createCriteria(Projeto.class);
			if(!filtros.isEmpty()){
				Iterator<Entry> itr = filtros.entrySet().iterator();
				while(itr.hasNext()){
					Entry entry = itr.next();
					criteria.add(Restrictions.ilike(entry.getKey().toString(), entry.getValue().toString(), MatchMode.ANYWHERE));
				}
			}
			
			criteria.setFirstResult(first);
			criteria.setMaxResults(maxpage);
			resultado = criteria.list();
			
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado;
	}
	
	public int ContarTodosProjetos() {

		EntityManager em = JpaUtil.getEntityManager();

		Number resultado = 0;

		try {
			String consulta = "select count(p) from Projeto p";
			TypedQuery<Number> query = em.createQuery(consulta, Number.class);
			resultado = query.getSingleResult();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

		return resultado.intValue();
	}

	public List<Projeto> BuscarTodosProjetos() {

		EntityManager em = JpaUtil.getEntityManager();

		List<Projeto> resultado = new ArrayList<Projeto>();

		try {
			String consulta = "select p from Projeto p";
			TypedQuery<Projeto> query = em.createQuery(consulta, Projeto.class);
			resultado = query.getResultList();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

		return resultado;
	}

	public List<Pessoal> BuscarTodosProjetosPessoais() {

		EntityManager em = JpaUtil.getEntityManager();

		List<Pessoal> resultado = new ArrayList<Pessoal>();

		try {
			String consulta = "select p from Pessoal p";
			TypedQuery<Pessoal> query = em.createQuery(consulta, Pessoal.class);
			resultado = query.getResultList();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

		return resultado;
	}
	
	public List<ProcessoSeletivo> BuscarTodosProjetosSeletivos() {

		EntityManager em = JpaUtil.getEntityManager();

		List<ProcessoSeletivo> resultado = new ArrayList<ProcessoSeletivo>();

		try {
			String consulta = "select p from ProcessoSeletivo p";
			TypedQuery<ProcessoSeletivo> query = em.createQuery(consulta, ProcessoSeletivo.class);
			resultado = query.getResultList();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

		return resultado;
	}
	
	public List<Projeto> BuscarProjetoPorID(Long id){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Projeto> resultado = new ArrayList<Projeto>();
		
		try{
			String consulta = "select p from Projeto p where p.id = :id";
			TypedQuery<Projeto> query = em.createQuery(consulta, Projeto.class);
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
	
	public List<Projeto> BuscarProjetoPorNome(String nome){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Projeto> resultado = new ArrayList<Projeto>();
		
		try{
			String consulta = "select p from Projeto p where p.nome = :nome";
			TypedQuery<Projeto> query = em.createQuery(consulta, Projeto.class);
			query.setParameter("nome", nome);
			resultado = query.getResultList();
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado;
		
	}
	
	public void Atualizar(Projeto p) {

		EntityManager em = JpaUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(p);
			em.getTransaction().commit();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

	}
	
	public void Remover(Long id){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		Projeto p = em.find(Projeto.class, id);
		
		try{
			em.getTransaction().begin();
			em.remove(p);
			em.getTransaction().commit();
			

		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}	
	}

}
