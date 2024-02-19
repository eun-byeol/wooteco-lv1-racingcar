package domain;

import common.exception.model.ValidateException;
import domain.accelerator.strategy.MoveAccelerator;
import domain.accelerator.strategy.NoneMoveAccelerator;
import domain.accelerator.strategy.RandomMoveAccelerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarTest {

    @ParameterizedTest
    @ValueSource(strings = {"p", "po", "poo", "pooo", "poooo"})
    @DisplayName("자동차 이름의 길이가 1 이상 5 이하로 주어지면 자동차가 정상적으로 생성된다")
    void createCarSuccess(String carName) {
        Assertions.assertThatCode(() -> new Car(carName, new RandomMoveAccelerator()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"pooobi"})
    @DisplayName("자동차 이름의 길이가 1 미만 5 초과로 주어지면 자동차가 정상적으로 생성되지 않는다")
    void createCarFail(String carName) {
        assertThatThrownBy(() -> new Car(carName, new RandomMoveAccelerator()))
                .isInstanceOf(ValidateException.class)
                .hasMessage(CarName.CAR_NAME_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("자동차 이름이 null로 주어지면 자동차가 정상적으로 생성되지 않는다")
    void createCarFailWhenInputNull() {
        assertThatThrownBy(() -> new Car(null, new RandomMoveAccelerator()))
                .isInstanceOf(ValidateException.class)
                .hasMessage(CarName.CAR_NAME_DOES_NOT_EXIST_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   "})
    @DisplayName("자동차 이름이 blank로 주어지면 자동차가 정상적으로 생성되지 않는다")
    void createCarFailWhenInputBlank(String carName) {
        assertThatThrownBy(() -> new Car(carName, new RandomMoveAccelerator()))
                .isInstanceOf(ValidateException.class)
                .hasMessage(CarName.CAR_NAME_DOES_NOT_EXIST_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("4 이상 9 이하의 값을 받으면 자동차가 이동한다")
    void moveCar() {
        //given
        Car car = new Car("pobi", new MoveAccelerator());
        //when
        car.pushAccelerator();
        //then
        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    @DisplayName("4 미만의 값을 받으면 자동차가 이동하지 않는다")
    void doNotMoveCar() {
        //given
        Car car = new Car("pobi", new NoneMoveAccelerator());
        //when
        car.pushAccelerator();
        //then
        assertThat(car.getPosition()).isEqualTo(0);
    }
}