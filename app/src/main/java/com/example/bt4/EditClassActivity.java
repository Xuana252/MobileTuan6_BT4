package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bt4.data.MyClass;
import com.example.bt4.data.Student;
import com.example.bt4.database.helper.ClassHelper;

public class EditClassActivity extends AppCompatActivity {

    Button cancelButton;
    Button createClassButton;

    EditText classNameInput;
    EditText classDepartmentInput;


    private MyClass myClass;
    private ClassHelper classHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        classHelper = new ClassHelper(this);


        cancelButton = findViewById(R.id.cancelButton);

        createClassButton = findViewById(R.id.editClassButton);

        classNameInput = findViewById(R.id.classNameInput);

        classDepartmentInput = findViewById(R.id.classDepartmentInput);

        Intent intent = getIntent();
        myClass = (MyClass) intent.getSerializableExtra("CLASS");




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(myClass !=null) {
            classNameInput.setText(myClass.getClassName());
            classDepartmentInput.setText(myClass.getClassDepartment());
            createClassButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String className = classNameInput.getText().toString();
                    String classDepartment = classDepartmentInput.getText().toString();
                    if (className.isEmpty()||classDepartment.isEmpty()) {
                        Toast.makeText(EditClassActivity.this,"please fill all field",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditClassActivity.this,"edit class successfully",Toast.LENGTH_LONG).show();

                        classHelper.update(new MyClass(myClass.getClassID(),className,classDepartment));
                        finish(); // Close the activity
                    }

                }
            });
        }

    }
}
