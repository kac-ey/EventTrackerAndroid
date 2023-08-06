package com.zybooks.eventtracker;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {

    private Button mSaveButton, mDateButton, mStartTimeButton, mEndTimeButton;
    private EditText mTitleText, mDescription;
    private DatePickerDialog datePickerDialog;
    int startHour, startMinute, endHour, endMinute;
    String title, date, startTime, endTime, description;
    EventDb eventDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Assign variable values
        mSaveButton = (Button) findViewById(R.id.saveButton);
        mDateButton = (Button) findViewById(R.id.dateButton);
        mStartTimeButton = (Button) findViewById(R.id.startTimeButton);
        mEndTimeButton = (Button) findViewById(R.id.endTimeButton);
        mTitleText = (EditText) findViewById(R.id.eventTitle);
        mDescription = (EditText) findViewById(R.id.eventDescription);

        title = mTitleText.getText().toString();
        date = mDateButton.getText().toString();
        startTime = mStartTimeButton.getText().toString();
        endTime = mEndTimeButton.getText().toString();
        description = mDescription.getText().toString();

        eventDb = new EventDb(AddEvent.this);

        // Initialize date picker
        initDatePicker();
        mDateButton.setText(getCurrentDate());

        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                startHour = hour;
                                startMinute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, startHour, startMinute);
                                mStartTimeButton.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(startHour, startMinute);
                timePickerDialog.show();
            }
        });

        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                endHour = hour;
                                endMinute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, endHour, endMinute);
                                mEndTimeButton.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(endHour, endMinute);
                timePickerDialog.show();
            }
        });

        // Disable Save button if no event name
        mTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mTitleTextInput = mTitleText.getText().toString().trim();
                mSaveButton.setEnabled(!mTitleTextInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventDb.insertEvent(title, date, startTime, endTime, description);

                // Return to event list screen
                Intent intent = new Intent(AddEvent.this, ListEvent.class);
                startActivity(intent);
            }
        });
    }

    public void datePicker(View view) {
        datePickerDialog.show();
    }

    public void cancelEvent(View view) {
        // Return to event list screen
        Intent intent = new Intent(this, ListEvent.class);
        startActivity(intent);
    }

    /* Methods for Date spinner and display */

    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    // Initialize Date Picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // January is 0
                month = month + 1;
                // Call makeDateString method
                String date = makeDateString(day, month, year);
                mDateButton.setText(date);
            }
        };

        // Display current date as default
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    // Display date as string rather than integers
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    // Return month abbreviation to avoid confusion
    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        // Default shouldn't happen
        return "JAN";
    }
    /* End methods for Date spinner and display */
}