package sai.govardhan.wordle.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import sai.govardhan.wordle.R;

public class MainActivity extends Activity {
    String words[];
    Random ran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ran=new Random(System.nanoTime());
        words=getResources().getStringArray(R.array.words);
        hideSystemBars();
        setContentView(R.layout.activity_main);
        TextView txt=findViewById(R.id.main);
        Button b=findViewById(R.id.button);
        b.setOnClickListener((v)->playGame());
        txt.animate().translationY(0).rotationX(0).setStartDelay(300).setDuration(500).setListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        b.animate().setDuration(500).alpha(1);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }
        );
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }

    public void playGame()
    {
        Intent t=new Intent(this,GameActivity.class);
        int wordNum=ran.nextInt()%words.length;
        if(wordNum<0)wordNum=-wordNum;
        Log.d("WORD IS",words[wordNum]);
        t.putExtra("word",words[wordNum]);
        startActivity(t);

    }
}