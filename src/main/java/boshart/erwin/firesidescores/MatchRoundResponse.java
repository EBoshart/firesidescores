package boshart.erwin.firesidescores;

import java.util.List;

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
