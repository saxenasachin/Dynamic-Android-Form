package com.github.jarvis.controllers;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.github.jarvis.AutoCompleteTextViewDataModel;
import com.github.jarvis.FormController;
import com.github.jarvis.validations.InputValidator;

import java.util.List;
import java.util.Set;

/**
 * Represents a field that allows free-form text.
 */
public class AutoCompleteEditTextController extends LabeledFieldController {
    private final int autoCompleteEditTextId = FormController.generateViewId();

    private int inputType;
    private final String placeholder;
    private final String submittedValue;
    List<AutoCompleteTextViewDataModel> autoCompleteTextViewDataModelList;
    private Context context;

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
    public AutoCompleteEditTextController(Context ctx, String name, String labelText, String placeholder, String submittedValue, Set<InputValidator> validators, int inputType) {
        super(ctx, name, labelText, validators);
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
     * @param validators    contains the validations to process on the field
     */
    public AutoCompleteEditTextController(Context ctx, String name, String labelText, String placeholder, String submittedValue, Set<InputValidator> validators) {
        this(ctx, name, labelText, placeholder,submittedValue, validators, InputType.TYPE_CLASS_TEXT);
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
    public AutoCompleteEditTextController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, int inputType) {
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
    public AutoCompleteEditTextController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, boolean isMobile, int inputType, List<AutoCompleteTextViewDataModel> autoCompleteTextViewDataModelList) {
        super(ctx, name, labelText, isRequired, isMobile);
        this.placeholder = placeholder;
        this.submittedValue = submittedValue;
        this.inputType = inputType;
        this.autoCompleteTextViewDataModelList=autoCompleteTextViewDataModelList;
        this.context=ctx;
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
    public AutoCompleteEditTextController(Context ctx, String name, String labelText, String placeholder, String submittedValue, boolean isRequired, boolean isMobile,List<AutoCompleteTextViewDataModel> autoCompleteTextViewDataModelList) {
        this(ctx, name, labelText, placeholder,submittedValue, isRequired, isMobile, (isMobile ? InputType.TYPE_CLASS_PHONE : InputType.TYPE_CLASS_TEXT),autoCompleteTextViewDataModelList);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     */
    public AutoCompleteEditTextController(Context ctx, String name, String labelText, String placeholder, String submittedValue) {
        this(ctx, name, labelText, placeholder,submittedValue, false, InputType.TYPE_CLASS_TEXT);
    }


    /**
     * Returns the EditText view associated with this element.
     *
     * @return the EditText view associated with this element
     */
    public AutoCompleteTextView getAutoCompleteTextViewId() {
        return (AutoCompleteTextView)getView().findViewById(autoCompleteEditTextId);
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
            getAutoCompleteTextViewId().setInputType(inputType);
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

    /**
     * Indicates whether this text field hides the input text for security reasons.
     *
     * @return  true if this text field hides the input text, or false otherwise
     */
    public boolean isSecureEntry() {
        return (inputType | InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0;
    }

    /**
     * Enables or disables secure entry for this text field. If enabled, input will be hidden from the user. Default is
     * false.
     *
     * @param isSecureEntry if true, input will be hidden from the user, otherwise input will be visible.
     */
    public void setSecureEntry(boolean isSecureEntry) {
        setInputTypeMask(InputType.TYPE_TEXT_VARIATION_PASSWORD, isSecureEntry);
    }

    @Override
    protected View createFieldView() {
        final AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(getContext());
        autoCompleteTextView.setId(autoCompleteEditTextId);

        autoCompleteTextView.setSingleLine(!isMultiLine());
        if (placeholder != null) {
            autoCompleteTextView.setHint(placeholder);
        }


        autoCompleteTextView.setInputType(inputType);
        if (inputType == InputType.TYPE_CLASS_PHONE) {
            autoCompleteTextView.setFilters(new InputFilter[] {
                    new InputFilter.LengthFilter(10)
            });
        }
        refresh(autoCompleteTextView);
        if (submittedValue != null) {
            autoCompleteTextView.setText(submittedValue);
            getModel().setValue(getName(), autoCompleteTextView.getText().toString());
        }
        ArrayAdapter<AutoCompleteTextViewDataModel> adapter = new ArrayAdapter<AutoCompleteTextViewDataModel>(context,android.R.layout.simple_list_item_1,autoCompleteTextViewDataModelList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getModel().setValue(getName(), autoCompleteTextView.getText().toString());
            }
        });

        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });

        return autoCompleteTextView;
    }

    private void refresh(AutoCompleteTextView autoCompleteTextView) {
        Object value = getModel().getValue(getName());
        String valueStr = value != null ? value.toString() : "";
        if (!valueStr.equals(autoCompleteTextView.getText().toString()))
            autoCompleteTextView.setText(valueStr);
    }

    @Override
    public void refresh() {
        refresh(getAutoCompleteTextViewId());
    }
}
