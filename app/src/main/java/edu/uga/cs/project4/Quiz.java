package edu.uga.cs.project4;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class Quiz {
    private long   id;
    private String date;
    private long question_1;
    private long question_2;
    private long question_3;
    private long question_4;
    private long question_5;
    private long question_6;
    private long result;
    private long questions_answered;

    // POJO constructor
    public Quiz()
    {
        this.id = -1;
        this.date = null;
        this.question_1 = -1;
        this.question_2 = -1;
        this.question_3 = -1;
        this.question_4 = -1;
        this.question_5 = -1;
        this.question_6 = -1;
        this.result = -1;
        this.questions_answered = -1;
    }

    public Quiz( String date, long question_1, long question_2, long question_3,
                 long question_4, long question_5, long question_6,
                 long result, long questions_answered) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.date = date;
        this.question_1 = question_1;
        this.question_2 = question_2;
        this.question_3 = question_3;
        this.question_4 = question_4;
        this.question_5 = question_5;
        this.question_6 = question_6;
        this.result = result;
        this.questions_answered = questions_answered;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setId(String date)
    {
        this.date = date;
    }

    public long getQuestion1()
    {
        return question_1;
    }

    public void setQuestion1(long qid)
    {
        this.question_1 = qid;
    }

    public long getQuestion2()
    {
        return question_2;
    }

    public void setQuestion2(long qid)
    {
        this.question_2 = qid;
    }

    public long getQuestion3()
    {
        return question_3;
    }

    public void setQuestion3(long qid)
    {
        this.question_3 = qid;
    }

    public long getQuestion4()
    {
        return question_4;
    }

    public void setQuestion4(long qid)
    {
        this.question_4 = qid;
    }

    public long getQuestion5()
    {
        return question_5;
    }

    public void setQuestion5(long qid)
    {
        this.question_5 = qid;
    }

    public long getQuestion6()
    {
        return question_6;
    }

    public void setQuestion6(long qid)
    {
        this.question_6 = qid;
    }

    public long getResult()
    {
        return result;
    }

    public void setResult(long val)
    {
        this.result = val;
    }

    public long getQuestionsAnswered()
    {
        return questions_answered;
    }

    public void setQuestionsAnswered(long val) { this.questions_answered = val; }


    // toString for output
    public String toString() {
        return id + ": " + date  + " " + question_1 + " " + question_2
                + " " + question_3
                + " " + question_4
                + " " + question_5
                + " " + question_6
                +  " " + result + " " + questions_answered;
    }
}
