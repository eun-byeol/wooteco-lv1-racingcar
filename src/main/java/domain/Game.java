package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
    public final int MAX_NUM = 9;
    public final int THRESHOLD = 4;

    public Game() {
    }

    public int setAttemptLimit(String attemptLimit) {
        return validateNumber(validateInputAttemptLimit(attemptLimit));
    }

    private int validateInputAttemptLimit(String attemptLimit) {
        try {
            return Integer.parseInt(attemptLimit);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 시도 횟수는 1 이상의 정수여야 합니다.");
        }
    }

    private int validateNumber(int inputAttemptLimit) {
        if (inputAttemptLimit <= 0) {
            throw new IllegalArgumentException("[ERROR] 시도 횟수는 양수여야 합니다.");
        }
        return inputAttemptLimit;
    }

    public List<Car> setCars(String carNames) {
        List<Car> cars = new ArrayList<>();
        for (String carName : separateCarName(carNames)) {
            cars.add(new Car(carName));
        }
        validateCar(cars);
        return cars;
    }

    private List<String> separateCarName(String carNames) {
        return List.of(carNames.split(","));
    }

    private void validateCar(List<Car> cars) {
        validateCarAmount(cars);
        validateDuplicateName(cars);
    }

    private void validateDuplicateName(List<Car> cars) {
        int duplication = 0;
        Set<String> validateCar = new HashSet<>();
        for (Car car : cars) {
            duplication = validateDuplication(car.getCarName(), validateCar);
        }

        if (cars.size() != duplication) {
            throw new IllegalArgumentException("[ERROR] 중복된 이름이 존재합니다.");
        }
    }

    private int validateDuplication(String carName, Set<String> validateCar) {
        validateCar.add(carName);
        return validateCar.size();
    }

    private void validateCarAmount(List<Car> cars) {
        if (cars.size() == 1) {
            throw new IllegalArgumentException("[ERROR] 경주할 자동차를 두 대 이상 입력해주세요.");
        }
    }

    public void getWinner(List<Car> cars) {
        for (Car car : cars) {
            findWinner(car, getMaxPosition(cars));
        }
    }

    private int getMaxPosition(List<Car> cars) {
        int max = -1;
        for (Car car : cars) {
            max = compareValue(car, max);
        }
        return max;
    }

    private void findWinner(Car car, int maxPosition) {
        if (car.isSamePosition(maxPosition)) {
            car.setWinner();
        }
    }

    private int compareValue(Car car, int max) {
        if (max <= car.getLocation()) {
            max = car.getLocation();
        }
        return max;
    }

    public void playRacing(List<Car> cars) {
        for (Car car : cars) {
            goOrStop(car, randomNumberGenerator());
        }
    }

    private int randomNumberGenerator() {
        Random random = new Random();
        return random.nextInt(MAX_NUM);
    }

    private void goOrStop(Car car, int randomNumber) {
        if (randomNumber >= THRESHOLD) {
            car.incLocation();
        }
    }
}
