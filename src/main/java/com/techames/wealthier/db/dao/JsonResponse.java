package com.techames.wealthier.db.dao;

import java.util.Date;

public class JsonResponse {
	
	private Date date;
	private double amount;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "JsonResponse [date=" + date + ", amount=" + amount + "]";
	}
	
	
}
