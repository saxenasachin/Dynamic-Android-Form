package com.github.jarvis.controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.jarvis.dynamicformbuilder.R;
import com.github.jarvis.validations.ImageClickListener;

/**
 * Represents a field that displays a value.
 * <p/>
 * For the field value, the associated FormModel can return a string or any object. The value's {@code toString} method
 * will be used to display the value.
 */
public class ButtonController extends LabeledFieldController {
    private ImageClickListener imageClickListener;
    private boolean isEnabled;
    /**
     * Constructs a new instance of a value field.
     *
     * @param ctx               the Android context
     * @param name              the name of the field
     * @param labelText         the label to display beside the field. Set to {@code null} to not show a label.
     */
    public ButtonController(Context ctx, String name, String labelText, ImageClickListener imageClickListener, boolean isEnabled) {
        super(ctx, name, labelText, false);
        this.imageClickListener=imageClickListener;
        this.isEnabled = isEnabled;
    }

    @Override
    protected View createFieldView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final TextView textView = (TextView)layoutInflater.inflate(R.layout.value_field, null);
        refresh(textView);

        return textView;
    }

    private TextView getTextView() {
        return (TextView)getView().findViewById(R.id.value_text);
    }

    private void refresh(TextView textView) {
        Object value = getModel().getValue(getName());
        textView.setText(value != null ? value.toString() : "");
        textView.setEnabled(isEnabled);
        if (isEnabled) {
            textView.setBackgroundColor(Color.parseColor("#f68c1e"));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageClickListener.onButtonClicked();
                }
            });
        } else {
            textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
            textView.setOnClickListener(null);
        }
    }

    public void toggleButton(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void refresh() {
        refresh(getTextView());
    }
}