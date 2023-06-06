package org.example.service;

import org.example.model.DataDecodable;
import org.example.model.Drone;
import org.example.model.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CsvLoaderService {

    public static DataDecodable loadDataFromFile(String fileName) {

        var drones = new ArrayList<Drone>();
        var orders = new ArrayList<Order>();

        Path pathToFile = Paths.get(fileName);

        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            var lineNumber = 0;

            while (line != null) {
                if (lineNumber == 0) {
                    String[] dronesArray = line.split(",");
                    for (int i = 0; i < dronesArray.length;) {
                        drones.add(new Drone(dronesArray[i], Double.valueOf(dronesArray[i+1])));
                        i += 2;
                    }
                    lineNumber++;
                } else {
                    String[] ordersArray = line.split(",");
                    for (int i = 0; i < ordersArray.length;) {
                        orders.add(new Order(ordersArray[i], Double.valueOf(ordersArray[i+1])));
                        i += 2;
                    }
                    lineNumber++;
                }
                line = br.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        var data = new DataDecodable(drones, orders);

        return data;
    }
}
