package me.rothman.listycity;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button deleteButton;
    private ListView cityList;
    private String selectedCity;
    private EditText cityField;
    private Button confirmButton;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> cityTextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
        * Locate the widgets from the current activity or fragment.
        * Provides a reference to manipulate the widgets programmatically after
        * the layout has been set.
        * */
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        cityList = findViewById(R.id.city_list);
        cityField = findViewById(R.id.city_field);
        confirmButton = findViewById(R.id.confirm_button);

        // Connect the event listeners for each widget
        addButton.setOnClickListener(this::onAddButtonClicked);
        confirmButton.setOnClickListener(this::onConfirmButtonClicked);
        cityList.setOnItemClickListener(this::onCitySelected);
        deleteButton.setOnClickListener(this::onDeleteButtonClicked);

        // Initially hide the city field and confirm button
        cityField.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        // Create a dynamic array for the list of cities, add some examples
        cityTextList = new ArrayList<>();
        cityTextList.add("Edmonton");
        cityTextList.add("Vancouver");
        selectedCity = null;

        // An ArrayAdapter is used to bind a list of strings to a ListView
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, cityTextList);
        cityList.setAdapter(cityAdapter);
    }

    private void onAddButtonClicked(View view) {
        // Show the city field and confirm buttons
        cityField.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    private void onConfirmButtonClicked(View view) {
        String newCity = cityField.getText().toString().trim();
        if (newCity.isEmpty() || newCity.isBlank()) {
            Toast.makeText(this, "Enter a valid city name!", Toast.LENGTH_SHORT).show();
        } else {
            // Add the new city to the text list
            cityTextList.add(newCity);

            // Notifier the adapter to update the ListView
            cityAdapter.notifyDataSetChanged();

            // Clear and hide the city entry field and confirm button
            cityField.setText("");
            cityField.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        }
    }

    private void onCitySelected(AdapterView<?> adapterView, View view, int index, long id) {
        // Mark the selected city for further processing
        selectedCity = cityTextList.get(index);
        Toast.makeText(this, "Selected: " + selectedCity, Toast.LENGTH_SHORT).show();
    }

    private void onDeleteButtonClicked(View view) {
        if (selectedCity == null) {
            Toast.makeText(this, "Select a city first!", Toast.LENGTH_SHORT).show();
        } else {
            // Delete the city
            cityTextList.remove(selectedCity);

            // Notifier the adapter to update the ListView
            cityAdapter.notifyDataSetChanged();

            // Clear the selected city selection
            selectedCity = null;
        }
    }
}
