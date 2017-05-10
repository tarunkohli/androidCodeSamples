package com.qtsachit.helper.util.picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * The class is Created by Sachit on 31/January/2017
 * <p>
 * Description- This class will provide the helper method for Time picker dialog
 * <p>
 * Additional notes-
 */
public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {

	private Pickable mPickableInstance;
	private String mSelectedDate	=	"";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState ) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog( getActivity(), this, hour, minute, DateFormat.is24HourFormat( getActivity() ) );
	}

	public void show(FragmentManager fragmentManager, String tag, Pickable ctx ) {
		this.show( fragmentManager, tag );
		mPickableInstance = ctx;
	}

	public void onTimeSet(TimePicker view, int hour, int minute ) {
		
		mSelectedDate	=	 "" + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
		mPickableInstance.onPickerDismissed(mSelectedDate);
	}
	
	@Override
	public void onDismiss( DialogInterface dialog ) {
		super.onDismiss(dialog);
	}
}