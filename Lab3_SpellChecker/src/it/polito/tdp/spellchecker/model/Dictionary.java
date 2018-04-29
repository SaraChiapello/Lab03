package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {

	private List<String> dizionario;
	private String language;
	
	public Dictionary() {

	}

	public boolean loadDictionary(String language) {
		if (dizionario != null && this.language.equals(language)) {
			return true;
		}
		
		dizionario = new ArrayList<String>();
		this.language = language;
		
		try {

			FileReader fr = new FileReader("rsc/" + language + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;

			while ((word = br.readLine()) != null) {
				dizionario.add(word.toLowerCase());
			}

			Collections.sort(dizionario);

			br.close();
			System.out.println("Dizionario " + language + " loaded. Found " + dizionario.size() + " words.");
			
			return true;

		} catch (IOException e) {
			System.err.println("Errore nella lettura del file");
			return false;
		}

	}

	public List<RichWord> spellCheckText(List<String> inputTextList) {

		List<RichWord> parole = new ArrayList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str);
			if (dizionario.contains(str.toLowerCase())) {
				richWord.setCorrect(true);
			} else {
				richWord.setCorrect(false);
			}
			parole.add(richWord);
		}

		return parole;
	}

	/*public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		// TODO Auto-generated method stub
		
		List<RichWord> parole = new ArrayList<RichWord>();
		int flag=0;
		int max=dizionario.size();
		int min=0;
		int i;
		int comp=0;

		for (String str : inputTextList) {
			RichWord richWord = new RichWord(str);
			do {
				i=((max+min)/2);
				comp=str.toLowerCase().compareTo(dizionario.get(i));
				if(comp<0)
					max=i;
				else if(comp>0)
					min=i;
			} while(comp==0||i==max||i==min);
			if(comp!=0) {
				richWord.setCorrect(false);
				parole.add(richWord);

			}else {
				richWord.setCorrect(true);
			}
		}

		return parole;
		}
*/
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> parole = new ArrayList<RichWord>();
		for (String str : inputTextList) {
			RichWord richWord = new RichWord(str);
			if (binarySearch(str.toLowerCase()))
				richWord.setCorrect(true);
			else
				richWord.setCorrect(false);
			parole.add(richWord);
		}
		return parole;
	}

	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		// TODO Auto-generated method stub
		List<RichWord> parole = new ArrayList<RichWord>();
		

		for (String str : inputTextList) {
			boolean trovato=false;

			RichWord richWord = new RichWord(str);
			for(String s:dizionario) {
				if(s.equalsIgnoreCase(str)) {
					trovato=true;
					break;
				}
			}
			richWord.setCorrect(trovato);
			
			parole.add(richWord);

		}
		return parole;
	}
	

	private boolean binarySearch(String stemp) {
		int inizio = 0;
		int fine = dizionario.size();
		while (inizio != fine) {
			int medio = (fine + inizio) / 2;
			if (stemp.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			} else if (stemp.compareToIgnoreCase(dizionario.get(medio)) > 0) { //la parola è dopo
				inizio = medio + 1;
			} else { //a parola è prima
				fine = medio-1;
			}
		}
		return false;
	}
}
