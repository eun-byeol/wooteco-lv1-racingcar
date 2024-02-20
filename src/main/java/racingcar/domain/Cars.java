package racingcar.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cars {
    private final List<Car> cars;

    public Cars(final List<Car> cars) {
        validate(cars);

        this.cars = cars;
    }

    private void validate(final List<Car> cars) {
        validateCarSize(cars);
        validateCarNameDuplication(cars);
    }

    private void validateCarSize(final List<Car> cars) {
        if (cars.size() <= 1) {
            throw new IllegalArgumentException("자동차 경주를 위해선 2대 이상의 자동차가 필요합니다.");
        }
    }

    private void validateCarNameDuplication(final List<Car> cars) {
        final int actualCarAmount = cars.size();
        final long distinctCarAmount = cars.stream()
                .map(Car::getName)
                .distinct()
                .count();
        if (actualCarAmount != distinctCarAmount) {
            throw new IllegalArgumentException("자동차의 이름은 중복될 수 없습니다.");
        }
    }

    public void tryMove(final Accelerator accelerator) {
        for (Car car : cars) {
            car.moveForward(accelerator);
        }
    }

    public List<Car> getCars() {
        return cars;
    }

    public Map<String, Integer> getCarsPosition() {
        final Map<String, Integer> carsPosition = new HashMap<>();

        for (Car car : cars) {
            carsPosition.put(car.getName(), car.getPosition());
        }

        return carsPosition;
    }
}
