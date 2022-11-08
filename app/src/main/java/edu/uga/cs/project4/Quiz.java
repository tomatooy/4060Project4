package edu.uga.cs.project4;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class Quiz {
    // instance variables for columns in table
    ArrayList<Question> questions = new ArrayList<Question>();
    int currentQuestionIndex = 0;
    double percentCorrect;
    int numberOfQuestions;
    int questionsCorrect;
    boolean isFinished = false;
    String Date;

    // POJO constructor
    public Quiz(ArrayList<Question> questions) {
        this.numberOfQuestions = questions.size();
        this.percentCorrect = 0;
        this.questionsCorrect = 0;
        this.questions = questions;

        // show first question
        System.out.println("# " + currentQuestionIndex +
                " " + questions.get(currentQuestionIndex).toString());
    }

    public boolean isFinished() {
        return isFinished;
    }

    // loads next question
    // checks if user is done with quiz
    public boolean next() {
        if(currentQuestionIndex + 1 >= questions.size()) {
            isFinished = true;
            return false;
        }
        else {
            currentQuestionIndex++;
            System.out.println("# " + currentQuestionIndex +
                    " " +questions.get(currentQuestionIndex).toString());
            return true;
        }
    }

    public Question getCurrentQuestionIndex() {
        return questions.get(currentQuestionIndex);
    }

    // user submits answer, marks correct or incorrect
    public boolean answer(int index) {
        Question current = questions.get(currentQuestionIndex);
        current.hasAttempted = true;
        current.userAnswer = current.possible.get(index);

        boolean correct = false;
        if(current.checkAnswer(index)) {
            correct = true;
            current.answeredCorrect = true;
            System.out.println("Correct!");
        }
        else {
            current.answeredCorrect = false;
            System.out.println("Wrong!");
        }
        System.out.println(percentCorrect + "%");

        return correct;
    }

    // toString for output
    public String toString() {
        String toReturn = "Number of questions: " + numberOfQuestions +
                "\nPercent correct: " + percentCorrect;
        for(Question q: questions){
            toReturn += "\n" + q.toString() ;
        }
        return toReturn;
    }
}
