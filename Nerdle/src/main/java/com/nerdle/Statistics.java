package com.nerdle;

public class Statistics {
    private String name;
    private int win_no;
    private int loose_no;
    private int winner_line_average;
    private int winner_elapsedTime_average;
    private int canceled_rounds;

    public Statistics(String name) {
        this.name = name;

    }


    // updates elapsed time average
    public void updateTimeAverage(int winTime) {
        setWinner_elapsedTime_average(getWinner_elapsedTime_average() + winTime/getWin_no());
    }
    
    public void updateLineAverage(int winLines) {
        setWinner_line_average(getWinner_line_average() + winLines/getWin_no());
    }
    

    // Getters and Setters
    public int getCanceled_rounds() {
        return canceled_rounds;
    }
    public int getLoose_no() {
        return loose_no;
    }
    public int getWin_no() {
        return win_no;
    }
    public String getName() {
        return name;
    }
    public int getWinner_elapsedTime_average() {
        return winner_elapsedTime_average;
    }
    public int getWinner_line_average() {
        return winner_line_average;
    }
    public void setCanceled_rounds(int canceled_rounds) {
        this.canceled_rounds = canceled_rounds;
    }
    public void setLoose_no(int loose_no) {
        this.loose_no = loose_no;
    }
    public void setWin_no(int win_no) {
        this.win_no = win_no;
    }
    public void setWinner_elapsedTime_average(int winner_elapsedTime_average) {
        this.winner_elapsedTime_average = winner_elapsedTime_average;
    }
    public void setWinner_line_average(int winner_line_average) {
        this.winner_line_average = winner_line_average;
    }
    public void setName(String name) {
        this.name = name;
    }

}
