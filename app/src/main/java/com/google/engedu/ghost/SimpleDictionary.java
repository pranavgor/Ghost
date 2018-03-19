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

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    Random random = new Random();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int length = prefix.length();
        Log.i("Info: len of prefix",String.valueOf(length));
        if (prefix=="")
        {
            int no = random.nextInt(words.size())-1;
            return (words.get(no));
        }
        else
        {
            String result;
            int low=0;
            int high = words.size()-1;
            while(low<=high)
            {
                int mid=(low+high)/2;
                String getword = words.get(mid);
                Log.i("Info: len of getword",String.valueOf(getword.length()));
                Log.i("Info: getword out while",getword);
                while(getword.length()<length && mid<words.size())
                {
                    mid=mid+1;
                    getword = words.get(mid);
                    Log.i("Info: getword in while ",getword);
                }
                if(mid==words.size())
                    break;
                String newword = getword.substring(0,length);
                Log.i("Info:substring to match",newword);
                int test = prefix.compareTo(newword);
                Log.i("Info: TEST COMPARE ", String.valueOf(test));
                if(test==0)
                {
                    result = words.get(mid);
                    return result;
                }
                else if(test > 0) {
                    low = mid + 1;
                    Log.i("Info: low val ", String.valueOf(low));
                }
                else {
                    high = mid - 1;
                    Log.i("Info: high val ", String.valueOf(high));
                }
            }

        }
        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
