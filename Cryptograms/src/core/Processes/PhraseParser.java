package core.Processes;

public class PhraseParser {
	private String origPhrase;
	private String[] punctuation = {",", "?", "!", ".", ":", ";", "'" };
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public PhraseParser(String phrase) {
		origPhrase = phrase;
	}
	
	public String getOrigPhrase() { return origPhrase; }
	
	public boolean isLetter(String s) {
		boolean isInAlphabet = false;
		if (s != null && s.length() == 1 && alphabet.contains(s)) {
			isInAlphabet = true;
		}
		
		return isInAlphabet;
	}
	
	public boolean isBlankSpace(String s) {
		boolean blank = false;
		if (s == null || s.isEmpty() || s.contains(" ")) {
			blank = true;
		}
		return blank;
	}
	
	public boolean isPunctuation(String s) {
		boolean isPunctuation = false;
		for (String punc : punctuation) {
			if (punc.contains(s)) {
				isPunctuation = true;
			}
		}
		return isPunctuation;
	}

}
