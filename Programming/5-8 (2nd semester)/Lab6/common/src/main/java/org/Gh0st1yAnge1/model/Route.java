package org.Gh0st1yAnge1.model;

import org.Gh0st1yAnge1.utils.IdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Route implements Comparable<Route>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Integer id;
    private String name;
    private Coordinates coordinates;
    private final java.time.LocalDate creationDate = LocalDate.now();
    private Location from;
    private Location to;
    private float distance;

    public Route(String name, Coordinates coordinates, Location from, Location to, float distance){
        this.id = IdGenerator.generate();
        this.name = name;
        this.coordinates = coordinates;
        this.to = to;
        this.from = from;
        this.distance = distance;
    }

    @Override
    public int compareTo(Route other) {
        if (this.to == null && other.getTo() == null) return 0;
        if (this.to == null) return 1;
        if (other.getTo() == null) return -1;
        return this.to.compareTo(other.getTo());
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
            this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setFrom(Location from) {
            this.from = from;
    }
    public Location getFrom() {
        return from;
    }

    public void setTo(Location to) {
            this.to = to;
    }
    public Location getTo() {
        return to;
    }

    public void setDistance(float distance) {
            this.distance = distance;
    }
    public float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Route{\n" +
                "    id=" + id +
                ",\n    name='" + name + '\'' +
                ",\n    coordinates=" + coordinates +
                ",\n    creationDate=" + creationDate +
                ",\n    from=" + from +
                ",\n    to=" + to +
                ",\n    distance=" + distance + '\n' +
                '}';
    }

}