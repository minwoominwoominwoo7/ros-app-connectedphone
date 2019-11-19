/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.android.pubCommandVoice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.ros.android.MessageCallable;
import org.ros.android.MessagePub;
import org.ros.android.RosActivity;
import org.ros.android.MessageSub;

import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import static android.speech.tts.TextToSpeech.ERROR;


/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MainActivity extends RosActivity {

  private MessagePub<std_msgs.String> pubString;
  private MessageSub<std_msgs.String> subString;
  private String subStringMessage;
  private Button commandButton0;
  private Button commandButton1;
  private Button commandButton2;
  private Button commandButton3;
  private Button commandButton4;
  private Button commandButton5;
  private Button commandButton6;
  private Button commandButton7;
  private Button commandButton8;

  private Context mContext;
  private BroadcastReceiver mReceiver = null;
  private MessagePub<std_msgs.String> pubVoice;
  private final int REQ_CODE_SPEECH_INPUT = 100;
  private ImageButton btnSpeak;
  private TextView txtSpeechInput;

  private Boolean feeling_action = false ;
  private Boolean smile_sound = true ;
  //private Switch sw;
  //private Switch sw1;

  private long last_time ;

  final String command0Array[] = {  "술", "따라", "한잔" } ;
  final String command1Array[] = { "건배 시작" , "건배시작" , "짠", "브라보", "잔 들어", "잠깐", "스탑", "잔들어" } ;
  final String command2Array[] = { "건배그만" , "건배 그만", "잔 내려", "잔내려", "잔 놓으세요", "잔놓으세요", "잔놔둬", "잔 놔둬", "잔을 내려", "잔을내려", "잔을 놓으세요", "잔을놓으세요", "잔을놔둬", "잔을 놔둬"} ;
  final String command3Array[] = { "안주", "과자"} ;
  final String command4Array[] = {  "폰들어", "폰보여","폰 들어", "폰 보여", "폰을들어", "폰을보여","폰을 들어", "폰을 보여","폰 집어", "폰을 집어","폰집어", "폰을집어"} ;
  final String command5Array[] = { "폰내려", "폰놓", "폰 내려", "폰 놓", "폰을내려", "폰을놓", "폰을 내려", "폰을 놓" } ;
  final String command6Array[] = { "정지", "그만", "멈춤" } ;
  final String command7Array[] = { "시작", "움직여", "움직이"} ;
  final String command8Array[] = { "파노라마"} ;

  private TextToSpeech tts;              // TTS 변수 선언

  public MainActivity() {
    // The RosActivity constructor configures the notification title and ticker
    // messages.
    super("Pubsub Sample", "Pubsub Sample");
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mContext = getApplicationContext();
    btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
    commandButton0 = (Button) findViewById(R.id.Button0);
    commandButton1 = (Button) findViewById(R.id.Button1);
    commandButton2 = (Button) findViewById(R.id.Button2);
    commandButton3 = (Button) findViewById(R.id.Button3);
    commandButton4 = (Button) findViewById(R.id.Button4);
    commandButton5 = (Button) findViewById(R.id.Button5);
    commandButton6 = (Button) findViewById(R.id.Button6);
    commandButton7 = (Button) findViewById(R.id.Button7);
    commandButton8 = (Button) findViewById(R.id.Button8);


    txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);

    commandButton0.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("0");
        pubString.publish();
      }
    });

    commandButton1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("1");
        pubString.publish();
      }
    });

    commandButton2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("2");
        pubString.publish();
      }
    });

    commandButton3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("3");
        pubString.publish();
      }
    });

    commandButton4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("4");
        pubString.publish();
      }
    });

    commandButton5.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("5");
        pubString.publish();
      }
    });

    commandButton6.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("6");
        pubString.publish();
      }
    });

    commandButton7.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("7");
        pubString.publish();
      }
    });

    commandButton8.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pubString.message.setData("8");
        pubString.publish();
      }
    });

    tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if(status != ERROR) {
          // 언어를 선택한다.
          tts.setLanguage(Locale.KOREAN);
        }
      }
    });
    last_time = System.currentTimeMillis();
    //registerReceiver();
  }


  @Override
  protected void init(NodeMainExecutor nodeMainExecutor) {

    NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
    nodeConfiguration.setMasterUri(getMasterUri());

    ////////////////publish string_command //
    pubString = new MessagePub<std_msgs.String>(mContext);
    pubString.setTopicName("command_Key");
    pubString.setMessageType(std_msgs.String._TYPE);
    nodeMainExecutor.execute(pubString,
            nodeConfiguration.setNodeName("android/portman/command_Key"));

    ////////////////publish String //
    pubVoice = new MessagePub<std_msgs.String>(mContext);
    pubVoice.setTopicName("~recognizer/output");
    pubVoice.setMessageType(std_msgs.String._TYPE);
    nodeMainExecutor.execute(pubVoice,
            nodeConfiguration.setNodeName("android/portman/publish_voice_string"));

    btnSpeak.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        promptSpeechInput();
      }
    });

  }

  /**
   * Showing google speech input dialog
   * */
  private void promptSpeechInput() {

    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt));

    try {
      startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
    } catch (ActivityNotFoundException a) {
      Toast.makeText(getApplicationContext(),
              getString(R.string.speech_not_supported),
              Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * Receiving speech input
   * */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    switch (requestCode) {
      case REQ_CODE_SPEECH_INPUT: {
        if (resultCode == RESULT_OK && null != data) {

          ArrayList<String> result = data
                  .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
          String sentance = result.get(0) ;
          String controlDirection = judgeCommand ( sentance );
          if( controlDirection != null ) {
            pubString.message.setData(controlDirection);
            pubString.publish();
            pubVoice.message.setData(sentance);
            pubVoice.publish();
            tts.setPitch(1.0f);
            tts.setSpeechRate(1.0f);

            if (controlDirection.equals("0")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n 따라서 0. 술 따르기 ");
              tts.speak("시원한 술 따라 드릴게요.",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("1")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 1. 건배 시작 ");
              tts.speak(" 우리 건배해요. ",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("2")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 2. 건배 그만 ");
              tts.speak("잔을 내려 놓겠습니다.",TextToSpeech.QUEUE_FLUSH, null);
            }  else if (controlDirection.equals("3")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 3. 안주 주기");
              tts.speak("맛있게 드세요.",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("4")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 4. 폰 들어 보여주기 ");
              tts.speak(" 네 알겠습니다. 폰을 집겠습니다.",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("5")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 5. 폰 내려놓기 ");
              tts.speak("폰을 내려 놓겠습니다. ",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("6")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 6. 일시 정지 ");
              tts.speak("움직임을 일시 정지 합니다.",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("7")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 7. 다시 시작하기 ");
              tts.speak("일시 정지한 움직임을 다시 시작합니다.",TextToSpeech.QUEUE_FLUSH, null);
            } else if (controlDirection.equals("8")) {
              txtSpeechInput.setText("'" + sentance + "' 를 인식했습니다." + "\n따라서 8. 파노라마 찍기");
              tts.speak("파노라마 샷을 찍겠습니다.",TextToSpeech.QUEUE_FLUSH, null);
            } else {
              txtSpeechInput.setText(sentance + "해당 명령이 존재 하지 않습니다. ");
            }
          }else{
            txtSpeechInput.setText(sentance +"해당 명령이 존재 하지 않습니다. ");
          }

        }
        break;
      }
      default:
        super.onActivityResult(requestCode, resultCode, data);
        break;
    }
  }

  public String judgeCommand( String sentance ){

    String currentControlState = null;
    int i = 0 ;
    for( i = 0 ; i<command0Array.length ; i++ ){
      if( sentance.contains(command0Array[i]) ){
        currentControlState = "0" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command1Array.length ; i++ ){
      if( sentance.contains(command1Array[i]) ){
        currentControlState = "1" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command2Array.length ; i++ ){
      if( sentance.contains(command2Array[i]) ){
        currentControlState = "2" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command3Array.length ; i++ ){
      if( sentance.contains(command3Array[i]) ){
        currentControlState = "3" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command4Array.length ; i++ ){
      if( sentance.contains(command4Array[i]) ){
        currentControlState = "4" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command5Array.length ; i++ ){
      if( sentance.contains(command5Array[i]) ){
        currentControlState = "5" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command6Array.length ; i++ ){
      if( sentance.contains(command6Array[i]) ){
        currentControlState = "6" ;
        return currentControlState ;
      }
    }
    for( i = 0 ; i<command7Array.length ; i++ ){
      if( sentance.contains(command7Array[i]) ){
        currentControlState = "7" ;
        return currentControlState ;
      }
    }

    for( i = 0 ; i<command8Array.length ; i++ ){
      if( sentance.contains(command8Array[i]) ){
        currentControlState = "8" ;
        return currentControlState ;
      }
    }
    return currentControlState ;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //unregisterReceiver();

  }

}