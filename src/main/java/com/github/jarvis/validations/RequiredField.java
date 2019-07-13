package com.github.jarvis.validations;

import android.content.res.Resources;
import com.github.jarvis.dynamicformbuilder.R;

/**
 * Represents a validation error where input is missing for a required field.
 */
public class RequiredField extends ValidationError {

    /**
     * Creates a new instance with the specified field name.
     *
     * @param fieldName     the field name
     * @param fieldLabel    the field label
     */
    public RequiredField(String fieldName, String fieldLabel) {
        super(fieldName, fieldLabel);
    }

    @Override
    public String getMessage(Resources resources) {
        return String.format(resources.getString(R.string.required_field_error_msg), getFieldLabel());
    }
}
