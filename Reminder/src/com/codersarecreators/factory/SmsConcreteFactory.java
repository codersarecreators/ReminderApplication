package com.codersarecreators.factory;

import com.codersarecreators.reminder.ReminderObject;
import com.codersarecreators.reminder.SmsObject;

public class SmsConcreteFactory extends SmsFactory {

	@Override
	public ReminderObject CreateReminderObject(String id, String note,
			String date, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsObject CreateSmsObject(String id, String text, String date,
			String time, String phoneNumber) {
		// TODO Auto-generated method stub
		SmsObject smsObj = new SmsObject(id,text,date,time,phoneNumber);
		if(null != smsObj)
			return smsObj;
		return null;
	}

}
