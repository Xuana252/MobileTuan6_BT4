package com.example.bt4.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyClass implements Serializable {
    private String className;
    private String classID;

    private  String classDepartment;

    private boolean isChecked = false;

    public MyClass(String id,String name,String department) {
        this.classID = id;
        this.className = name;
        this.classDepartment =  department;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public  boolean isChecked() {
        return this.isChecked;
    }

    public String getClassName(){
        return this.className;
    }

    public String getClassID(){
        return this.classID;
    }

    public String getClassDepartment() {return this.classDepartment;}


}