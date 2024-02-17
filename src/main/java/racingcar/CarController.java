package racingcar;

import java.util.List;
import java.util.function.Supplier;
import racingcar.domain.Car;
import racingcar.domain.CarFactory;
import racingcar.domain.CarStatus;
import racingcar.domain.Circuit;
import racingcar.exception.RacingCarException;
import racingcar.random.NumberGenerator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class CarController {

    private final InputView inputView;
    private final OutputView outputView;
    private final NumberGenerator numberGenerator;

    public CarController(InputView inputView, OutputView outputView, NumberGenerator numberGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.numberGenerator = numberGenerator;
    }

    public void run() {
        List<String> carNames = repeatUntilValid(inputView::getNames);
        int tryNumber = repeatUntilValid(inputView::getTryNumber);

        CarFactory carFactory = new CarFactory(numberGenerator);
        List<Car> cars = carFactory.createCars(carNames);
        Circuit circuit = new Circuit(cars);

        play(circuit, tryNumber);
        reportWinners(circuit);
    }

    private void reportWinners(Circuit circuit) {
        List<CarStatus> raceResults = circuit.getRaceResults();
        List<CarStatus> winners = circuit.getWinners(raceResults);
        outputView.printWinners(winners);
    }

    private void play(Circuit circuit, int tryNumber) {
        outputView.printResultMessage();
        for (int tries = 0; tries < tryNumber; tries++) {
            circuit.startRace();
            List<CarStatus> raceResults = circuit.getRaceResults();
            outputView.printResults(raceResults);
        }
    }

    private <T> T repeatUntilValid(Supplier<T> function) {
        try {
            return function.get();
        } catch (RacingCarException e) {
            outputView.printErrorMessage(e.getMessage());
            return repeatUntilValid(function);
        }
    }

}
