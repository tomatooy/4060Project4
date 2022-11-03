package edu.uga.cs.project4;

public class Quiz {
    // instance variables for columns in table
    private long id;
    private String date;
    private long q1;
    private long q2;
    private long q3;
    private long q4;
    private long q5;
    private long q6;
    private long score;

    // POJO constructor
    public Quiz() {
        this.id = -1;
        this.date = null;
        this.q1 = 1;
        this.q2 = 2;
        this.q3 = 3;
        this.q4 = 4;
        this.q5 = 5;
        this.q6 = 6;
    }

    // getters
    public long getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public long getQ1() {
        return q1;
    }
    public long getQ2() {
        return q2;
    }
    public long getQ3() {
        return q3;
    }
    public long getQ4() {
        return q4;
    }
    public long getQ5() {
        return q5;
    }
    public long getQ6() {
        return q6;
    }
    public long getScore() {
        return score;
    }

    // setters
    public void setDate(String date) {
        this.date = date;
    }
    public void setQ1(long q1) {
        this.q1 = q1;
    }
    public void setQ2(long q2) {
        this.q2 = q2;
    }
    public void setQ3(long q3) {
        this.q3 = q3;
    }
    public void setQ4(long q4) {
        this.q4 = q4;
    }
    public void setQ5(long q5) {
        this.q5 = q5;
    }
    public void setQ6(long q6) {
        this.q6 = q6;
    }
    public void setScore(long score) {
        this.score = score;
    }

    // toString for output
    public String toString() {
        return id + ": " + date + " " + q1 + " " + q2 + " " + q3 + " " +
                q4 + " " + q5 + " " + q6 + " " + score;
    }
}
