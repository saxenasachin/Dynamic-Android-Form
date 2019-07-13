package com.github.jarvis.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.jarvis.FormElementController;
import com.github.jarvis.dynamicformbuilder.R;
import com.github.jarvis.validations.InputValidator;
import com.github.jarvis.validations.RequiredFieldValidator;
import com.github.jarvis.validations.RequiredMobileValidator;
import com.github.jarvis.validations.ValidationError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An abstract class that represents a generic form field with an associated label.
 */
public abstract class BaseLineController extends FormElementController {
    private static final RequiredFieldValidator REQUIRED_FIELD_VALIDATOR = new RequiredFieldValidator();
    private static final RequiredMobileValidator REQUIRED_MOBILE_VALIDATOR = new RequiredMobileValidator();
    private final String labelText;
    private View fieldView;
    private Set<InputValidator> validators;

    /**
     * Creates a labeled field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. If null, no label is displayed and the field will
     *                      occupy the entire length of the row.
     * @param isRequired    indicates whether this field is required. If true, this field checks for a non-empty or
     *                      non-null value upon validation. Otherwise, this field can be empty.
     */
    public BaseLineController(Context ctx, String name, String labelText, boolean isRequired) {
        this(ctx, name, labelText, new HashSet<InputValidator>());
        setIsRequired(isRequired);
    }


    /**
     * Creates a labeled field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. If null, no label is displayed and the field will
     *                      occupy the entire length of the row.
     * @param isRequired    indicates whether this field is required. If true, this field checks for a non-empty or
     *                      non-null value upon validation. Otherwise, this field can be empty.
     */
    public BaseLineController(Context ctx, String name, String labelText, boolean isRequired, boolean isMobile) {
        this(ctx, name, labelText, new HashSet<InputValidator>());
        setIsRequired(isRequired);
        setIsMobile(isMobile);
    }

    /**
     * Creates a labeled field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. If null, no label is displayed and the field will
     *                      occupy the entire length of the row.
     * @param validators    The list of input validations to add to the field.
     */
    public BaseLineController(Context ctx, String name, String labelText, Set<InputValidator> validators) {
        super(ctx, name);
        this.labelText = labelText;
        this.validators = validators;
    }

    /**
     * Returns the associated label for this field.
     *
     * @return the associated label for this field
     */
    public String getLabel() {
        if (labelText == null) return getName();
        else return labelText;
    }

    /**
     * Sets whether this field is required to have user input.
     *
     * @param required  if true, this field checks for a non-empty or non-null value upon validation. Otherwise, this
     *                  field can be empty.
     */
    public void setIsRequired(boolean required) {
        if (! required) {
            validators.remove(REQUIRED_FIELD_VALIDATOR);
        } else if (! isRequired()) {
            validators.add(REQUIRED_FIELD_VALIDATOR);
        }
    }

    public void setIsMobile(boolean isMobile) {
        if (! isMobile) {
            validators.remove(REQUIRED_MOBILE_VALIDATOR);
        } else if (! isMobileRequired()) {
            validators.add(REQUIRED_MOBILE_VALIDATOR);
        }
    }

    /**
     * Indicates whether this field requires an input value.
     *
     * @return  true if this field is required to have input, otherwise false
     */
    public boolean isRequired() {
        return validators.contains(REQUIRED_FIELD_VALIDATOR);
    }

    public boolean isMobileRequired() {
        return validators.contains(REQUIRED_MOBILE_VALIDATOR);
    }

    /**
     * Runs a validation on the user input and returns all the validation errors of this field.
     * Previous error messages are removed when calling {@code validateInput()}.
     *
     * @return  a list containing all the validation errors
     */
    public List<ValidationError> validateInput() {
        List<ValidationError> errors = new ArrayList<>();
        Object value = getModel().getValue(getName());
        ValidationError error;
        for (InputValidator validator : validators) {
            error = validator.validate(value, getName(), getLabel());
            if (error != null){
                errors.add(error);
            }
        }

        return errors;
    }

    /**
     * Returns the associated view for the field (without the label view) of this element.
     *
     * @return          the view for this element
     */
    public View getFieldView() {
        if (fieldView == null) {
            fieldView = createFieldView();
        }
        return fieldView;
    }

    /**
     * Constructs the view associated with this field without the label. It will be used to combine with the label.
     *
     * @return          the newly created view for this field
     */
    protected abstract View createFieldView();

    @Override
    protected View createView() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater != null ? inflater.inflate(R.layout.form_line_element, null) : null;
        if (view != null) {
            FrameLayout container = (FrameLayout)view.findViewById(R.id.field_container);
            container.addView(getFieldView());
        }
        return view;
    }

    @Override
    public void setError(String message) {

    }
}
