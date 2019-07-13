package com.github.jarvis.controllers;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;

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

/**
 * Represents a field that allows selecting a specific date via a date picker.
 * <p/>
 * For the field value, the associated FormModel must return a {@link Date} instance. No selected date can be
 * represented by returning {@code null} for the value of the field.
 */
public class DatePickerController extends LabeledFieldController {
    private final int editTextId = FormController.generateViewId();

    private DatePickerDialog datePickerDialog = null;
    private final SimpleDateFormat displayFormat;
    private final TimeZone timeZone;
    private String placeHolder;
    private String submittedDated;
    private ValidationListener validationListener;

    /**
     * Constructs a new instance of a date picker field.
     *
     * @param ctx               the Android context
     * @param name              the name of the field
     * @param labelText         the label to display beside the field. Set to {@code null} to not show a label.
     * @param validators        contains the validations to process on the field
     * @param displayFormat     the format of the date to show in the text box when a date is set
     */
    public DatePickerController(Context ctx, String name, String labelText, String placeHolder, String submittedDate, Set<InputValidator> validators, SimpleDateFormat displayFormat) {
        super(ctx, name, labelText, validators);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
        this.placeHolder=placeHolder;
        this.submittedDated=submittedDate;
    }

    /**
     * Constructs a new instance of a date picker field.
     *
     * @param ctx               the Android context
     * @param name              the name of the field
     * @param labelText         the label to display beside the field. Set to {@code null} to not show a label.
     * @param isRequired        indicates if the field is required or not
     * @param displayFormat     the format of the date to show in the text box when a date is set
     */
    public DatePickerController(Context ctx, String name, String labelText, String placeHolder, String submittedDate,
                                boolean isRequired, SimpleDateFormat displayFormat, ValidationListener validationListener) {
        super(ctx, name, labelText, isRequired);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
        this.placeHolder=placeHolder;
        this.submittedDated=submittedDate;
        this.validationListener = validationListener;
    }

    /**
     * Constructs a new instance of a date picker field, with the selected date displayed in "MMM d, yyyy" format.
     *
     * @param name              the name of the field
     * @param labelText         the label to display beside the field
     */
    public DatePickerController(Context context, String name, String labelText, String placeHolder, String submittedDate,
                                boolean isRequired, ValidationListener validationListener) {
        this(context, name, labelText, placeHolder,submittedDate, isRequired, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()), validationListener);
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
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getContext(), editText);
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(getContext(), editText);
                }
            }
        });

        if (submittedDated != null && !submittedDated.isEmpty()) {
            editText.setText(submittedDated);
            getModel().setValue(getName(),submittedDated);
        }
        return editText;
    }

    private void showDatePickerDialog(final Context context, final EditText editText) {
        // don't show dialog again if it's already being shown
        if (datePickerDialog == null) {
            String date = (String)getModel().getValue(getName());
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeZone(timeZone);
            final SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            if (date != null) {
                try {
                    calendar.setTime(format1.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            datePickerDialog = new DatePickerDialog(context, R.style.Theme_AppCompat_DayNight_Dialog, new OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeZone(timeZone);
                    calendar.set(year, monthOfYear, dayOfMonth);

                    String formatted = format1.format(calendar.getTime());
                    getModel().setValue(getName(), formatted);
                    editText.setText(formatted);
                    validationListener.onValidateInput();

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    datePickerDialog = null;
                }
            });

            datePickerDialog.show();
        }
    }

    private EditText getEditText() {
        return (EditText)getView().findViewById(editTextId);
    }

    private void refresh(EditText editText) {
        String value = (String) getModel().getValue(getName());
        editText.setText(value != null ? value : "");
    }

    public void refresh() {
        refresh(getEditText());
    }
}
