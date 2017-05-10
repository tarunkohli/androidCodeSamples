package com.qtsachit.helper.util.picker;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * The class is Created by Sachit on 31/January/2017
 * <p>
 * Description- This class will provide the helper method for Date picker dialog
 * <p>
 * Additional notes-
 */
public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	private Pickable mPickableInstance;
	private String mSelectedDate	=	"";
	private boolean mIsSetMinDate = false;
	private long mMinDate = 0;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
		if (mIsSetMinDate) {
			try {
				if (mMinDate > 0) {
					datePickerDialog.getDatePicker().setMinDate(mMinDate);
				} else {
					datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return datePickerDialog;
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// The month is zero indexed.
		month = month + 1;
		mSelectedDate	=	"" + (day < 10 ? "0" + day : day) + "-" + (month < 10 ? "0" + month : month) + "-" + year;
		mPickableInstance.onPickerDismissed(mSelectedDate);
	}

	/***
	 * In Android 4.0 only a dialog is presented with 2 button, Set and Cancel. This method
	 * implementation handles the 'Cancel' button behavior in the app.
	 * ***/
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		mSelectedDate	=	"";
		mPickableInstance.onPickerDismissed(mSelectedDate);
	}

	public void show(FragmentManager fragmentManager, String tag, Pickable ctx) {
		this.show(fragmentManager, tag);
		mPickableInstance = ctx;
	}

	/**
	 * Sets the minimal date to DatePickerDialog. If provided minDate value is zero then DatePicker
	 * will show current date as minimal date. This method should get called before show(...)
	 * method.
	 * 
	 * @param minDate
	 *            in milliseconds
	 */
	public void setMinimumDate(long minDate) {
		mIsSetMinDate = true;
		mMinDate = minDate;
	}
}