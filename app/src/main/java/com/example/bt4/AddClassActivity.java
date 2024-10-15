package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bt4.data.MyClass;
import com.example.bt4.database.helper.ClassHelper;

public class AddClassActivity extends AppCompatActivity {

    Button cancelButton;
    Button createClassButton;

    EditText classNameInput;
    EditText classDepartmentInput;


    private ClassHelper classHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        classHelper = new ClassHelper(this);


        cancelButton = findViewById(R.id.cancelButton);

        createClassButton = findViewById(R.id.addClassButton);

        classNameInput = findViewById(R.id.classNameInput);

        classDepartmentInput = findViewById(R.id.classDepartmentInput);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = classNameInput.getText().toString();
                String classDepartment = classDepartmentInput.getText().toString();
                if (className.isEmpty()||classDepartment.isEmpty()) {
                    Toast.makeText(AddClassActivity.this,"please fill all field",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddClassActivity.this,"add class successfully",Toast.LENGTH_LONG).show();

                    classHelper.addNew(new MyClass("1",className,classDepartment));
                    finish(); // Close the activity
                }

            }
        });
    }
}