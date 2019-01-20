package sar.upmc.fr.crossingroads;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ScoreBoard {
	
	private List<Score> liste;
	private static String url_file_score = "https://www.lrde.epita.fr/~renault/teaching/ppm/2018/results.txt";
	private static String path_file = "";
	
	public ScoreBoard (List<Score> l) {
		this.liste = new ArrayList<Score>(l);
	}
	
	public ScoreBoard () {
		this.liste = new ArrayList<Score>();
	}
	
	public List<Score> getListe () {
		return this.liste;
	}
	
	public void addScore (Score s) {
		this.liste.add(s);
	}
	
	public void mergeScoreBoard (ScoreBoard sc) {
		this.liste.addAll(sc.getListe());
		Collections.sort(this.liste, new SortByName());
		for (int i = 1; i < this.liste.size(); i++) {
			if (this.liste.get(i - 1).getLigne().equalsIgnoreCase(this.liste.get(i).getLigne())) {
				this.liste.remove(i);
				i--;
			}
		}
	}
	
	public void sortByName () {
		if (new SortByName().isSorted(this.liste))
			Collections.sort(this.liste, Collections.reverseOrder(new SortByName()));
		else
			Collections.sort(this.liste, new SortByName());
	}
	
	public static ScoreBoard getFromUrl () {
		ScoreBoard sb = new ScoreBoard();
		byte [] file = Download.DownloadFromUrl(ScoreBoard.url_file_score);

		if (file != null) {
			String text = new String(file, 0, file.length);
			String[] lines = text.split("\n");

			for (String s : lines) {
				String[] tmp = s.split("#");
				if (tmp.length == 4) {
					Score score = new Score(s, tmp[0], tmp[1], tmp[2], tmp[3]);
					sb.addScore(score);
				}
			}
		}

		return sb;
	}
	
	public String[][] toTabString () {
		String [][] result = new String[this.liste.size()][4];
		
		for(int i = 0; i < this.liste.size(); i++) {
			result[i][0] = this.liste.get(i).name_joueur;
            result[i][1] = this.liste.get(i).ville_position;
            result[i][2] = this.liste.get(i).score;
            result[i][3] = this.liste.get(i).level;
        }
		return result;
	}
	
	@Override
	public String toString () {
		return this.liste.toString();
	}
	
}
