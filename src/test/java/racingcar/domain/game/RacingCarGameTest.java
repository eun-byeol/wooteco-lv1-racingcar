package racingcar.domain.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.domain.car.Car;
import racingcar.domain.car.Cars;
import racingcar.mock.MockFixedNumberGenerator;
import racingcar.mock.MockFixedWinnerJudge;
import racingcar.mock.MoveMethodCalledCountStoreCars;
import racingcar.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("RacingCarGame 는 ")
class RacingCarGameTest {

    private static final int ON_GORING_NUMBER = 5;
    private final NumberGenerator numberGenerator = new MockFixedNumberGenerator(ON_GORING_NUMBER);
    private final WinnerJudge winnerJudge = new WinnerJudge();
    final String carName1 = "말랑";
    final String carName2 = "헤나";
    final String carName3 = "카일";
    final List<String> carNames = List.of(carName1, carName2, carName3);
    final Cars cars = new Cars(carNames);
    final Lap tenLap = Lap.totalLap(10);
    final Lap oneLap = Lap.totalLap(1);

    @Nested
    @DisplayName("성공 테스트")
    class SuccessTest {

        @DisplayName("race() 시 자동차들의 전진을 시도하고 Lap을 1 증가시킨다.")
        @Test
        void race_success_1() throws NoSuchFieldException, IllegalAccessException {
            // given
            MoveMethodCalledCountStoreCars moveMethodCalledCountStoreCars = new MoveMethodCalledCountStoreCars(carNames);
            RacingCarGame game = RacingCarGame.init(numberGenerator, winnerJudge, moveMethodCalledCountStoreCars, tenLap);

            // when
            game.race();

            // when & then
            Lap currentLap = ReflectionTestUtils.getDeclaredField(game, "currentLap", Lap.class);
            assertThat(moveMethodCalledCountStoreCars.getCallCount()).isEqualTo(1);
            assertThat(currentLap.value()).isEqualTo(1);
        }

        @DisplayName("gameResult() 시 경주가 끝났다면 우승자를 반환한다.")
        @Test
        void gameResult_success_1() {
            // given
            List<Car> winners = List.of(new Car(carName1), new Car(carName2));
            List<String> winnerNames = winners.stream()
                    .map(it -> it.getName().getValue())
                    .collect(Collectors.toList());
            RacingCarGame tenLapGame = RacingCarGame.init(numberGenerator, MockFixedWinnerJudge.withWinners(winners), cars, tenLap);
            while (tenLapGame.hasMoreLap()) {
                tenLapGame.race();
            }

            // when
            GameResult gameResult = tenLapGame.gameResult();

            // then
            assertThat(gameResult.winnerNames())
                    .containsExactlyInAnyOrderElementsOf(winnerNames);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailTest {

        @Test
        @DisplayName("race() 시 남은 바퀴수가 없는 경우 예외가 발생한다.")
        void race_fail_1() {
            // given
            RacingCarGame oneLapGame = RacingCarGame.init(numberGenerator, winnerJudge, cars, oneLap);
            oneLapGame.race();

            // when & then
            assertThrows(IllegalStateException.class, oneLapGame::race);
        }

        @DisplayName("gameResult() 시 경주가 끝나지 않았다면 예외가 발생한다.")
        @Test
        void gameResult_fail_1() {
            // given
            RacingCarGame tenLapGame = RacingCarGame.init(numberGenerator, winnerJudge, cars, tenLap);

            // when & then
            assertThrows(IllegalStateException.class, tenLapGame::gameResult);
        }
    }
}