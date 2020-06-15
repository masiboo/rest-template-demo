package com.example.resttemplatedemo.model;

public class ScoreBoard {

    private long id;
    private String team;
    private int point;

    public ScoreBoard() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
