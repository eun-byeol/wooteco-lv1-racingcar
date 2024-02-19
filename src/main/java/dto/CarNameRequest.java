package dto;

import java.util.Arrays;
import java.util.List;

public record CarNameRequest(String cars) {

    public CarNameRequest {
        validateCars(cars);
    }

    public static final String DELIMITER = ",";

    public List<String> asList() {
        return Arrays.asList(cars.split(DELIMITER));
    }

    public void validateCars(String input) {
        validateBlank(input);
        validateDuplicatedDelimiter(input);
        validateStartWord(input);
        validateEndWord(input);
    }

    private void validateEndWord(String input) {
        if (input.endsWith(DELIMITER)) {
            throw new IllegalArgumentException(String.format("입력된 값: %s, 구분자로 끝날 수 없습니다.", input));
        }
    }

    private void validateStartWord(String input) {
        if (input.startsWith(DELIMITER)) {
            throw new IllegalArgumentException(String.format("입력된 값: %s, 구분자로 시작할 수 없습니다.", input));
        }
    }

    private void validateDuplicatedDelimiter(String input) {
        if (input.contains(DELIMITER.repeat(2))) {
            throw new IllegalArgumentException(String.format("입력된 값: %s, 올바른 형식으로 입력해주세요.", input));
        }
    }

    private void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(String.format("입력된 값: %s, 공백을 입력할 수 없습니다.", input));
        }
    }
}
