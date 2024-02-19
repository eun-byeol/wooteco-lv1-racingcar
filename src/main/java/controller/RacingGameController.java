package controller;

import domain.Cars;
import domain.Count;
import domain.RaceResult;
import domain.RacingGame;
import domain.RandomMovementGenerator;
import domain.RandomNumberGenerator;
import dto.CarNameRequest;
import dto.CountRequest;
import java.util.function.Function;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class RacingGameController {
    private final InputView inputView;
    private final OutputView outputView;

    public RacingGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CarNameRequest carsNameRequest = retryUntilNoException(inputView::readCars);
        CountRequest countRequest = retryUntilNoException(inputView::readCount);
        Count count = countRequest.toCount();
        Cars cars = retryUntilNoException(Cars::from, carsNameRequest.asList());
        RacingGame racingGame = RacingGame.of(count, cars, new RandomMovementGenerator(new RandomNumberGenerator()));
        RaceResult raceResult = racingGame.race();
        outputView.showStatusMessage();
        outputView.showStatus(raceResult);
        outputView.showResult(raceResult.getWinners());
    }

    private <T> T retryUntilNoException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return retryUntilNoException(supplier);
        }
    }

    private <T, R> R retryUntilNoException(Function<T, R> function, T input) {
        try {
            return function.apply(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return retryUntilNoException(function, input);
        }
    }
}
