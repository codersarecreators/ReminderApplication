package com.codersarecreators.reminder;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends Activity {

	private static Context contextObj = null;
	private static ArrayList<ReminderObject> listReminderObject = null;
	private static ArrayList<String> listReminderObjectIds = null;

	/*
	 * METHODS BEGIN HERE
	 */
	public void AddReminderForDeletingById(String id) {
		if (null == listReminderObjectIds)
			listReminderObjectIds = new ArrayList<String>();
		listReminderObjectIds.add(id);
	}

	public void RemoveReminderById(String Id) {
		if (null != listReminderObjectIds) {
			listReminderObjectIds.remove(listReminderObjectIds.indexOf(Id));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set the contextObj
		// Populate list view with Reminders
		contextObj = this;
		ListView listView = (ListView) findViewById(R.id.listView);
		listReminderObject = new ArrayList<ReminderObject>();
		// Pass the list of Reminders to ArrayAdapter
		listReminderObject = DatabaseGateway.GetDbGateWay()
				.GetTodaysReminders();
		// writing demo code, that I anticipate, will be removed later on...

		if (listReminderObject == null)
			listReminderObject = new ArrayList<ReminderObject>();

		// call the GetListForDisplay so that the listString is populated.
		// GetListForDisplay();
		ArrayAdapter<ReminderObject> adapter = new ArrayAdapter<ReminderObject>(
				this, android.R.layout.simple_list_item_1, listReminderObject);
		listView.setAdapter(adapter);

		// Listener set on items in the list
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyToast.RaiseToast("Item in the List clicked");
			}
		});
	}

	// GetContext Method
	public static Context GetContext() {
		return contextObj;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Function called when clicked on + sign
	public void displayAddReminderScreen(View view) {
		// Launches new Activity AddReminder which has functionalities to add
		// new Reminder
		Intent intent = new Intent(this, AddReminder.class);
		startActivity(intent);
	}

	// Delete reminder function of deleteReminderDialogue
	/*
	 * public void deleteReminder(View view) { // System.out.println("test");
	 * 
	 * } }
	 */

	// Returns to Home Screen from Delete Reminder Dialogue
	public void returnToHomeScreen(View view) {

	}

	public void displayDeleteReminderDialogue(View view) {
		/*
		 * what we have to do here is that, we just have to get list of
		 * reminders for today. this has to be retrieved in the way, such that,
		 * just one call should get all the current reminders loading only some
		 * of the reminders will also not be very heavy on the database.
		 */
		// listReminderObject =
		// DatabaseGateway.GetDbGateWay().GetTodaysReminders();
		/*
		 * the list is always going to be in the same state because the activity
		 * has not been destroyed.
		 */
		if (listReminderObject != null) {
			if (listReminderObject.size() > 0) {
				final Dialog deleteReminderDialogue = new Dialog(this);
				deleteReminderDialogue.setCancelable(true);
				// delete_reminder_dialogue.xml is the View which will be
				// inflated
				// on
				// the dialogue
				View deleteReminderDialogueView = getLayoutInflater().inflate(
						R.layout.delete_reminder_dialogue, null);
				ListView listview = (ListView) deleteReminderDialogueView
						.findViewById(R.id.listViewDeleteReminder);
				// Fill the list view with list of reminders and checkboxes, so
				// need
				// CustomAdapter

				CustomListAdapterDeleteReminderDialog customListAdapterDeleteReminderDialog = new CustomListAdapterDeleteReminderDialog(
						deleteReminderDialogueView.getContext(),
						listReminderObject);
				listview.setAdapter(customListAdapterDeleteReminderDialog);
				Button deleteButton = (Button)deleteReminderDialogueView.findViewById(R.id.deleteReminderDialogue_deleteBtn);
				deleteButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if ((null != listReminderObjectIds)
								&& (listReminderObjectIds.size() > 0)) {
							DatabaseGateway.GetDbGateWay().DeleteReminders(
									listReminderObjectIds);
							
							deleteReminderDialogue.dismiss();
						}
					}
				});
				/*
				 * listview.setOnItemClickListener(new OnItemClickListener() {
				 * 
				 * @Override public void onItemClick(AdapterView<?> parent, View
				 * view, int position, long id) {
				 * 
				 * CheckBox checkBox = (CheckBox)findViewById(view.getId());
				 * if(checkBox.isChecked()) { if(listReminderObjectIds == null)
				 * { listReminderObjectIds = new ArrayList<String>(); }
				 * listReminderObjectIds
				 * .add(listReminderObject.get(position).getId());
				 * MyToast.RaiseToast
				 * ("ReminderAdded successfully for addition!"); } } });
				 */

				deleteReminderDialogue
						.setContentView(deleteReminderDialogueView);
				deleteReminderDialogue.show();
			} else {
				MyToast.RaiseToast("No Reminders found in the list to delete!");
			}
		}
	}

	/*
	 * private void GetListForDisplay() { for (int i = 0; i <
	 * listReminderObject.size(); i++) {
	 * listString.add(listReminderObject.get(i).getTime() + ": " +
	 * listReminderObject.get(i).getNote()); } }
	 */

	/*
	 * public void getSelectedItemText(View v) { CheckBox checkBox = (CheckBox)
	 * findViewById(v.getId()); MyToast.RaiseToast("CheckBox: " +
	 * checkBox.getText()); }
	 */
}
