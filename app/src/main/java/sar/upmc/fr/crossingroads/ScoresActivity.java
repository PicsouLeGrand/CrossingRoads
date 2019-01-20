package sar.upmc.fr.crossingroads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class ScoresActivity extends AppCompatActivity {

    private TextView textView;
    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        android.support.v7.app.ActionBar tb = getSupportActionBar();
        tb.setHomeButtonEnabled(true);
        tb.setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.textViewScores);

        scoreBoard = new ScoreBoard();
        scoreBoard = scoreBoard.getFromUrl();
        Log.d("tag", "scoreboard : " + scoreBoard.getListe().toString());
//        textView.setText(scoreBoard.getListe().toString());

        for(Score s : scoreBoard.getListe()) {
            textView.append(s.toString() + "\n");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}
