package com.github.jarvis.validations;

import android.content.Context;
import android.content.res.Resources;

import com.github.jarvis.dynamicformbuilder.R;
import com.github.jarvis.utils.MessageUtil;

import java.util.List;

public class PopUpValidationErrorDisplay implements ValidationErrorDisplay {
    private final Context context;

    public PopUpValidationErrorDisplay(Context context) {
        this.context = context;
    }

    @Override
    public void resetErrors() {
        // Do nothing, the popup can be dismissed
    }

    @Override
    public void showErrors(List<ValidationError> errors) {
        StringBuilder sb = new StringBuilder();
        Resources res = context.getResources();
        for (ValidationError error : errors) {
            sb.append(error.getMessage(res)).append("\n");
        }
        MessageUtil.showAlertMessage(context.getString(R.string.validation_error_title), sb.toString(), context);
    }
}
