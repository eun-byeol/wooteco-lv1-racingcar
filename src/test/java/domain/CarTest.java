package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CarTest {

    static Stream<Arguments> moves() {
        return Stream.of(
                Arguments.of(true, 1, "true면 전진한다."),
                Arguments.of(false, 0, "false면 전진하지 않는다.")
        );
    }

    @ParameterizedTest(name = "{2}")
    @DisplayName("true면 전진하고, false면 전진하지 않는다.")
    @MethodSource("moves")
    void move(boolean doMove, int expected, String title) {
        Car car = new Car("배키");
        car.move(doMove);
        int result = car.getMovement();

        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> invalidCarName() {
        return Stream.of(
                Arguments.of(" ", "차 이름이 공백이다.", "빈 자동차 이름이 존재합니다."),
                Arguments.of("", "차 이름이 빈 문자열이다.", "빈 자동차 이름이 존재합니다."),
                Arguments.of("명오배키켬미", "차 이름이 5자를 초과한다.", "자동차 이름이 5자 초과입니다.")
        );
    }

    @ParameterizedTest(name = "{1}")
    @DisplayName("자동차 이름 입력 시 아래 경우에 예외가 발생한다")
    @MethodSource("invalidCarName")
    public void invalidCarNameThrowException(String carName, String reason, String errorMessage) {
        assertThatThrownBy(() -> new Car(carName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorMessage);
    }

    @ParameterizedTest()
    @DisplayName("유효한 자동차 이름 입력 시 예외가 발생하지 않는다.")
    @ValueSource(strings = {"명오", "가나다라마", "myung"})
    public void validCarNameDoesNotThrowException(String carName) {
        assertThatCode(() -> new Car(carName))
                .doesNotThrowAnyException();
    }
}
