package me.rothman.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter<City> {

    public CityAdapter(@NonNull Context context, ArrayList<City> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // if the recyclable view is null then inflate the custom layout for the same class
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_item, parent, false);
        }

        // Get the position of the view from the ArrayAdapter
        City currentCity = getItem(position);
        assert currentCity != null;

        // Update the city name
        TextView cityNameView = convertView.findViewById(R.id.city_name);
        cityNameView.setText(currentCity.getName());

        // Update selected city text
        TextView citySelectedView = convertView.findViewById(R.id.city_selected);
        String selectedText = "";
        if (currentCity.isSelected()) {
            selectedText = getContext().getString(R.string.selected);
        }
        citySelectedView.setText(selectedText);

        return convertView;
    }
}
