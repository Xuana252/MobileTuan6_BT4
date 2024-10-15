package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bt4.adapter.MyClassAdapter;
import com.example.bt4.adapter.StudentAdapter;
import com.example.bt4.data.MyClass;
import com.example.bt4.data.Student;
import com.example.bt4.database.helper.StudentHelper;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    TextView classStudentCount;
    TextView className;

    MyClass selectedClass;

    private List<Student> studentList = new ArrayList<>();

    private StudentHelper studentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        studentHelper = new StudentHelper(this);


        Intent intent = getIntent();
        selectedClass = (MyClass) intent.getSerializableExtra("CLASS");



        if (selectedClass != null) {
            try {

                studentList = studentHelper.loadClass(selectedClass.getClassID());


                ListView studentListView = (ListView) findViewById(R.id.studentListView);
                classStudentCount = findViewById(R.id.classStudentCount);
                className = findViewById(R.id.className);


                className.setText("Lớp :" + selectedClass.getClassName());
                classStudentCount.setText("Sỉ số:  " + studentList.size());

                StudentAdapter arrayAdapter;
                arrayAdapter = new StudentAdapter(getApplicationContext(), R.layout.student_item, studentList);


                studentListView.setAdapter(arrayAdapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }



    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.Add) {
            Intent intent = new Intent(ThirdActivity.this, AddStudentActivity.class);
            intent.putExtra("CLASSID",selectedClass.getClassID());
            startActivity(intent);
            return  true;
        } else if (itemId == R.id.Delete) {
            List<Student> studentsToRemove = new ArrayList<>();
            for (Student student : studentList) {
                if (student.isChecked()) {
                    studentsToRemove.add(student);
                    studentHelper.delete(student.getID());
                }
            }
            studentList.removeAll(studentsToRemove);

            // Update the ListView and student count
            ((StudentAdapter) ((ListView) findViewById(R.id.studentListView)).getAdapter()).notifyDataSetChanged();
            classStudentCount.setText("Sỉ số: " + studentList.size());
            return true;
        } else if (itemId == R.id.Edit) {
            for (Student student : studentList) {
                if (student.isChecked()) {
                    Intent intent = new Intent(ThirdActivity.this,EditStudentActivity.class);
                    intent.putExtra("STUDENT",student);
                    startActivity(intent);
                    break;
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadClassList();
    }

    private void reloadClassList() {
        // Clear the existing list to avoid duplicates
        studentList.clear();

        // Reload the class list from the database
        studentList.addAll(studentHelper.loadClass(selectedClass.getClassID()));

        // Notify the adapter about the data change
        ((StudentAdapter) ((ListView) findViewById(R.id.studentListView)).getAdapter()).notifyDataSetChanged();
    }




}