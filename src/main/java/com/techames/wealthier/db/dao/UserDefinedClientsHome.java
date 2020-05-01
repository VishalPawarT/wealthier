package com.techames.wealthier.db.dao;
// Generated 2 Sep, 2018 1:04:53 AM by Hibernate Tools 4.3.5.Final


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.techames.wealthier.db.model.UserDefinedClients;

/**
 * Home object for domain model class UserDefinedClients.
 * @see com.jetsynthesys.UserDefinedClients
 * @author Hibernate Tools
 */

@Repository
@Transactional
public class UserDefinedClientsHome {

	private static final Log log = LogFactory.getLog(UserDefinedClientsHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UserDefinedClients transientInstance) {
		log.debug("persisting UserDefinedClients instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UserDefinedClients persistentInstance) {
		log.debug("removing UserDefinedClients instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UserDefinedClients merge(UserDefinedClients detachedInstance) {
		log.debug("merging UserDefinedClients instance");
		try {
			UserDefinedClients result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserDefinedClients findById(int id) {
		log.debug("getting UserDefinedClients instance with id: " + id);
		try {
			UserDefinedClients instance = entityManager.find(UserDefinedClients.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserDefinedClients> getAllClientsByAccountId(int accountId) {
		log.debug("getting UserDefinedSources instance with id: " + accountId);
		try {
			List<UserDefinedClients> instance = entityManager.createQuery("From UserDefinedClients cd where cd.udClientAccountId = :accountId" , UserDefinedClients.class)
					.setParameter("accountId", accountId)
					.getResultList();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
	  }
	}

	public List<UserDefinedClients> getAllClientsByAccountIdAndType(int accountId, int i, int j) {
		log.debug("getting UserDefinedSources instance with id: " + accountId);
		try {
			List<UserDefinedClients> instance = entityManager.createQuery("From UserDefinedClients cd where cd.udClientAccountId = :accountId and (cd.udClientType = :i OR cd.udClientType= :j)"   , UserDefinedClients.class)
					.setParameter("accountId", accountId)
					.setParameter("i", i)
					.setParameter("j", j)
					.getResultList();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
	  }
	}


}
