package sai.govardhan.wordle.Activities;


import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sai.govardhan.wordle.R;
import sai.govardhan.wordle.util.Game;

public class GameActivity extends Activity {
    Game g;
    TextView[][] texts;
    int row=0,col=0;
    boolean isGamePaused;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent t=getIntent();
        g=new Game(t.getStringExtra("word"),0,0);
        texts=new TextView[6][5];

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        hideSystemBars();
        setContentView(R.layout.game);
        LinearLayout ll=findViewById(R.id.contain);
        getAllButtons((ViewGroup) ll);
        getAllTextViews((ViewGroup)ll);



    }
    boolean isWin=false;
    void pauseInput()
    {
        isGamePaused=true;
    }
    void resumeInput()
    {
        if(isWin||g.getCurRow()==6)
        {   View v=null;
            if(isWin)
                v= getLayoutInflater().inflate(R.layout.win,null);
            else
            {
                v = getLayoutInflater().inflate(R.layout.lose, null);
                ((TextView)v.findViewById(R.id.ans)).setText(g.getAns());
            }
            AlertDialog.Builder ad = new AlertDialog.Builder(this);


            ad.setView(v);

            AlertDialog d=ad.show();
            ((Button)v.findViewById(R.id.continues)).setOnClickListener((vs)->{d.dismiss();finish(vs);});

        }
        else
            isGamePaused=false;
    }
    public void finish(View v)
    {   finish();
    }
    void animator(View v,int color,int delay)
    {
        v.animate().rotationX(180).setDuration(250).setStartDelay(delay*100).alpha(0).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(v.getRotationX()<=1)
                            return;
                        v.animate().rotationX(0).alpha(1).setDuration(250);
                        v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
                        if(delay==4)
                        {   Log.d("YO","GAME RESUMED");
                            Handler hd=new Handler();

                            hd.postDelayed(()->{resumeInput();},500);
                        }
                    }
                }

        );




    }
    void ValidateGame()
    {    pauseInput();
        Log.d("VALUD","VALD");
        Integer verdict[]=g.validate();
        int checkRow=g.getCurRow()-1;
        boolean flag=true;
        for(int i=0;i<5;i++)
        {
            if(verdict[i]!=2)
                flag=false;
        }
        isWin=flag;
        for(int i=0;i<5;i++)
            switch (verdict[i])
            {
                case 2:
                    animator(texts[checkRow][i],R.color.green,i);
                    break;
                case 1:
                    animator(texts[checkRow][i],R.color.blue,i);
                    break;
                case 0:
                    animator(texts[checkRow][i],R.color.red,i);
            }


    }

    void getAllTextViews(ViewGroup v)
    {

        for (int i = 0; i < v.getChildCount(); i++) {
            View child = v.getChildAt(i);
            if(child instanceof ViewGroup)
                getAllTextViews((ViewGroup)child);
            else if(child instanceof TextView)
            {   TextView cur=(TextView)child;
                if(cur.getTag()!=null)
                    if(cur.getTag().toString().equals("text"))
                    {   if(col==5)
                        {
                            col=0;
                             row+=1;
                        }
                        Log.d("Addeed","TextView");
                        texts[row][col]=cur;
                        col+=1;
                    }

            }

        }

    }
    void getAllButtons(ViewGroup v) {
        List<Button> mButtons = new ArrayList<>();
        for (int i = 0; i < v.getChildCount(); i++) {
            View child = v.getChildAt(i);
            if(child instanceof ViewGroup)
                getAllButtons((ViewGroup)child);
            else if(child instanceof Button)
            {   ((Button)child).setOnClickListener(this::onClick);
                mButtons.add((Button) child);
            }

        }
    }
    public void onClick(View v)
    {   if(isGamePaused)
            return;

        if(v instanceof Button)
        {
            Log.d("Ac","Button ");
            Button b=(Button)v;
            if(b.getText().toString().equals("DEL"))
            {
                if(g.getCurCol()==0)
                    return;
                else
                {   g.removeLetter();
                    texts[g.getCurRow()][g.getCurCol()].setText("");

                }
            return ;
            }
            Log.d("Pressed",b.getText().toString());
            int prevRow=g.getCurRow();
            Log.d(g.getCurRow().toString(),g.getCurCol().toString());
            texts[g.getCurRow()][g.getCurCol()].setText(b.getText().toString());
            g.addLetterAt(b.getText().toString());
            if(prevRow!=g.getCurRow())
            {   ValidateGame();

            }

        }

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
}