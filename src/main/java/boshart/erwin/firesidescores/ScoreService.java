package boshart.erwin.firesidescores;

import boshart.erwin.firesidescores.ScoreController.ScoreDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private static final String BASE_BATTLEFY_API_URL = "https://dtmwra1jsgyb0.cloudfront.net";

    private final RestTemplate restTemplate;

    public ScoreService() {
        restTemplate = new RestTemplate();
    }

    private List<MatchRoundResponse> getScoreByRound(String stageId, int round) {
        String url = BASE_BATTLEFY_API_URL + "/stages/" + stageId + "/matches?roundNumber=" + round;
        var response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MatchRoundResponse>>() {});
        return response.getBody();

    }

    public List<ScoreDTO> getScores(String stageId) {
        List<ScoreDTO> result = new ArrayList<>();
        int round = 1;
        while (true) {
            List<MatchRoundResponse> scoresByRound = getScoreByRound(stageId, round);
            if (scoresByRound.isEmpty())
                return result;
            List<ScoreDTO> matchRoundResults = scoresByRound.stream().map(s -> {
                ScoreDTO scoreDTO = new ScoreDTO();
                if (s.top.winner) {
                    scoreDTO.winner = s.top.team.name;
                    scoreDTO.loser = s.bottom.team != null ? s.bottom.team.name : null;
                } else {
                    scoreDTO.winner = s.bottom.team.name;
                    scoreDTO.loser = s.top.team != null ? s.top.team.name : null;
                }
                return scoreDTO;

            }).collect(Collectors.toList());
            result.addAll(matchRoundResults);
            round++;
        }
    }


}
