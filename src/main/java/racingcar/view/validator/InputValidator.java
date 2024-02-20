package racingcar.view.validator;

import static racingcar.message.ErrorMessage.ERROR_WITH_CONTAIN_BLANK;
import static racingcar.message.ErrorMessage.ERROR_WITH_DUPLICATE_NAME;
import static racingcar.message.ErrorMessage.ERROR_WITH_INPUT_BLANK;
import static racingcar.message.ErrorMessage.ERROR_WITH_NON_NUMERIC;
import static racingcar.message.ErrorMessage.ERROR_WITH_ONLY_ONE_CAR;
import static racingcar.message.ErrorMessage.ERROR_WITH_OUT_OF_INTEGER_RANGE;
import static racingcar.message.ErrorMessage.ERROR_WITH_OVER_LENGTH;
import static racingcar.message.ErrorMessage.ERROR_WITH_UNDER_MINIMUM;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputValidator {
    private static final int MIN_CAR_COUNT = 2;
    private static final int MIN_TRY_NUMBER = 1;
    
    public static void validateAvailableNames(List<String> names){
        validateIsMultipleCarNames(names);
        validateIsDuplicate(names);
    }

    public static void validateIsBlank(String input) {
        if(input.isBlank()) {
            throw new IllegalArgumentException(ERROR_WITH_INPUT_BLANK);
        }
    }


    private static void validateIsMultipleCarNames(List<String> names) {
        if (names.size() < MIN_CAR_COUNT) {
            throw new IllegalArgumentException(ERROR_WITH_ONLY_ONE_CAR);
        }
    }

    private static void validateIsDuplicate(List<String> names) {
        Set<String> nameSet = new HashSet<>(names);

        if(nameSet.size() != names.size()) {
            throw new IllegalArgumentException(ERROR_WITH_DUPLICATE_NAME);
        }
    }

    public static void validateTryNumber(String tryNumber) {
        validateIsNumeric(tryNumber);
        validateIsInteger(tryNumber);
        validateIsOverMinimum(tryNumber);
    }

    private static void validateIsNumeric(String tryNumber) {
        if(!tryNumber.matches("\\d+")) {
            throw new IllegalArgumentException(ERROR_WITH_NON_NUMERIC);
        }
    }

    private static void validateIsInteger(String tryNumber) {
        try {
            Integer.parseInt(tryNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_WITH_OUT_OF_INTEGER_RANGE);
        }
    }

    private static void validateIsOverMinimum(String tryNumber) {
        if(Integer.parseInt(tryNumber) < MIN_TRY_NUMBER) {
            throw new IllegalArgumentException(ERROR_WITH_UNDER_MINIMUM);
        }
    }
}
