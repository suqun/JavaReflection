package com.larry.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/5/29
 */
public class MyClass {


    protected List<String> stringList = new ArrayList<String>();

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
