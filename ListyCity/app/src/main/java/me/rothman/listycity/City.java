package me.rothman.listycity;

import java.util.Objects;

public class City {
    private boolean selected;
    private String name;

    public City(String name) {
        this.name = name;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        final City other = (City) obj;
        return Objects.equals(name, other.name) && Objects.equals(selected, other.isSelected());
    }
}
