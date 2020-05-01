package com.techames.wealthier.db.dao;
// Generated 2 Sep, 2018 1:04:53 AM by Hibernate Tools 4.3.5.Final


import java.util.ArrayList
;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.techames.wealthier.db.model.CreditDetails;
import com.techames.wealthier.db.model.DailyBalanceSheet;

import lombok.extern.slf4j.Slf4j;

/**
 * Home object for domain model class CreditDetails.
 * @see com.jetsynthesys.CreditDetails
 * @author Hibernate Tools
 */
@Repository
@Transactional
@Slf4j
public class CreditDetailsHome {

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(CreditDetails transientInstance) {
		log.debug("persisting CreditDetails instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(CreditDetails persistentInstance) {
		log.debug("removing CreditDetails instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public CreditDetails merge(CreditDetails detachedInstance) {
		log.debug("merging CreditDetails instance");
		try {
			CreditDetails result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CreditDetails findById(int id) {
		log.debug("getting CreditDetails instance with id: " + id);
		try {
			CreditDetails instance = entityManager.find(CreditDetails.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CreditDetails> getAllCreditTransactionDetails(int accountId) {
		log.debug("getting CreditDetails instance with id: " + accountId);
		try {
			List<CreditDetails> instance = entityManager.createQuery("From CreditDetails cd where cd.crTransactionAccountId = :accountId" ,CreditDetails.class)
					.setParameter("accountId", accountId)
					.getResultList();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public double getTotalCreditAmountForUser(int accountId) {
		log.debug("getting CreditDetails instance with id: " + accountId);
		try {
			double instance = (double) entityManager.createQuery("Select sum(cd.crTransactionAmount) From CreditDetails cd where cd.crTransactionAccountId = :accountId")
					.setParameter("accountId", accountId)
					.getSingleResult();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Object> getAllCreditTransactionDetailsByDay(
			int accountId) {
		log.debug("getting CreditDetails instance with id: " + accountId);
		try {
			
			String sql = "SELECT cr_transaction_date As crTransactionDate, sum(cr_transaction_amount) As crTransactionAmount FROM credit_details where cr_transaction_account_id = "+ accountId +" group by cr_transaction_date";
			
			List<Object> instance = entityManager.createNativeQuery(sql).getResultList();
			log.info("get successful  " + instance);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		
	}

	public List<CreditDetails> getAllCreditTransactionDetailsByPassedStatus(int accountId, boolean isPassed) {
		log.debug("getting CreditDetails instance with id: " + accountId);
		try {
			List<CreditDetails> instance = entityManager.createQuery("From CreditDetails cd where cd.crTransactionAccountId = :accountId and cd.crTransactionPassed =:crTransactionPassed" ,CreditDetails.class)
					.setParameter("accountId", accountId)
					.setParameter("crTransactionPassed", isPassed)
					.getResultList();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public int changeCreditTransactionStatus(int accountId, int id, boolean status) {
		
		
		int i = 0;
		if(status) {
			i  = 1;
		}
		String sql = "UPDATE `credit_details` SET `cr_transaction_passed`= " + i + " WHERE `cr_transaction_id`= "+ id;
		/*return entityManager.createQuery("update CreditDetails  set crTransactionPassed = :newStatus"
				+ "where crTransactionAccountId = :accountId and crTransactionId = transactionId")
				.setParameter("newStatus", status)
				.setParameter("crTransactionAccountId", accountId)
				.setParameter("crTransactionId", id)
				.executeUpdate();*/
		return entityManager.createNativeQuery(sql).executeUpdate();
	}

	public List<CreditDetails> getAllCreditTransactionDetailsByUserId(int userId) {
		log.debug("getting CreditDetails instance with id: " + userId);
		try {
			List<CreditDetails> instance = entityManager.createQuery("From CreditDetails cd where cd.crTransactionUserId = :userId" ,CreditDetails.class)
					.setParameter("userId", userId)
					.getResultList();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DailyBalanceSheet> getDailyBalanceSheet(Integer id) {
		/*String sql = "SELECT SUM(cr_transaction_amount) As totalCreditAmount, cr_transaction_date As transactionDate FROM credit_details cr where cr_transaction_user_id = "+ id +" group by cr.cr_transaction_date";
		List<DailyBalanceSheet> respList = new ArrayList<>();
		List<Object> credResp = entityManager.createNativeQuery(sql).getResultList();
		
		String debSql =  "SELECT SUM(deb_transaction_amount) As totalDebitAmount, deb_transaction_date As transactionDate FROM debit_details deb where deb_transaction_user_id = "+ id +" group by deb.deb_transaction_date";
		List<Object> debResp = entityManager.createNativeQuery(debSql).getResultList();*/
		
		String sql = "select date1 As transactionDate, sum(case when type='debit_spend' then deb_amount else 0 end) as totalDebitAmount,\n" + 
				"sum(case when type='credit_spend' then deb_amount else 0 end) as totalCreditAmount\n" + 
				"from\n" + 
				"(\n" + 
				"select deb_transaction_date as date1 ,deb_transaction_amount as deb_amount,'debit_spend' as type from debit_details where deb_transaction_user_id =" +id +" \n" + 
				"union all\n" + 
				"select cr_transaction_date as date1,cr_transaction_amount as credit_amount,'credit_spend' as type from credit_details where cr_transaction_user_id =" +id +  " \n"+
				") as a \n" + 
				"group by date1;";
		List<DailyBalanceSheet> respList = new ArrayList<>();
		List<Object> credDebResp = entityManager.createNativeQuery(sql).getResultList();
		
		for(Object obj : credDebResp) {
			Object[] obj1 = (Object[]) obj;
			DailyBalanceSheet dbsheet = new DailyBalanceSheet();
			dbsheet.setTotalCreditAmount((double) obj1[2]);
			dbsheet.setTransactionDate((Date) obj1[0]);
			dbsheet.setTotalDebitAmount((double) obj1[1]);
			dbsheet.setBalance(dbsheet.getTotalCreditAmount() - dbsheet.getTotalDebitAmount());
			dbsheet.setUserId(id);
			respList.add(dbsheet);
		}
		log.info("information got"  + respList);
		return respList;	
	}

	public List<DailyBalanceSheet> getDailyBalanceSheetBySourceId(Integer id, int sourceId, String sourceName) {
		String sql = "select date1 As transactionDate, sum(case when type='debit_spend' then deb_amount else 0 end) as totalDebitAmount,\n" + 
				"sum(case when type='credit_spend' then deb_amount else 0 end) as totalCreditAmount\n" + 
				"from\n" + 
				"(\n" + 
				"select deb_transaction_date as date1 ,deb_transaction_amount as deb_amount,'debit_spend' as type from debit_details where deb_transaction_user_id =" +id +" and deb_transaction_source_id = " + sourceId+" \n" + 
				"union all\n" + 
				"select cr_transaction_date as date1,cr_transaction_amount as credit_amount,'credit_spend' as type from credit_details where cr_transaction_user_id =" +id +" and cr_transacion_source_id = " + sourceId +" \n"+
				") as a \n" + 
				"group by date1;";
		List<DailyBalanceSheet> respList = new ArrayList<>();
		List<Object> credDebResp = entityManager.createNativeQuery(sql).getResultList();
		
		for(Object obj : credDebResp) {
			Object[] obj1 = (Object[]) obj;
			DailyBalanceSheet dbsheet = new DailyBalanceSheet();
			dbsheet.setTotalCreditAmount((double) obj1[2]);
			dbsheet.setTransactionDate((Date) obj1[0]);
			dbsheet.setTotalDebitAmount((double) obj1[1]);
			dbsheet.setSourceId(sourceId);
			dbsheet.setSourceName(sourceName);
			dbsheet.setBalance(dbsheet.getTotalCreditAmount() - dbsheet.getTotalDebitAmount());
			dbsheet.setUserId(id);
			respList.add(dbsheet);
		}
		log.info("information got for source id "  + respList);
		return respList;
	}

	/*public void insertCreditDetails(CreditDetails creditDetails) {
		log.debug("getting CreditDetails instance with id: " + creditDetails);
		try {
			CreditDetails instance = entityManager.merge(entity)
			log.debug("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}*/
}
