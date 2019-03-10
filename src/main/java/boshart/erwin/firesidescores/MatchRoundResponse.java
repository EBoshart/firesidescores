package boshart.erwin.firesidescores;

public class MatchRoundResponse {

    public Team top;
    public Team bottom;

    public static class Team {
        public Teamplayer team;
        public boolean winner;
        public Integer score;
    }

    public static class Teamplayer {
        public String name;
    }


}
