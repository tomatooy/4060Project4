package edu.uga.cs.project4;

import java.util.*;

public class Question {
    // instance variables for columns in table
    public String prompt;
    public String state;
    public String correctAnswer;
    public String userAnswer;
    public String[] possibleAnswers;
    public boolean hasAttempted = false;
    public boolean answeredCorrect = false;
    public ArrayList<String> possible = new ArrayList<String>();
    public int correctIndex;
    public String id;

    // POJO Constructor
    public Question(String prompt, String state, String correctAnswer,
                    String[] possibleAnswers, String id) {
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
        this.state = state;
        this.prompt = prompt;
        System.out.println("Question created");
        generateQuizList();
        this.id = id;
    }

    // function to shuffle answer choices
    private void generateQuizList() {
        for(int i = 0; i < possibleAnswers.length; i++) {
            possible.add(possibleAnswers[i]);
        }
        possible.add(correctAnswer);
        Collections.shuffle(possible);
        for(int i = 0; i < possible.size(); i++) {
            if(possible.get(i).equals(correctAnswer)) {
                correctIndex = i;
            }
        }
    }

    // checks if user answer is correct
    public boolean checkAnswer(int index) {
        return index == correctIndex;
    }

    // toString for output
    public String toString() {
        String toReturn = prompt + " " + correctAnswer + "* ";
        for(String s : possibleAnswers){
            toReturn += s + " ";
        }
        return toReturn;
    }
}


