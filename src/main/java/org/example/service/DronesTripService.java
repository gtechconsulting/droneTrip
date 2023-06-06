package org.example.service;

import org.example.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class DronesTripService {

    static HashMap trips = new HashMap<String, List<DronesTrip>>();

    public static void generateBestTrip(DataDecodable data) {

        var drones = data.drones();
        var orders = data.orders();

        while(orders.size() > 0) {
            drones.forEach(drone -> {
                List<DronesTrip> dronesTripList = createdScheduledTripsForDrone(drone);

                var lastDroneTrip = dronesTripList.get(dronesTripList.size()-1);

                lastDroneTrip = pickOrderForCapacity(drone, orders, lastDroneTrip);
                dronesTripList.set(dronesTripList.size()-1, lastDroneTrip);
                trips.put(drone.name(), dronesTripList);
            });
        }

        trips.forEach((key, value) -> {
            System.out.println("[ Dron #" + key +"]");
            ((List<DronesTrip>) value).forEach(dronesTrip -> {
                System.out.println("[ Trip # ]");
                dronesTrip.trips().forEach(trip -> {
                    System.out.print("[ Location #" + trip.locationName() +"], ");
                });
                System.out.println("");
            });
        });
    }

    private static List<DronesTrip> createdScheduledTripsForDrone(Drone drone){
        List<DronesTrip> dronesTripList = (List<DronesTrip>) trips.get(drone.name());

        if(dronesTripList == null || dronesTripList.size() == 0) {
            dronesTripList = new ArrayList<>();
        }

        dronesTripList.add(new DronesTrip(dronesTripList != null ?dronesTripList.size() + 1 : 1, new ArrayList<>()));
        trips.put(drone.name(), dronesTripList);

        return dronesTripList;
    }

    private static DronesTrip pickOrderForCapacity(Drone drone, List<Order> orders, DronesTrip dronesTrip){

        var remainingCapacity = drone.maxWeight();

        for(int i = 0 ; i < orders.size(); i++) {
            var order = orders.get(i);
            remainingCapacity = remainingCapacity - order.weight();

            if(order.weight() <= remainingCapacity) {
                dronesTrip.trips().add(new Trip(order.location()));
                orders.remove(order);
            } else {
                break;
            }
        }

        return dronesTrip;
    }

}
