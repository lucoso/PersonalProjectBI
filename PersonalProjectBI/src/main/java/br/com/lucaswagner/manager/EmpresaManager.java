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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.lucaswagner.model.Empresa;
import br.com.lucaswagner.util.JpaUtil;

public class EmpresaManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void Cadastrar(Empresa e) {

		EntityManager em = JpaUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(e);
			em.getTransaction().commit();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}
	}
	
	public int ContarTodasEmpresaLazy(){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		Number resultado = 0;
		
		try{
			Session s = em.unwrap(Session.class);
			Criteria criteria = s.createCriteria(Empresa.class);
			resultado = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
			
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado.intValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Empresa> BuscarTodasEmpresasLazy(int first, int maxpage, Map filtros){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Empresa> resultado = new ArrayList<Empresa>();
		
		try{
			Session s = em.unwrap(Session.class);
			Criteria criteria = s.createCriteria(Empresa.class);
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

	
	public List<Empresa> BuscarTodasEmpresas() {

		EntityManager em = JpaUtil.getEntityManager();

		List<Empresa> resultado = new ArrayList<Empresa>();

		try {
			String consulta = "select e from Empresa e";
			TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
			resultado = query.getResultList();

		} catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {

			em.close();
		}

		return resultado;
	}

	public List<Empresa> BuscarEmpresaPorID(Long id){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Empresa> resultado = new ArrayList<Empresa>();
		
		try{
			String consulta = "select e from Empresa e where e.id = :id ";
			TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
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
	
	public Empresa BuscarEmpresaPorIDParaConverter(Long id){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Empresa> resultado = new ArrayList<Empresa>();
		Empresa e = null;
		
		try{
			String consulta = "select e from Empresa e where e.id = :id ";
			TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
			query.setParameter("id", id);
			resultado = query.getResultList();
			
			if(!resultado.isEmpty()){
				for(Empresa empresa : resultado){
					e = empresa;
				}
			}
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return e;
		
	}
	
	public List<Empresa> BuscarEmpresaPorSite(String site){
		
		EntityManager em = JpaUtil.getEntityManager();
		
		List<Empresa> resultado = new ArrayList<Empresa>();
		
		try{
			String consulta = "select e from Empresa e where e.site = :site";
			TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
			query.setParameter("site", site);
			resultado = query.getResultList();
		
		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}
		
		return resultado;
		
	}

	public void Atualizar(Empresa e) {

		EntityManager em = JpaUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(e);
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
		
		Empresa e = em.find(Empresa.class, id);
		
		try{
			em.getTransaction().begin();
			em.remove(e);
			em.getTransaction().commit();
			

		}catch (Exception ex) {

			ex.printStackTrace();
			em.getTransaction().rollback();
		
		}finally{
			
			em.close();
		}		
	}

}
