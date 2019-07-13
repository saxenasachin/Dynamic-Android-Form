package com.github.jarvis.controllers;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.jarvis.FormController;
import com.github.jarvis.validations.InputValidator;
import com.github.jarvis.validations.ValidationListener;

import java.util.Set;

/**
 * Represents a field that allows free-form text.
 */
public class TextViewController extends LabelTextController {
    private final int textViewId = FormController.generateViewId();

    private int inputType;
    private final String placeholder;
    private final String submittedValue;

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param validators    contains the validations to process on the field
     * @param inputType     the content type of the text box as a mask; possible values are defined by {@link InputType}.
     *                      For example, to enable multi-line, enable {@code InputType.TYPE_TEXT_FLAG_MULTI_LINE}.
     */
    public TextViewController(Context ctx, String name, String labelText, String placeholder, String submittedValue, Set<InputValidator> validators, int inputType) {
        super(ctx, name, labelText, validators);
        this.placeholder = placeholder;
        this.inputType = inputType;
        this.submittedValue=submittedValue;

    }
    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param validators    contains the validations to process on the field
     */
    public TextViewController(Context ctx, String name, String labelText, String placeholder, String submittedValue, Set<InputValidator> validators) {
        this(ctx, name, labelText, placeholder, submittedValue, validators, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param isRequired    indicates if the field is required or not
     * @param inputType     the content type of the text box as a mask; possible values are defined by {@link InputType}.
     *                      For example, to enable multi-line, enable {@code InputType.TYPE_TEXT_FLAG_MULTI_LINE}.
     */
    public TextViewController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, int inputType) {
        super(ctx, name, labelText, isRequired);
        this.placeholder = placeholder;
        this.submittedValue = submittedValue;
        this.inputType = inputType;
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param isRequired    indicates if the field is required or not
     * @param inputType     the content type of the text box as a mask; possible values are defined by {@link InputType}.
     *                      For example, to enable multi-line, enable {@code InputType.TYPE_TEXT_FLAG_MULTI_LINE}.
     */
    public TextViewController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, boolean isMobile, int inputType) {
        super(ctx, name, labelText, isRequired, isMobile);
        this.placeholder = placeholder;
        this.submittedValue = submittedValue;
        this.inputType = inputType;
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param isRequired    indicates if the field is required or not
     */
    public TextViewController(Context ctx, String name, String labelText, String placeholder, String submittedValue,
                              boolean isRequired, boolean isMobile, ValidationListener validationListener) {
        this(ctx, name, labelText, placeholder,submittedValue, isRequired, isMobile, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     */
    public TextViewController(Context ctx, String name, String labelText, String placeholder, String submittedValue) {
        this(ctx, name, labelText, placeholder,submittedValue, false, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     */
    public TextViewController(Context ctx, String name, String labelText) {
        this(ctx, name, labelText, null,null, false, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Returns the EditText view associated with this element.
     *
     * @return the EditText view associated with this element
     */
    public TextView getTextView() {
        return (TextView)getView().findViewById(textViewId);
    }

    /**
     * Returns a mask representing the content input type. Possible values are defined by {@link InputType}.
     *
     * @return a mask representing the content input type
     */
    public int getInputType() {
        return inputType;
    }

    private void setInputTypeMask(int mask, boolean enabled) {
        if (enabled) {
            inputType = inputType | mask;
        } else {
            inputType = inputType & ~mask;
        }
        if (isViewCreated()) {
            getTextView().setInputType(inputType);
        }
    }

    /**
     * Indicates whether this text box has multi-line enabled.
     *
     * @return  true if this text box has multi-line enabled, or false otherwise
     */
    public boolean isMultiLine() {
        return (inputType | InputType.TYPE_TEXT_FLAG_MULTI_LINE) != 0;
    }

    /**
     * Enables or disables multi-line input for the text field. Default is false.
     *
     * @param multiLine if true, multi-line input is allowed, otherwise, the field will only allow a single line.
     */
    public void setMultiLine(boolean multiLine) {
        setInputTypeMask(InputType.TYPE_TEXT_FLAG_MULTI_LINE, multiLine);
    }

    @Override
    protected View createFieldView() {
        final TextView textView = new TextView(getContext());
        textView.setId(textViewId);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(15);

        FrameLayout.LayoutParams llp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(10, 0, 0, 0);
        textView.setLayoutParams(llp);

        if (submittedValue != null && !submittedValue.isEmpty()) {
            textView.setText(submittedValue);
            getModel().setValue(getName(),submittedValue);
        }
        refresh(textView);

        if (submittedValue != null && !submittedValue.isEmpty()) {
            textView.setText(submittedValue);
        }

        return textView;
    }

    private void refresh(TextView textView) {
        Object value = getModel().getValue(getName());
        String valueStr = value != null ? value.toString() : "";
        if (!valueStr.equals(textView.getText().toString()))
            textView.setText(valueStr);
    }

    @Override
    public void refresh() {
        refresh(getTextView());
    }
}
