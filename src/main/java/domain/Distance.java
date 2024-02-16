package domain;

import java.util.Objects;

public class Distance implements Comparable<Distance> {
    private int distance;

    private Distance(int distance) {
        this.distance = distance;
    }

    public static Distance from(int distance) {
        return new Distance(distance);
    }

    public static Distance init() {
        return new Distance(0);
    }

    public void increase() {
        this.distance++;
    }

    public boolean isSameDistance(Distance maxDistance) {
        return this.distance == maxDistance.distance;
    }

    @Override
    public int compareTo(Distance other) {
        return this.distance - other.distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Distance distance1 = (Distance) o;
        return distance == distance1.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance);
    }

    public int getDistance() {
        return distance;
    }
}