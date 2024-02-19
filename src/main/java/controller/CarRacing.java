package controller;

import common.exception.message.ExceptionMessage;
import common.exception.model.ValidateException;
import domain.Car;
import domain.Cars;
import domain.TryCount;
import domain.accelerator.Accelerator;
import domain.accelerator.strategy.RandomMoveAccelerator;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.List;


public class CarRacing {
    private final InputView inputView;
    private final OutputView outputView;

    public CarRacing(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Cars start() {
        Cars cars = createCars(new RandomMoveAccelerator());
        TryCount tryCount = createTryCount();

        printMoveResult(tryCount, cars);
        return cars;
    }

    public void announceWinners(Cars cars) {
        outputView.printWinners(getWinners(cars));
    }

    private void printMoveResult(TryCount tryCount, Cars cars) {
        outputView.printMoveResultMessage();
        tryMove(tryCount, cars);
    }

    private Cars createCars(Accelerator accelerator) {
        try {
            String input = inputView.readCarNames();
            validateCarNamesInput(input);

            List<String> carNames = Arrays.asList(input.split(InputView.CAR_NAMES_DELIMITER));
            List<Car> cars = carNames.stream()
                    .map(carName -> new Car(carName, accelerator))
                    .toList();

            return new Cars(cars);
        } catch (ValidateException exception) {
            outputView.printErrorMessage(exception);
            return createCars(accelerator);
        }
    }

    private void validateCarNamesInput(String carNames) {
        if (!InputView.CAR_NAMES_PATTERN.matcher(carNames).matches()) {
            throw new ValidateException(ExceptionMessage.CAR_NAME_PATTERN_ERROR_MESSAGE);
        }
    }

    private TryCount createTryCount() {
        try {
            String input = inputView.readTryAmount();
            validateTryCountInput(input);

            return new TryCount(Integer.parseInt(input));
        } catch (ValidateException exception) {
            outputView.printErrorMessage(exception);
            return createTryCount();
        }
    }

    private void validateTryCountInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new ValidateException(ExceptionMessage.INT_FORMAT_ERROR_MESSAGE);
        }
    }

    private void tryMove(TryCount tryCount, Cars cars) {
        for (int i = 0; i < tryCount.getValue(); i++) {
            cars.tryMove();
            outputView.printCarsPosition(cars.getCars());
        }
    }

    private List<String> getWinners(Cars cars) {
        int winnerPosition = cars.getWinnerPosition();

        return cars.getCars().stream()
                .filter(car -> car.getPosition() == winnerPosition)
                .map(Car::getName)
                .toList();
    }
}
