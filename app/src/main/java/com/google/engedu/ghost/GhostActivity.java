/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    FastDictionary fastDictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            fastDictionary = new FastDictionary(assetManager.open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        onStart(null);
    }

    public void challenge(View view) {
        TextView Status = (TextView) findViewById(R.id.gameStatus);
        TextView label = (TextView) findViewById(R.id.ghostText);
        String word = label.getText().toString();
        if(word.length()>=4 && fastDictionary.isWord(word)) {
            Status.setText("User wins");
            Toast.makeText(this,"Challenge successful. You won !!",Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Reset to play again", Toast.LENGTH_LONG).show();
        }
        else {
            if(fastDictionary.getAnyWordStartingWith(word)!="") {
                String temp = fastDictionary.getAnyWordStartingWith(word);
                Toast.makeText(this, "Challenge Un-successful", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "The word that can be formed is: "+temp.toUpperCase(), Toast.LENGTH_LONG).show();
                Status.setText("Computer wins");
            }
            else {
                Status.setText("User wins");
                Toast.makeText(this,"Challenge successful. You won !!",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Reset to play again",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void reset(View view)
    {
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView Status = (TextView) findViewById(R.id.gameStatus);
        TextView label = (TextView) findViewById(R.id.ghostText);
        // Do computer turn stuff then make it the user's turn again
        int flag=0;
        String input = label.getText().toString();
        Log.i("Info : -",input);
        int length = label.length();
        if(length>=4 && fastDictionary.isWord(input))
        {
            Status.setText("Computer wins");
            Toast.makeText(this,"Reset to play again",Toast.LENGTH_LONG).show();
            flag = 1;
        }
        else
        {
           if(fastDictionary.getAnyWordStartingWith(input)==null)
           {
               Status.setText("Computer wins");
               Toast.makeText(this,"No word can be formed using this prefix",Toast.LENGTH_LONG).show();
               Toast.makeText(this,"Reset to play again",Toast.LENGTH_LONG).show();
               flag = 1;
           }
           else
           {
               String word = fastDictionary.getAnyWordStartingWith(input);
               char c = word.charAt(length);
               label.append(""+c);
           }
        }
        if (flag == 0) {
            userTurn = true;
            Status.setText(USER_TURN);
        }
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        TextView ghosttext = (TextView)findViewById(R.id.ghostText);
        TextView gamestatus = (TextView)findViewById(R.id.gameStatus);
        char c = (char) event.getUnicodeChar();
        if ((c>='a' && c<='z') || (c>='A' && c<='Z'))
        {
            ghosttext.append(""+c);
            gamestatus.setText(COMPUTER_TURN);
            userTurn=false;
            Log.i("Info : -","Working");
            computerTurn();
        }
        else
        {
            Toast.makeText(this, "Please enter a valid character", Toast.LENGTH_LONG).show();
        }
        return super.onKeyUp(keyCode, event);
    }
}
