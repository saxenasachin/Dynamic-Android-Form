package com.github.jarvis;

/**
 * Created by shivendrakumar on 13/10/17.
 */

public class ImageDataModel {

    public String text;
    public String drawable;
    public String color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDrawable() {
        return drawable;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ImageDataModel(String text,String drawable, String color )
    {
        this.text=text;
        this.drawable=drawable;
        this.color=color;
    }
}
