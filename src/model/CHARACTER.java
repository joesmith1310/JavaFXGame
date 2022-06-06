package model;



public enum CHARACTER {
	
	TIMONE("view/resources/characterChooser/lion_king_PNG31.png","view/resources/characterChooser/lion_king_PNG73.png","view/resources/characterChooser/lion_king_PNG2.png"),
	PUMBA("view/resources/characterChooser/lion_king_PNG13.png","view/resources/characterChooser/lion_king_PNG13.png","view/resources/characterChooser/lion_king_PNG1.png"),
	SIMBA("view/resources/characterChooser/lion_king_PNG7.png","view/resources/characterChooser/lion_king_PNG35.png","view/resources/characterChooser/lion_king_PNG3.png");
	
	private String urlCharacter;
	private String urlPlayerCharacter;
	private String urlFlippedPlayerCharacter;
	
	private CHARACTER(String urlCharacter, String playerCharacter, String flippedPlayerCharacter) {
		this.urlCharacter = urlCharacter;
		this.urlPlayerCharacter = playerCharacter;
		this.urlFlippedPlayerCharacter = flippedPlayerCharacter;
	}
	
	public String getURL() {
		return this.urlCharacter;
	}
	public String getPlayerURL() {
		return this.urlPlayerCharacter;
	}
	public String getFlippedPlayerURL() {
		return this.urlFlippedPlayerCharacter;
	}

}
