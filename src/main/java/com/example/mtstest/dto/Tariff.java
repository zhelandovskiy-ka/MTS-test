package com.example.mtstest.dto;

public class Tariff {
    private String name;
    private String options;
    private String description;
    private double price;

    public Tariff(String name, String options, String description, double price) {
        this.name = name;
        this.options = options;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getOptions() {
        return options;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
