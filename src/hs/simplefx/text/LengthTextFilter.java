package hs.simplefx.text;

public class LengthTextFilter implements TextFilter {

	private int minLen, maxLen;
	
	public LengthTextFilter(int minLen, int maxLen) {
		this.minLen = minLen;
		this.maxLen = maxLen;
	}

	@Override
	public boolean isAccepted(String string) {
		return string.length() >= minLen && string.length() <= maxLen;
	}
	
}
