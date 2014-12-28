package com.codersarecreators.reminder;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomListAdapterDeleteReminderDialog extends BaseAdapter {

	private ArrayList<ReminderObject> listReminderNotes;
	// private ArrayList<String> listReminderNotes = null;
	private LayoutInflater layoutInflater;

	public CustomListAdapterDeleteReminderDialog(Context context,
			ArrayList<ReminderObject> listReminderObjects) {

		listReminderNotes = listReminderObjects;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listReminderNotes.size();
	}

	@Override
	public Object getItem(int position) {
		return listReminderNotes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder holder = null;

		if (null == view) {
			view = layoutInflater.inflate(R.layout.delete_reminder_list_item,
					null);

			holder = new ViewHolder();

			holder.checkboxView = (CheckBox) view
					.findViewById(R.id.delete_reminder_checkbox);
			//holder.checkboxView.setId(position);

			holder.checkboxView.setText(listReminderNotes.get(position)
					.toString());
			holder.checkboxView
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton checkBox,
								boolean isChecked) {

							if (isChecked) {
								new MainActivity().AddReminderForDeletingById(listReminderNotes.get(position).getId());
								MyToast.RaiseToast("Inside the checked case: " + listReminderNotes.get(position).getId());

							} else {
								new MainActivity().RemoveReminderById(listReminderNotes.get(position).getId());
								MyToast.RaiseToast("Unchecked!");
							}
						}
					});
		}

		return view;
	}

	static class ViewHolder {
		CheckBox checkboxView;
	}

}
