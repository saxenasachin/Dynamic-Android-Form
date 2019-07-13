package com.github.jarvis;

import java.io.Serializable;

/**
 * Created by shivendrakumar on 13/10/17.
 */

public class AutoCompleteTextViewDataModel implements Serializable {

    public String name;
    public String id;

    public AutoCompleteTextViewDataModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
