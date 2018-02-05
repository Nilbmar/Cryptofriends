package core.Phrases;

public class PhraseParser {
	private String origPhrase;
	private String[] punctuation = {",", "?", "!", ".", ":", ";", "'" };
	
	public PhraseParser(String phrase) {
		origPhrase = phrase;
	}
	
	public String getOrigPhrase() { return origPhrase; }
	
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
