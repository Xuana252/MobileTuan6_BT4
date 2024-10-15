package com.example.bt4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bt4.adapter.MyClassAdapter;
import com.example.bt4.data.MyClass;
import com.example.bt4.data.Student;
import com.example.bt4.database.helper.ClassHelper;

import java.util.ArrayList;
import java.util.List;

public class SecondaryActivity extends AppCompatActivity {


    private List<MyClass> classList = new ArrayList<>();

    private ClassHelper classHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        classHelper = new ClassHelper(this);

        try {

            classList = classHelper.loadAll();


            ListView classListview = (ListView) findViewById(R.id.classListView);

            MyClassAdapter arrayAdapter = new MyClassAdapter(SecondaryActivity.this, R.layout.class_item, classList);


            classListview.setAdapter(arrayAdapter);


            classListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyClass selectedClass = classList.get(position); // Get the selected class


                    Intent intent = new Intent(SecondaryActivity.this, ThirdActivity.class);
                    intent.putExtra("CLASS",selectedClass);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(SecondaryActivity.this, AddClassActivity.class);// Pass the class ID
            startActivity(intent);
            return true;
        } else if (itemId == R.id.Delete) {
            List<MyClass> classesToRemove = new ArrayList<>();
            for (MyClass myClass : classList) {
                if (myClass.isChecked()) {
                    classesToRemove.add(myClass);
                    classHelper.delete(myClass.getClassID());
                }
            }
            classList.removeAll(classesToRemove);

            // Update the ListView and student count
            ((MyClassAdapter) ((ListView) findViewById(R.id.classListView)).getAdapter()).notifyDataSetChanged();
            return true;
        } else if (itemId == R.id.Edit) {
            for (MyClass myClass : classList) {
                if (myClass.isChecked()) {
                    Intent intent = new Intent(SecondaryActivity.this,EditClassActivity.class);
                    intent.putExtra("CLASS",myClass);
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
        classList.clear();

        // Reload the class list from the database
        classList.addAll(classHelper.loadAll());

        // Notify the adapter about the data change
        ((MyClassAdapter) ((ListView) findViewById(R.id.classListView)).getAdapter()).notifyDataSetChanged();
    }

}