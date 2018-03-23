package mhashim6.game1024.Exceptions;

@SuppressWarnings("serial")
public class GameOverExeption extends RuntimeException {

	private String message;

	public GameOverExeption(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Game is Over: " + message;
	}

}
