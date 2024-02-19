package racingcar.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import racingcar.dto.RoundResult;
import racingcar.util.RefuelGenerator;

public class Cars {
    static final int MIN_CARS_SIZE = 2;
    static final int MAX_CARS_SIZE = 10;
    static final int MAX_RANDOM_NUMBER = 9;

    private final List<Car> cars;

    private Cars(final List<Car> cars) {
        validateNotDuplicate(cars);
        validateSize(cars);
        this.cars = cars;
    }

    public static Cars of(final List<String> carNames) {
        List<Car> cars = carNames.stream()
                .map(Car::from)
                .toList();
        return new Cars(cars);
    }

    private void validateNotDuplicate(final List<Car> cars) {
        long uniqueCarNames = cars.stream()
                .distinct()
                .count();
        if (cars.size() != uniqueCarNames) {
            throw new IllegalArgumentException("자동차 이름은 중복될 수 없습니다.");
        }
    }

    private void validateSize(final List<Car> cars) {
        if (cars.size() < MIN_CARS_SIZE || cars.size() > MAX_CARS_SIZE) {
            throw new IllegalArgumentException(
                    String.format("자동차 대수는 %d대 이상 %d대 이하여야 합니다.", MIN_CARS_SIZE, MAX_CARS_SIZE)
            );
        }
    }

    public void move(RefuelGenerator generator) {
        List<Integer> numbers = generator.generate(MAX_RANDOM_NUMBER, cars.size());
        IntStream.range(0, cars.size())
                        .forEach(i -> cars.get(i).move(numbers.get(i)));
    }

    public RoundResult result() {
        return cars.stream()
                .map(Car::getCarStatus)
                .collect(Collectors.collectingAndThen(Collectors.toList(), RoundResult::new));
    }

    public List<String> getWinners() {
        final Car highestPositionCar = getHighestPositionCar();
        return getSamePositionCars(highestPositionCar);
    }

    private Car getHighestPositionCar() {
        return cars.stream()
                .max(Car::compareTo)
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<String> getSamePositionCars(final Car highestPositionCar) {
        return cars.stream()
                .filter(highestPositionCar::isSamePosition)
                .map(Car::getName)
                .toList();
    }
}
