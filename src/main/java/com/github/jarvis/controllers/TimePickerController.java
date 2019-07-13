package com.github.jarvis.controllers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.github.jarvis.FormController;
import com.github.jarvis.dynamicformbuilder.R;
import com.github.jarvis.validations.InputValidator;
import com.github.jarvis.validations.ValidationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class TimePickerController extends LabeledFieldController {
    private final int editTextId = FormController.generateViewId();

    private TimePickerDialog timePickerDialog = null;
    private final SimpleDateFormat displayFormat;
    private final TimeZone timeZone;
    private final boolean is24HourView;
    private String placeHolder;
    private String submittedValue;
    private ValidationListener validationListener;

    /**
     * Constructs a new instance of a submittedValue picker field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param validators    contains the validations to process on the field
     * @param displayFormat the format of the submittedValue to show in the text box when a submittedValue is set
     * @param is24HourView  the format of submittedValue picker dialog should be 24 hour format or not
     */
    public TimePickerController(Context ctx, String name, String labelText, Set<InputValidator> validators, SimpleDateFormat displayFormat, boolean is24HourView) {
        super(ctx, name, labelText, validators);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
        this.is24HourView = is24HourView;
    }


    /**
     * Constructs a new instance of a submittedValue picker field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param isRequired    indicates if the field is required or not
     * @param displayFormat the format of the submittedValue to show in the text box when a submittedValue is set
     * @param is24HourView  the format of submittedValue picker dialog should be 24 hour format or not
     */
    public TimePickerController(Context ctx, String name, String labelText, String placeHolder, String submittedValue, boolean isRequired,
                                SimpleDateFormat displayFormat, boolean is24HourView, ValidationListener validationListener) {
        super(ctx, name, labelText, isRequired);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
        this.is24HourView = is24HourView;
        this.placeHolder = placeHolder;
        this.submittedValue = submittedValue;
        this.validationListener = validationListener;
    }

    /**
     * Constructs a new instance of a submittedValue picker field, with the selected submittedValue displayed in "HH:mm" format.
     *
     * @param placeHolder
     * @param name        the name of the field
     * @param labelText   the label to display beside the field
     */
    public TimePickerController(Context context, String name, String labelText, String placeHolder, String submittedValue, ValidationListener validationListener) {
        this(context, name, labelText, placeHolder, submittedValue, false, new SimpleDateFormat("hh:mm a", Locale.getDefault()), false, validationListener);
    }

    @Override
    protected View createFieldView() {
        final EditText editText = new EditText(getContext());
        editText.setId(editTextId);

        editText.setSingleLine(true);
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        editText.setKeyListener(null);
        editText.setHint(placeHolder);
        refresh(editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getContext(), editText);
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerDialog(getContext(), editText);
                }
            }
        });

        editText.setText(submittedValue);

        if (submittedValue != null && !submittedValue.isEmpty()) {
            try {
                Date value = displayFormat.parse(submittedValue);
                getModel().setValue(getName(), value);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return editText;
    }

    private void showTimePickerDialog(Context context, final EditText editText) {
// don't show dialog again if it's already being shown
        if (timePickerDialog == null) {
            Date date = (Date) getModel().getValue(getName());
            if (date == null) {
                date = new Date();
            }
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeZone(timeZone);
            calendar.setTime(date);

            timePickerDialog = new TimePickerDialog(context, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeZone(timeZone);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    getModel().setValue(getName(), calendar.getTime());
                    editText.setText(displayFormat.format(calendar.getTime()));
                    validationListener.onValidateInput();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourView);

            timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    timePickerDialog = null;
                }
            });

            timePickerDialog.show();
        }
    }

    private EditText getEditText() {
        return (EditText) getView().findViewById(editTextId);
    }

    private void refresh(EditText editText) {
        Date value = (Date) getModel().getValue(getName());
        editText.setText(value != null ? displayFormat.format(value) : "");
    }

    @Override
    public void refresh() {
        refresh(getEditText());
    }
}



