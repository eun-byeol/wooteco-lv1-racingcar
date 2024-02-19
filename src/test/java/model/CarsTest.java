package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

class CarsTest {

    @DisplayName("자동차 이름이 중복되면 예외가 발생한다.")
    @Test
    void nameDuplicatedTest() {
        List<String> carNames = List.of("lemon", "lemon", "a", "nyang");
        assertThatThrownBy(() -> Cars.from(carNames))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자동차 이름 개수가 2개 미만이면 예외가 발생한다.")
    @Test
    void carNameCountTest() {
        List<String> carNames = List.of("lemon");
        assertThatThrownBy(() -> Cars.from(carNames))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
