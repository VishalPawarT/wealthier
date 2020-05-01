package com.techames.wealthier.db.dao;
// Generated 2 Sep, 2018 1:04:53 AM by Hibernate Tools 4.3.5.Final


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.techames.wealthier.db.model.UserDefinedSources;

/**
 * Home object for domain model class UserDefinedSources.
 * @see com.jetsynthesys.UserDefinedSources
 * @author Hibernate Tools
 */

@Repository
@Transactional
public class UserDefinedSourcesHome {

	private static final Log log = LogFactory.getLog(UserDefinedSourcesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UserDefinedSources transientInstance) {
		log.debug("persisting UserDefinedSources instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UserDefinedSources persistentInstance) {
		log.debug("removing UserDefinedSources instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UserDefinedSources merge(UserDefinedSources detachedInstance) {
		log.debug("merging UserDefinedSources instance");
		try {
			UserDefinedSources result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserDefinedSources findById(int id) {
		log.debug("getting UserDefinedSources instance with id: " + id);
		try {
			UserDefinedSources instance = entityManager.find(UserDefinedSources.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserDefinedSources> getAllSourcesByAccountId(int accountId) {
		log.debug("getting UserDefinedSources instance with id: " + accountId);
		try {
			List<UserDefinedSources> instance = entityManager.createQuery("From UserDefinedSources cd where cd.accountId = :accountId" , UserDefinedSources.class)
					.setParameter("accountId", accountId)
					.getResultList();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
	  }
  }

}