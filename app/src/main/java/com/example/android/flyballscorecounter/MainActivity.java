package com.example.android.flyballscorecounter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context context;

    int backgrounds[] = {R.drawable.background1,R.drawable.background2,R.drawable.background3,R.drawable.background4};

    int scoreA;
    int faultA;
    int scoreB;
    int faultB;

    int[] viewsStatesA = new int[4];
    int[] viewsStatesB = new int[4];

    String[] dogs = {"1st","2nd","3rd","4th"};

    View firstDogTeamA;
    View secondDogTeamA;
    View thirdDogTeamA;
    View fourthDogTeamA;
    View[] viewsA;

    Queue<Integer> queueTeamA = new LinkedList<Integer>();
    Queue<Integer> queueTeamB = new LinkedList<Integer>();

    TextView textViewTeamAScore;
    TextView textViewTeamAFault;
    Button teamAGreenBut;
    Button teamARedBut;

    View firstDogTeamB;
    View secondDogTeamB;
    View thirdDogTeamB;
    View fourthDogTeamB;
    View[] viewsB;

    TextView textViewTeamBScore;
    TextView textViewTeamBFault;
    Button teamBGreenBut;
    Button teamBRedBut;

    Spinner backgroundSpinner;
    ImageView background;

    Button[] buttons;

    RelativeLayout teamABackground;
    RelativeLayout teamBBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

        backgroundSpinner = (Spinner) findViewById(R.id.spinner);
        backgroundSpinner.setOnItemSelectedListener(this);

        background = (ImageView) findViewById(R.id.backgroundImage);

        CustomAdapter customAdapterForBackground = new CustomAdapter(getApplicationContext(),backgrounds);
        backgroundSpinner.setAdapter(customAdapterForBackground);

        textViewTeamAScore = (TextView) findViewById(R.id.textViewTeamAScore);
        textViewTeamAFault = (TextView) findViewById(R.id.textViewTeamAFault);
        firstDogTeamA = findViewById(R.id.firstDogTeamA);
        secondDogTeamA = findViewById(R.id.secondDogTeamA);
        thirdDogTeamA = findViewById(R.id.thirdDogTeamA);
        fourthDogTeamA = findViewById(R.id.fourthDogTeamA);
        teamAGreenBut = (Button) findViewById(R.id.teamAGreenButton);
        teamARedBut = (Button) findViewById(R.id.teamARedButton);
        teamABackground = (RelativeLayout) findViewById(R.id.teamABackground);

        textViewTeamBScore = (TextView) findViewById(R.id.textViewTeamBScore);
        textViewTeamBFault = (TextView) findViewById(R.id.textViewTeamBFault);
        firstDogTeamB = findViewById(R.id.firstDogTeamB);
        secondDogTeamB = findViewById(R.id.secondDogTeamB);
        thirdDogTeamB = findViewById(R.id.thirdDogTeamB);
        fourthDogTeamB = findViewById(R.id.fourthDogTeamB);
        teamBGreenBut = (Button) findViewById(R.id.teamBGreenButton);
        teamBRedBut = (Button) findViewById(R.id.teamBRedButton);
        teamBBackground = (RelativeLayout) findViewById(R.id.teamBBackground);

        viewsA = new View[] {firstDogTeamA,secondDogTeamA,thirdDogTeamA,fourthDogTeamA};
        viewsB = new View[] {firstDogTeamB,secondDogTeamB,thirdDogTeamB,fourthDogTeamB};
        buttons = new Button[] {teamAGreenBut,teamARedBut,teamBGreenBut,teamBRedBut};

        initializeValues();
        initializeLayout();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("scoreA", scoreA);
        savedInstanceState.putInt("faultA", faultA);
        savedInstanceState.putInt("scoreB", scoreB);
        savedInstanceState.putInt("faultB", faultB);
        savedInstanceState.putCharSequence("teamAButText1",teamAGreenBut.getText());
        savedInstanceState.putCharSequence("teamAButText2",teamARedBut.getText());
        savedInstanceState.putCharSequence("teamBButText1",teamBGreenBut.getText());
        savedInstanceState.putCharSequence("teamBButText2",teamBRedBut.getText());

        boolean[] notFinishedTeamA = new boolean[4];
        int sizeQueueTeamA = queueTeamA.size();
        int nextDogA = queueTeamA.peek();
        savedInstanceState.putInt("nextDogA",nextDogA);
        for (int i=0; i<sizeQueueTeamA; i++){
            notFinishedTeamA[queueTeamA.remove()] = true;
        }
        savedInstanceState.putBooleanArray("notFinishedTeamA", notFinishedTeamA);

        boolean[] notFinishedTeamB = new boolean[4];
        int sizeQueueTeamB = queueTeamB.size();
        int nextDogB = queueTeamB.peek();
        savedInstanceState.putInt("nextDogB",nextDogB);
        for (int i=0; i<sizeQueueTeamB; i++){
            notFinishedTeamB[queueTeamB.remove()] = true;
        }
        savedInstanceState.putBooleanArray("notFinishedTeamB", notFinishedTeamB);

        savedInstanceState.putIntArray("viewsStatesA",viewsStatesA);
        savedInstanceState.putIntArray("viewsStatesB",viewsStatesB);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState !=null) {
            scoreA = savedInstanceState.getInt("scoreA");
            faultA = savedInstanceState.getInt("faultA");
            scoreB = savedInstanceState.getInt("scoreB");
            faultB = savedInstanceState.getInt("faultB");
            textViewTeamAScore.setText("" + scoreA);
            textViewTeamAFault.setText("" + faultA);
            textViewTeamBScore.setText("" + scoreB);
            textViewTeamBFault.setText("" + faultB);

            CharSequence butText1A = savedInstanceState.getCharSequence("teamAButText1");
            CharSequence butText2A = savedInstanceState.getCharSequence("teamAButText2");
            teamAGreenBut.setText(butText1A);
            teamARedBut.setText(butText2A);
            CharSequence butText1B = savedInstanceState.getCharSequence("teamBButText1");
            CharSequence butText2B = savedInstanceState.getCharSequence("teamBButText2");
            teamBGreenBut.setText(butText1B);
            teamBRedBut.setText(butText2B);

            boolean[] notFinishedTeamA = savedInstanceState.getBooleanArray("notFinishedTeamA");
            boolean[] notFinishedTeamB = savedInstanceState.getBooleanArray("notFinishedTeamB");
            queueTeamA.clear();
            queueTeamB.clear();
            int nextDogA = savedInstanceState.getInt("nextDogA");
            int nextDogB = savedInstanceState.getInt("nextDogB");
            for (int i = nextDogA; i < nextDogA+4; i++) {
                if (notFinishedTeamA[i%4]) {
                    queueTeamA.add(i%4);
                }
            }
            for (int i = nextDogB; i<nextDogB+4; i++) {
                if (notFinishedTeamB[i%4]) {
                    queueTeamB.add(i%4);
                }
            }

            viewsStatesA = savedInstanceState.getIntArray("viewsStatesA");
            viewsStatesB = savedInstanceState.getIntArray("viewsStatesB");

            for(int i=0; i<4; i++){
                switch(viewsStatesA[i]){
                    case 0:
                        viewsA[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle));
                        break;
                    case 1:
                        viewsA[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_with_tick));
                        break;
                    case 2:
                        viewsA[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_with_x));
                        break;
                }
                switch(viewsStatesB[i]){
                    case 0:
                        viewsB[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle));
                        break;
                    case 1:
                        viewsB[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_with_tick));
                        break;
                    case 2:
                        viewsB[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_with_x));
                        break;
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        background.setImageResource(backgrounds[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void scoring(View view) {
        int dogA;
        int dogB;
        switch (view.getId()) {
            case R.id.teamAGreenButton:
                dogA = queueTeamA.remove();
                scoreA++;
                updateTeamA(true,dogA);
                break;
            case R.id.teamARedButton:
                dogA = queueTeamA.remove();
                faultA++;
                queueTeamA.add(dogA);
                updateTeamA(false,dogA);
                break;
            case R.id.teamBGreenButton:
                dogB = queueTeamB.remove();
                scoreB++;
                updateTeamB(true,dogB);
                break;
            case R.id.teamBRedButton:
                dogB = queueTeamB.remove();
                faultB++;
                queueTeamB.add(dogB);
                updateTeamB(false,dogB);
                break;
        }
    }

    public void updateTeamA(boolean finished, int dogA) {
        if (queueTeamA.size() != 0) {
            int nextDog = queueTeamA.peek();
            teamAGreenBut.setText(getResources().getString(R.string.buttonText1, dogs[nextDog]));
            teamARedBut.setText(getResources().getString(R.string.buttonText2, dogs[nextDog]));
        }

        Drawable background;
        if (finished) {
            background = ContextCompat.getDrawable(this, R.drawable.rectangle_with_tick);
            viewsStatesA[dogA] = 1;
            textViewTeamAScore.setText("" + scoreA);
        } else {
            background = ContextCompat.getDrawable(this, R.drawable.rectangle_with_x);
            viewsStatesA[dogA] = 2;
            textViewTeamAFault.setText("" + faultA);
        }
        viewsA[dogA].setBackground(background);
        if(scoreA == 4) stop(true);
    }

    public void updateTeamB(boolean finished,int dogB) {
        if (queueTeamB.size() != 0) {
            int nextDog = queueTeamB.peek();
            teamBGreenBut.setText(getResources().getString(R.string.buttonText1, dogs[nextDog]));
            teamBRedBut.setText(getResources().getString(R.string.buttonText2, dogs[nextDog]));
        }

        Drawable background;
        if (finished) {
            background = ContextCompat.getDrawable(this, R.drawable.rectangle_with_tick);
            viewsStatesB[dogB] = 1;
            textViewTeamBScore.setText("" + scoreB);
        } else {
            background = ContextCompat.getDrawable(this, R.drawable.rectangle_with_x);
            viewsStatesB[dogB] = 2;
            textViewTeamBFault.setText("" + faultB);
        }
        viewsB[dogB].setBackground(background);
        if(scoreB == 4) stop(false);
    }

    public void initializeLayout(){
        teamAGreenBut.setText(getResources().getString(R.string.buttonText1, dogs[0]));
        teamARedBut.setText(getResources().getString(R.string.buttonText2, dogs[0]));
        teamBGreenBut.setText(getResources().getString(R.string.buttonText1, dogs[0]));
        teamBRedBut.setText(getResources().getString(R.string.buttonText2, dogs[0]));

        textViewTeamAScore.setText("0");
        textViewTeamAFault.setText("0");
        textViewTeamBScore.setText("0");
        textViewTeamBFault.setText("0");

        for(int i=0; i<4; i++){
            buttons[i].setAlpha(1f);
            buttons[i].setEnabled(true);
        }
        for(int i=0; i<4; i++){
            viewsA[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle));
            viewsStatesA[i] = 0;
            viewsB[i].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle));
            viewsStatesB[i] = 0;
        }
        teamABackground.setBackgroundResource(R.drawable.background);
        teamBBackground.setBackgroundResource(R.drawable.background);
    }

    public void initializeValues(){
        scoreA = 0;
        faultA = 0;
        scoreB = 0;
        faultB = 0;
        queueTeamA.clear();
        queueTeamB.clear();
        for(int i=0; i<4; i++){
            queueTeamA.add(i);
            queueTeamB.add(i);
        }
    }

    public void stop(boolean isAWinner) {
        if(isAWinner) {
            teamABackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gold, null));
            teamBBackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.silver, null));
        } else {
            teamABackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.silver, null));
            teamBBackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gold, null));
        }
        for(int i=0; i<4; i++){
            buttons[i].setAlpha(0.4f);
            buttons[i].setEnabled(false);
        }
    }

    public void reset(View view) {
        initializeValues();
        initializeLayout();
        /*Intent newActivity = new Intent(this, MainActivity.class);
        startActivity(newActivity);*/
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.resetText), Toast.LENGTH_SHORT).show();
    }
}


