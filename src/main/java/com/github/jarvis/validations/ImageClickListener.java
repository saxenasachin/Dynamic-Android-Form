package com.github.jarvis.validations;

import android.widget.ImageView;

import com.github.jarvis.ImageDataModel;

/**
 * Created by shivendrakumar on 13/10/17.
 */

public interface ImageClickListener {

    void onImageClicked(int position, ImageDataModel imageDataModel);
    void onButtonClicked();
}
