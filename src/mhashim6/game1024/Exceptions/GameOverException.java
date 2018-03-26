package mhashim6.game1024.Exceptions;

@SuppressWarnings("serial")
public class GameOverException extends RuntimeException {

	private String message;

	public GameOverException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Game is Over: " + message;
	}

}
