package racingcar.utils;

import java.util.Random;

public class RandomNumber {

	private final static int MAX_EXCLUSIVE = 10;
	private static final Random random = new Random();

	public int getRandomNumber() {
		return random.nextInt(MAX_EXCLUSIVE);
	}
}