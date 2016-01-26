package jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JpaManager {
	private static final String PERSISTENCE_UNIT_NAME = "AgileDoctorDB";
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;
	public static EntityManagerFactory getEMF() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			System.out.println(emf.isOpen());
		}
		return emf;
	}

	public static EntityManager getEM() {
		if (null == em){
			em = getEMF().createEntityManager();
		}
		return em;
	}

	public static <T extends Serializable> void persist(T entity) {
		EntityManager em = getEM();
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} finally {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> List<T> findWithNamedQuery(String strQuery, Map<String, Object> params){
		EntityManager em = getEM();
		try {
			 Query query = em.createNamedQuery(strQuery);
			 if (null != params && !params.isEmpty()){
				 for(String key: params.keySet()){
					 query.setParameter(key, params.get(key));
				 }
			 }
			 return (List<T>)query.getResultList();
		} finally {
		}
	}
	
	public static <T extends Serializable> T findById(Class<T> type, long id){
		EntityManager em = getEM();
		try {
			return em.find(type, id);
		} finally {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> List<T> findAll(String strTableName){
		EntityManager em = getEM();
		try {
			 Query query = em.createQuery("SELECT e FROM " + strTableName + " e" + "");
			 return (List<T>)query.getResultList();
		} finally {
			
		}
	}
	
	public static <T extends Serializable> void update(T entity) {
		EntityManager em = getEM();
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} finally {
			
		}
	}
	
	public static <T extends Serializable> void delete(T entity) {
		EntityManager em = getEM();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} finally {
			
		}
	}
	
}
