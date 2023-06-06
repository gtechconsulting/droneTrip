package org.example.model;

import java.util.List;

public record DataDecodable(List<Drone> drones, List<Order> orders) {
}
