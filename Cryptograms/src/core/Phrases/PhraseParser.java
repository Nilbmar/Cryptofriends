package core.Phrases;

public class PhraseParser {
	private String origPhrase;
	private String newPhrase;
	private String[] punctuation = {",", "?", "!", ".", ":", ";", "'" };
	private String[] words;
	
	public PhraseParser(String phrase) {
		origPhrase = phrase;
	}
	
	public String getOrigPhrase() { return origPhrase; }
	
	public String[] getWords() { return words; }
	private void parseWords() {
		
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
