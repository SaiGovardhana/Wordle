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

import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import sai.govardhan.wordle.R;

public class MainActivity extends Activity {
    String words[];
    String easy[];
    Random ran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ran=new Random(System.nanoTime());
        words=getResources().getStringArray(R.array.words);
        easy=getResources().getStringArray(R.array.easy);
        hideSystemBars();
        setContentView(R.layout.activity_main);
        TextView txt=findViewById(R.id.main);
        Button b=findViewById(R.id.button);
        b.setOnClickListener((v)->playGame());

        txt.animate().translationY(0).rotationX(0).setStartDelay(300).setDuration(3500).setInterpolator(new OvershootInterpolator()).setListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        b.animate().setDuration(700).alpha(1);
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

        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );

        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }

    public void playGame()
    {
        Intent t=new Intent(this,GameActivity.class);
        int wordNum=ran.nextInt()%easy.length;
        if(wordNum<0)wordNum=-wordNum;
        Log.d("WORD IS",easy[wordNum]);
        t.putExtra("word",easy[wordNum]);
        startActivity(t);

    }
}