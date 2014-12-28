package com.codersarecreators.factory;

import com.codersarecreators.reminder.ReminderObject;
import com.codersarecreators.reminder.SmsObject;

public class ReminderConcreteFactory extends ReminderFactory {

	
	
	@Override
	public SmsObject CreateSmsObject(String id, String text, String date,
			String time, String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReminderObject CreateReminderObject(
			String id, String note, String date, String time) {
		// TODO Auto-generated method stub
		ReminderObject reminderObj = new ReminderObject(id,note,date,time);
		if(null != reminderObj)
			return reminderObj;
		return null;
	}
	
	

}
