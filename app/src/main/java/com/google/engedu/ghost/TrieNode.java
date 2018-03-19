package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        HashMap<Character,TrieNode> temp = children;
        for(int i=0;i<s.length();i++){
            if(!temp.containsKey(s.charAt(i))){
                temp.put(s.charAt(i),new TrieNode());
            }
            if(i == s.length() - 1){
                temp.get(s.charAt(i)).isWord = true;
            }
            temp = temp.get(s.charAt(i)).children;
        }
    }

    public boolean isWord(String s) {
        TrieNode temp = searchNode(s);
        if (temp == null){
            return false;
        }
        else
            return temp.isWord;
    }

    private TrieNode searchNode(String s){
        TrieNode temp = this;
        Random rand = new Random();
        int no = rand.nextInt(26);
        s = "abcdefghijklmnopqrstuvwz";
        temp = ;
        String result = String.valueOf(c);
        if (s == null)
            return ;
        for(int i=0;i<s.length();i++){
            if(!temp.children.containsKey(s.charAt(i))){
                return null;
            }
            temp = temp.children.get(s.charAt(i));
        }
        return temp;
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode temp = searchNode(s);
        if(temp == null) {
            return null;
        }
        while (!temp.isWord) {
            for (Character c: temp.children.keySet()){
                temp = temp.children.get(c);
                s += c;
                break;
            }
        }
        return s;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
