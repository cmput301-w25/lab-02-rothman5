package me.rothman.listycity;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button deleteButton;
    private ListView cityListView;
    private EditText cityField;
    private Button confirmButton;

    private City selectedCity;
    private View selectedView;
    private ArrayList<City> cityList;
    private ArrayAdapter<City> cityAdapter;

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
        cityListView = findViewById(R.id.city_list);
        cityField = findViewById(R.id.city_field);
        confirmButton = findViewById(R.id.confirm_button);

        // Connect the event listeners for each widget
        addButton.setOnClickListener(this::onAddButtonClicked);
        confirmButton.setOnClickListener(this::onConfirmButtonClicked);
        cityListView.setOnItemClickListener(this::onCitySelected);
        deleteButton.setOnClickListener(this::onDeleteButtonClicked);

        // Initially hide the city field and confirm button
        cityField.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        // Create a list for the cities, add some examples
        cityList = new ArrayList<>();
        cityList.add(new City("Edmonton"));
        cityList.add(new City("Vancouver"));

        // Initially there's no selected city
        selectedCity = null;
        selectedView = null;

        // An ArrayAdapter is used to bind a list of strings to a ListView
        cityAdapter = new CityAdapter(this, cityList);
        cityListView.setAdapter(cityAdapter);
    }

    private void onAddButtonClicked(View view) {
        showCityFieldAndConfirmButton();

        if (selectedView != null) {
            // Deselect
            selectedView.setBackgroundColor(getColor(R.color.off_white));
            selectedCity.setSelected(false);
            TextView citySelectedView = selectedView.findViewById(R.id.city_selected);
            citySelectedView.setText("");
            selectedView = null;
        }
    }

    private void onConfirmButtonClicked(View view) {
        // Get the entered city from the field
        String newCityName = cityField.getText().toString().trim();

        if (newCityName.isEmpty() || newCityName.isBlank()) {
            Toast.makeText(this, "Enter a valid city name!", Toast.LENGTH_SHORT).show();
        } else {
            for (City city : cityList) {
                if (city.getName().equals(newCityName)) {
                    Toast.makeText(this, "Enter a unique city name!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Add the new city to the text list
            cityList.add(new City(newCityName));

            // Notifier the adapter to update the ListView
            cityAdapter.notifyDataSetChanged();

            hideCityFieldAndConfirmButton();
        }
    }

    private void onCitySelected(AdapterView<?> adapterView, View view, int index, long id) {
        // Mark the selected city for further processing
        City prevSelectedCity = selectedCity;
        selectedCity = cityList.get(index);

        String selectedText;
        if (selectedCity.isSelected()) {
            // Deselect
            selectedView = null;
            view.setBackgroundColor(getColor(R.color.off_white));
            selectedText = "";
            selectedCity.setSelected(false);
        } else {
            hideCityFieldAndConfirmButton();

            if (selectedView != null) {
                // Deselect previous item
                selectedView.setBackgroundColor(getColor(R.color.off_white));
                prevSelectedCity.setSelected(false);
                TextView prevCitySelectedView = selectedView.findViewById(R.id.city_selected);
                prevCitySelectedView.setText("");
            }

            // Select
            selectedView = view;
            view.setBackgroundColor(getColor(R.color.selected_color));
            selectedText = getString(R.string.selected);
            selectedCity.setSelected(true);
        }

        TextView citySelectedView = view.findViewById(R.id.city_selected);
        citySelectedView.setText(selectedText);
    }

    private void onDeleteButtonClicked(View view) {
        if (selectedCity == null) {
            Toast.makeText(this, "Select a city first!", Toast.LENGTH_SHORT).show();
        } else {
            // Delete the city
            cityList.remove(selectedCity);

            // Notifier the adapter to update the ListView
            cityAdapter.notifyDataSetChanged();

            // Deselect
            selectedView.setBackgroundColor(getColor(R.color.off_white));
            selectedCity.setSelected(false);
            TextView citySelectedView = selectedView.findViewById(R.id.city_selected);
            citySelectedView.setText("");

            // Clear the selected city selection
            selectedCity = null;
            selectedView = null;
        }
    }

    private void showCityFieldAndConfirmButton() {
        // Show the city field and confirm buttons
        cityField.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    private void hideCityFieldAndConfirmButton() {
        // Clear and hide the city entry field and confirm button
        cityField.setText("");
        cityField.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);
    }
}
