package com.example.bt4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.bt4.R;
import com.example.bt4.data.MyClass;
import com.example.bt4.data.Student;

import org.w3c.dom.Text;

import java.util.List;

public class MyClassAdapter extends ArrayAdapter<MyClass> {
    int resource;
    private List<MyClass> classes;
    private Context context;

    public MyClassAdapter(Context context, int resource, List<MyClass> classes) {
        super(context, resource, classes);
        this.context = context;
        this.classes = classes;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(this.getContext());
            v = vi.inflate(this.resource, null);
        }
        MyClass myClass = getItem(position);

        if (myClass != null) {
            TextView classID = (TextView) v.findViewById(R.id.classID);
            TextView className = (TextView) v.findViewById(R.id.className);
            TextView classDepartment = (TextView) v.findViewById(R.id.classDepartment);
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(view -> {

            });

            if (classID != null) {
                classID.setText(myClass.getClassID());
            }
            if (className != null) {
                className.setText(myClass.getClassName());
            }
            if(classDepartment != null) {
                classDepartment.setText(myClass.getClassDepartment());
            }
            checkBox.setChecked(myClass.isChecked());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                myClass.setChecked(isChecked);
            });
        }
        return v;
    }

}