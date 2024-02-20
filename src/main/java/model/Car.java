package model;

public class Car {

    private static final int INITIAL_POSITION = 0;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 5;
    private static final int FORWARD_MIN_NUMBER = 4;

    private final String name;
    private int position;

    private Car(final String name) {
        this.position = INITIAL_POSITION;
        this.name = name;
    }

    public static Car from(final String name) {
        validateNameLength(name);
        return new Car(name);
    }

    private static void validateNameLength(String name) {
        if (name == null || name.trim().isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalStateException(
                    String.format("[ERROR] 자동차 이름은 %d 글자 이상 %d 글자 이하여야 합니다.",
                            MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    public void moveForward(int power) {
        if (power >= FORWARD_MIN_NUMBER) {
            this.position++;
        }
    }

    public int getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }
}
