package sar.upmc.fr.crossingroads;

import java.util.Comparator;
import java.util.List;

public class Score {

	private String	ligne;
	String	name_joueur;
	String	ville_position;
	String	score;
	String	level;
	
	public Score (String l, String name, String ville_position, String score, String level) {
		this.ligne = l;
		this.name_joueur = name;
		this.ville_position = ville_position;
		this.score = score;
		this.level = level;
	}
	
	public String getLigne () {
		return this.ligne;
	}
	
	@Override
	public String toString() {
		return name_joueur + "\t\t" + ville_position + "\t\t" + score + "\t\t" + level;
	}
	
	
}

class SortByName implements Comparator<Score>{

	@Override
	public int compare(Score o1, Score o2) {
		return o1.name_joueur.compareToIgnoreCase(o2.name_joueur);
	}
	
	public boolean isSorted(List<Score> l) {
		boolean result;
		
		result = true;
		for (int i = 1; i < l.size() && result; i++) {
	        if (l.get(i-1).name_joueur.compareToIgnoreCase(l.get(i).name_joueur) > 0)
	        	result = false;
	    }
		return result;
	}
	
}

class SortByScore implements Comparator<Score>{

	@Override
	public int compare(Score o1, Score o2) {
		return o1.score.compareTo(o2.score);
	}
	
}
