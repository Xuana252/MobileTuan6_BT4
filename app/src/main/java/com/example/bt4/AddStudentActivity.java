package com.example.bt4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bt4.data.Student;
import com.example.bt4.database.helper.StudentHelper;

import java.util.Calendar;

public class AddStudentActivity extends AppCompatActivity {

    Button cancelButton;
    Button createStudentButton;

    Button datePickerButton;
    EditText studentNameInput;

    EditText studentDobInput;
    private StudentHelper studentHelper;

    private String  classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        studentHelper = new StudentHelper(this);

        Intent intent = getIntent();
        classId = intent.getStringExtra("CLASSID");

        cancelButton = findViewById(R.id.cancelButton);
        createStudentButton = findViewById(R.id.addStudentButton);
        datePickerButton = findViewById(R.id.studentDobButton);

        studentNameInput = findViewById(R.id.studentNameInput);
        studentDobInput = findViewById(R.id.studentDobInput);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentName = studentNameInput.getText().toString();
                String studentDob = studentDobInput.getText().toString();
                if (studentName.isEmpty() || studentDob.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this,"please fill all field",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddStudentActivity.this,"add student successfully",Toast.LENGTH_LONG).show();
                    studentHelper.addNew(new Student("1",studentName,studentDob,classId));
                    finish(); // Close the activity
                }

            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddStudentActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Set the selected date to the EditText
                        studentDobInput.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
}