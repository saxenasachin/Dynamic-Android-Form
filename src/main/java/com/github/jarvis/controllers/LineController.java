package com.github.jarvis.controllers;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.FrameLayout;

import com.github.jarvis.FormController;
import com.github.jarvis.validations.InputValidator;

import java.util.Set;

/**
 * Represents a field that allows free-form text.
 */
public class LineController extends BaseLineController {
    private final int lineId = FormController.generateViewId();

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
    public LineController(Context ctx, String name, String labelText, String placeholder, String submittedValue, Set<InputValidator> validators, int inputType) {
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
    public LineController(Context ctx, String name, String labelText, String placeholder, String submittedValue, Set<InputValidator> validators) {
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
    public LineController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, int inputType) {
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
    public LineController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, boolean isMobile, int inputType) {
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
    public LineController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, boolean isMobile) {
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
    public LineController(Context ctx, String name, String labelText, String placeholder, String submittedValue) {
        this(ctx, name, labelText, placeholder,submittedValue, false, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     */
    public LineController(Context ctx, String name, String labelText) {
        this(ctx, name, labelText, null,null, false, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Returns the EditText view associated with this element.
     *
     * @return the EditText view associated with this element
     */
    public View getLineView() {
        return getView().findViewById(lineId);
    }

    /**
     * Returns a mask representing the content input type. Possible values are defined by {@link InputType}.
     *
     * @return a mask representing the content input type
     */
    public int getInputType() {
        return inputType;
    }


    @Override
    protected View createFieldView() {
        final View view = new View(getContext());
        view.setId(lineId);

        FrameLayout.LayoutParams llp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 5);
        llp.setMargins(0, 0, 0, 0);
        view.setLayoutParams(llp);
        view.setBackgroundColor(Color.BLACK);

        if (submittedValue != null) {
            getModel().setValue(getName(),submittedValue);
        }
        refresh(view);

        return view;
    }

    private void refresh(View view) {
        Object value = getModel().getValue(getName());
        String valueStr = value != null ? value.toString() : "";
    }

    @Override
    public void refresh() {
        refresh(getLineView());
    }
}
