package core.Phrases;

public class PhraseParser {
	private String origPhrase;
	private String newPhrase;
	private String[] punctuation = {",", "?", "!", ".", ":", ";" };
	private String[] words;
	
	public PhraseParser(String phrase) {
		origPhrase = phrase;
		parseWords();
	}
	
	public String getOrigPhrase() { return origPhrase; }
	
	public String[] getWords() { return words; }
	private void parseWords() {
		newPhrase = origPhrase;
		System.out.println("PhraseParser: newPhrase - " + newPhrase);
		// Remove MOST punctuation and prepare for word splitting
		// Not removing apostrophes yet because that would either
		// 1) Split letters off from the word or
		// 2) mash letters together with no Space for the apostrophe later
		for (String punc : punctuation) {
			newPhrase = newPhrase.replace(punc, "");
		}
		
		// Still contains apostrophes
		words = newPhrase.split(" ");
	}

}
