package com.techames.wealthier.utils;

import java.util.Random;

import org.springframework.stereotype.Component;



@Component
public class CommonUtility {

	public int getUniqueNumber() {
		return new Random().nextInt(999999999) + 1;
	}
}
