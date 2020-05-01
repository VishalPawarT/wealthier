package com.techames.wealthier.db.dao;
// Generated 2 Sep, 2018 1:04:53 AM by Hibernate Tools 4.3.5.Final


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.techames.wealthier.db.model.ClientInformation;

/**
 * Home object for domain model class ClientInformation.
 * @see com.jetsynthesys.ClientInformation
 * @author Hibernate Tools
 */
@Repository
public class ClientInformationHome {

	private static final Log log = LogFactory.getLog(ClientInformationHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ClientInformation transientInstance) {
		log.debug("persisting ClientInformation instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ClientInformation persistentInstance) {
		log.debug("removing ClientInformation instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ClientInformation merge(ClientInformation detachedInstance) {
		log.debug("merging ClientInformation instance");
		try {
			ClientInformation result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ClientInformation findById(int id) {
		log.debug("getting ClientInformation instance with id: " + id);
		try {
			ClientInformation instance = entityManager.find(ClientInformation.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
