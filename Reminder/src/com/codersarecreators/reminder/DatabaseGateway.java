package com.codersarecreators.reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseGateway extends SQLiteOpenHelper {

	private static Context contextObj = MainActivity.GetContext();
	private static String DB_NAME = "ReminderAppDb";
	private static int DB_VERSION = 9;
	private static String TABLE_REMINDER = "TABLE_REMINDER";
	private static String TABLE_MESSAGESERVICE = "TABLE_SMS";
	private static String TABLE_REMINDER_MESSAGE_ASSOC = "RemSmsAssoc";
	// the singleton object
	private static DatabaseGateway dbGateWayObj = null;
	private static SQLiteDatabase dbObj = null;

	// constructor
	private DatabaseGateway() {
		super(contextObj, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {
		super.onConfigure(db);
		// set the foreign key constraint to true
		db.setForeignKeyConstraintsEnabled(true);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		/*
		 * Refer to the tables' design and create the tables accordingly.
		 */
		String CreateTableForReminder = "CREATE TABLE " + TABLE_REMINDER + "("
				+ "ReminderId TEXT NOT NULL," + "Date TEXT NOT NULL,"
				+ "Time TEXT NOT NULL," + "Notes TEXT NOT NULL,"
				+ "PRIMARY KEY(ReminderId)" + ")";
		String CreateTableForSms = "CREATE TABLE " + TABLE_MESSAGESERVICE + "("
				+ "SmsId TEXT NOT NULL," + "Date TEXT NOT NULL,"
				+ "Time TEXT NOT NULL," + "MessageText TEXT NOT NULL,"
				+ "PhoneNumber TEXT NOT NULL," + "PRIMARY KEY(SmsId)" + ")";
		String CreateTableForAssoc = "CREATE TABLE "
				+ TABLE_REMINDER_MESSAGE_ASSOC + "("
				+ "ReminderId TEXT NOT NULL," + "SmsId TEXT NOT NULL,"
				+ "FOREIGN KEY(ReminderId) REFERENCES " + TABLE_REMINDER
				+ "(ReminderId)," + "FOREIGN KEY(SmsId) REFERENCES "
				+ TABLE_MESSAGESERVICE + "(SmsId)" + ")";
		try {
			db.execSQL(CreateTableForReminder);
			db.execSQL(CreateTableForSms);
			db.execSQL(CreateTableForAssoc);
			MyToast.RaiseToast("All tables created successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			MyToast.RaiseToast("In OnCreate()");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_MESSAGE_ASSOC);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGESERVICE);
			MyToast.RaiseToast("In OnUpgrade()");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onCreate(db);
	}

	public static DatabaseGateway GetDbGateWay() {
		if (null == dbGateWayObj) {
			dbGateWayObj = new DatabaseGateway();
		}
		if (null == dbObj) {
			dbObj = dbGateWayObj.getWritableDatabase();
		}
		return dbGateWayObj;
	}

	private void onInsert() {
		/*
		 * This method will basically take parameter of the type Reminder.
		 * Surround this method with try catch blocks and raise appropriate
		 * toast if the reminder is added or deleted. this method will be called
		 * from the business logic, with Reminder Obj as the parameter.
		 */
	}

	public ArrayList<ReminderObject> GetTodaysReminders() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		String today = dateFormatter.format(cal.getTime());
		Cursor curObj = null;

		ArrayList<ReminderObject> listRemObj = new ArrayList<ReminderObject>();
		String sql = "SELECT * FROM " + TABLE_REMINDER + " WHERE Date like '%"
				+ today + "%'";
		curObj = dbObj.rawQuery(sql, null);
		if (null != curObj) {
			if (curObj.moveToFirst()) {
				do {

					listRemObj.add(new ReminderObject(curObj.getString(0),
							curObj.getString(1), curObj.getString(2), curObj
							.getString(3)));

				} while (curObj.moveToNext() != false);
			}
		}
		return listRemObj;
	}

	public void InsertReminder(ReminderObject reminderObj) {

		try {
			String sql = "INSERT INTO " + TABLE_REMINDER + " VALUES(" + "'"
					+ reminderObj.getId() + "'," + "'" + reminderObj.getDate()
					+ "'," + "'" + reminderObj.getTime() + "'," + "'"
					+ reminderObj.getNote() + "')";
			dbObj.execSQL(sql);
			MyToast.RaiseToast("Reminder added successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void DeleteReminders(ArrayList<String> listId) {
		for (int i = 0; i < listId.size(); i++) {
			String Id = "'" + listId.get(i) + "'";
			String sql = " DELETE FROM " + TABLE_REMINDER
					+ " WHERE ReminderId = " + Id;
			dbObj.execSQL(sql);
			MyToast.RaiseToast("Deleted Reminder!");
		}
	}

	/**
	 * Insert into SMS details table
	 * 
	 * @param smsObj
	 */
	public void InsertSMS(SmsObject smsObj) {

		try {
			String sql = "INSERT INTO " + TABLE_MESSAGESERVICE + " VALUES("
					+ "'" + smsObj.getId() + "'," + "'" + smsObj.getDate()
					+ "'," + "'" + smsObj.getTime() + "'," + "'"
					+ smsObj.getText() + "'," + "'" + smsObj.getPhoneNumber()
					+ "')";
			dbObj.execSQL(sql);
			MyToast.RaiseToast("SMS added successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Insert values into table which associate Reminder and SMS
	 * 
	 * @param reminderId
	 * @param smsId
	 */
	public void InsertIntoRemSmsAssocTable(String reminderId, String smsId) {
		try {
			String sql = "INSERT INTO " + TABLE_REMINDER_MESSAGE_ASSOC
					+ " VALUES(" + "'" + reminderId + "'," + "'" + smsId + "')";
			dbObj.execSQL(sql);
			MyToast.RaiseToast("Values added in Associate table succesfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
