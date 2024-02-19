package model;

public class Car {

    private static final int INITIAL_POSITION = 0;
    private static final int MAX_NAME_LENGTH = 5;
    private static final int FORWARD_MIN_NUMBER = 4;
    private static final int STEP = 1;

    private final String name;
    private int position;

    private Car(String name) {
        this.name = name;
        this.position = INITIAL_POSITION;
    }

    public static Car from(String name) {
        validateNameLength(name);
        return new Car(name);
    }

    private static void validateNameLength(String name) {
        if (name == null || name.length() > MAX_NAME_LENGTH || name.trim().isEmpty()) {
            throw new IllegalStateException("자동차 이름은 한 글자 이상 다섯 글자 이하여야 합니다.");
        }
    }

    public void moveForward(int number) {
        if (number >= FORWARD_MIN_NUMBER) {
            position += STEP;
        }
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
