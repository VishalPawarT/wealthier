package com.techames.wealthier.db.model;

import java.util.Date;

public class DailyBalanceSheet {

	private int userId;
	private int accountId;
	private double totalCreditAmount;
	private double totalDebitAmount;
	private double balance;
	private Date transactionDate;
	private String sourceName;
	private int sourceId;
	
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getTotalCreditAmount() {
		return totalCreditAmount;
	}
	public void setTotalCreditAmount(double totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}
	public double getTotalDebitAmount() {
		return totalDebitAmount;
	}
	public void setTotalDebitAmount(double totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	@Override
	public String toString() {
		return "DailyBalanceSheet [userId=" + userId + ", accountId=" + accountId + ", totalCreditAmount="
				+ totalCreditAmount + ", totalDebitAmount=" + totalDebitAmount + ", balance=" + balance
				+ ", transactionDate=" + transactionDate + ", sourceName=" + sourceName + ", sourceId=" + sourceId
				+ "]";
	}
	
	
}
