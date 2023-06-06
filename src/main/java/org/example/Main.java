package org.example;

import org.example.model.DataDecodable;
import org.example.service.CsvLoaderService;
import org.example.service.DronesTripService;

public class Main {
    public static void main(String[] args) {
        DataDecodable data = CsvLoaderService.loadDataFromFile("src/main/resources/data.csv");

        DronesTripService.generateBestTrip(data);
    }
}