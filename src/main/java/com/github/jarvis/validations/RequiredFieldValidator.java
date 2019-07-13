package com.github.jarvis.validations;

import android.text.TextUtils;

import com.github.jarvis.ImageDataModel;

import java.util.ArrayList;

public class RequiredFieldValidator implements InputValidator {
    @Override
    public ValidationError validate(Object value, String fieldName, String fieldLabel) {

        if (value instanceof ArrayList<?>) {
            ArrayList<?> imageArrayList = (ArrayList<?>)value;
            for (int i = 0; i < imageArrayList.size(); i++) {
                Object o = imageArrayList.get(i);
                if (o instanceof ImageDataModel) {
                    ImageDataModel imageDataModel = (ImageDataModel) o;
                    if (imageDataModel.getDrawable() == null || imageDataModel.getDrawable().equalsIgnoreCase("")) {
                        return new  RequiredField(fieldName, imageDataModel.getText());
                    }
                }
            }
        }
        if (value == null || (value instanceof String && TextUtils.isEmpty((String) value))) {
            return new  RequiredField(fieldName, fieldLabel);
        }
        return null;
    }

    /**
     * Makes every instances of {@link RequiredFieldValidator} equal.
     *
     * @param o The object to compare.
     * @return true if o is also an instance of RequiredFieldValidator, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) || o != null && getClass() == o.getClass();
    }

    /**
     * Every instance of {{@link RequiredFieldValidator}} share the same hashcode.
     *
     * @return 0
     */
    @Override
    public int hashCode() {
        return 0;
    }
}
