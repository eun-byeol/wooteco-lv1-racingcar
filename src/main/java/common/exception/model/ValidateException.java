package common.exception.model;

import common.exception.CustomException;

public class ValidateException extends CustomException {
    public ValidateException(String message) {
        super(message);
    }
}
