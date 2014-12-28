package com.codersarecreators.factory;

import com.codersarecreators.reminder.SmsObject;

public abstract class SmsFactory implements AbstractFactory {

	@Override
	public abstract SmsObject CreateSmsObject(String id, String text, String date, String time, String phoneNumber);
	
}
