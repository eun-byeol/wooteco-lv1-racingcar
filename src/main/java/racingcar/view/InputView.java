package racingcar.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public List<String> readCarNames() {
        System.out.println("경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).");
        String input = scanner.nextLine();
        validateInput(input);
        return Arrays.stream(input.split(","))
                .toList();
    }

    public int readNumberOfRaces() {
        System.out.println("시도할 회수는 몇회인가요?");
        String input = scanner.nextLine();
        return parseInt(input);
    }

    public void finishReadingInput() {
        scanner.close();
    }

    private static void validateInput(String input) {
        if (input == null || input.isEmpty() || input.endsWith(",")) {
            throw new IllegalArgumentException();
        }
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
