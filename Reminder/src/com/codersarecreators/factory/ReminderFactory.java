package com.codersarecreators.factory;

import com.codersarecreators.reminder.ReminderObject;

public abstract class ReminderFactory implements AbstractFactory {

	/*
	 * Take just the one method that is associated with the reminder object from the abstract factory interface.
	 */
	@Override
	public abstract ReminderObject CreateReminderObject(String id,String note,String date,String time);
	
}
