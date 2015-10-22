package control;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JpaManager {
	private static final String PERSISTENCE_UNIT_NAME = "SGame";
	private static EntityManagerFactory emf = null;

	public static EntityManagerFactory getEMF() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return emf;
	}

	public static EntityManager createEM() {
		return getEMF().createEntityManager();
	}

	public static <T extends Serializable> void create(T entity) {
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	public static <T extends Serializable> T findById(Class<T> type, long id){
		EntityManager em = createEM();
		try {
			return em.find(type, id);
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> List<T> findAll(String strTableName){
		EntityManager em = createEM();
		try {
			 Query query = em.createQuery("SELECT e FROM " + strTableName + " e" + " ORDER BY e.id ASC");
			 return (List<T>)query.getResultList();
		} finally {
			em.close();
		}
	}
	
	public static <T extends Serializable> void update(T entity) {
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	public static <T extends Serializable> void delete(T entity) {
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
}
