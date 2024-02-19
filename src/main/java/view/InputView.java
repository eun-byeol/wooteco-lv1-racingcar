package view;

import dto.CarNameRequest;
import dto.CountRequest;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {
    Scanner scanner = new Scanner(System.in);
    public CarNameRequest readCars() {
        System.out.println("경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).");
        String input = scanner.nextLine().trim();
        return new CarNameRequest(input);
    }

    public CountRequest readCount() {
        System.out.println("시도할 회수는 몇회인가요?");
        String input = scanner.nextLine().trim();
        return new CountRequest(input);
    }
}
