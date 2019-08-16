package com.扑克牌;

import java.util.Objects;

public class card {
    private String color;
    private String id;

    public card() {
    }

    public card(String color, String id) {
        this.color = color;
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return color + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        card card = (card) o;
        return Objects.equals(color, card.color) &&
                Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(color, id);
    }
}
