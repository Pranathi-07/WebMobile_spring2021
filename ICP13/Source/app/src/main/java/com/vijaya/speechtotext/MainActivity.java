package com.vijaya.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import java.text.SimpleDateFormat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private TextToSpeech textToSpeech;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String PREFS = "prefs";
    private static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(PREFS,0);
        editor = preferences.edit();

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int text = textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.speak("Hello", TextToSpeech.QUEUE_FLUSH, null);
                } }});
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                    ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String input = res.get(0);
                    assistant(input);
                }
                break;
            }
        }
    }

    private void assistant(String text){
        Log.e("Speech","" + text);
        String[] speech = text.split(" ");
        if(text.contains("hello medical assistant")){
            textToSpeech.speak("What is your name?", TextToSpeech.QUEUE_FLUSH, null);
        }
        if(text.contains("hi my name is ")){
            String name = speech[speech.length-1];
            editor.putString(NAME,name).apply();
            textToSpeech.speak("How are you today?" + preferences.getString(NAME, null), TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(text.contains("I'm not feeling good what should I do")){
            textToSpeech.speak("I can understand. Please tell your symptoms in short.", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(text.contains("what medicine should I take")) {
            textToSpeech.speak(preferences.getString(NAME, null) + ", I think you have Fever. Please take this medicine.", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(text.contains("thank you")){
            textToSpeech.speak("Thank you too" + preferences.getString(NAME, null)+"Take care", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(text.contains("what time is it")) {
            SimpleDateFormat sdfDate =new SimpleDateFormat("h:mm a");
            Date now = new Date();
            String[] strDate = sdfDate.format(now).split(":");
            if(strDate[1].contains("00"))strDate[1] = "o'clock";
            textToSpeech.speak("The time is : "+sdfDate.format(now), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}