package racingcar.util;

import java.util.List;

public class TestRefuelGenerator implements RefuelGenerator {
    @Override
    public List<Integer> generate(int max, int total) {
        return List.of(4, 4, 3, 3);
    }
}
