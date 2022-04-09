package hs.simplefx.text;

public class CharacterTextFilter implements TextFilter {

	private String acceptedChars;
	
	public CharacterTextFilter(String acceptedChars) {
		this.acceptedChars = acceptedChars;
	}
	
	@Override
	public boolean isAccepted(String string) {
		return string.matches("["+acceptedChars+"]*");
	}

}
