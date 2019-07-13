package com.github.jarvis.validations;

import android.text.TextUtils;

public class RequiredMobileValidator implements InputValidator {
    @Override
    public ValidationError validate(Object value, String fieldName, String fieldLabel) {
        if (value == null || (value instanceof String && !isValidPhoneNumber((String)value))) {
            return new RequiredMobile(fieldName, fieldLabel);
        }
        return null;
    }

    /**
     * This method validates phone number, phone number should be
     * of 10 digit, starts with 6, 7, 8 or 9 and can't be empty
     * @param phone - Phone number
     * @return - true or false
     */
    private boolean isValidPhoneNumber(String phone) {
        if (phone.length() < 10) return false;
        boolean isEmpty = TextUtils.isEmpty(phone);
        boolean isValidPhoneNumber = isNumberStarsWithValidDigits(phone);

        if (isEmpty) return false;
        return isValidPhoneNumber;
    }

    /**
     * This method checks if number starts with 6, 7, 8 or 9
     * @param mobile - Mobile Number
     * @return - true or false
     */
    private boolean isNumberStarsWithValidDigits(String mobile) {
        String regEx = "^[56789]\\d{9}$";
        return mobile.matches(regEx);
    }

    /**
     * Makes every instances of {@link RequiredMobileValidator} equal.
     *
     * @param o The object to compare.
     * @return true if o is also an instance of RequiredFieldValidator, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) || o != null && getClass() == o.getClass();
    }

    /**
     * Every instance of {{@link RequiredMobileValidator}} share the same hashcode.
     *
     * @return 0
     */
    @Override
    public int hashCode() {
        return 0;
    }
}
