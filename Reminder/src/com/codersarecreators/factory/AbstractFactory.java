package com.codersarecreators.factory;

import com.codersarecreators.reminder.ReminderObject;
import com.codersarecreators.reminder.SmsObject;

public interface AbstractFactory {
	ReminderObject CreateReminderObject(String id,String note,String date,String time);
	SmsObject CreateSmsObject(String id, String text, String date, String time, String phoneNumber);
}
