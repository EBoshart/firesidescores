package boshart.erwin.firesidescores;


import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
public class ScoreController {

    public static class ScoreDTO {
        public String winner;
        public String loser;

        @Override
        public String toString() {
            return
                    winner + ',' + loser + "\n";
        }
    }

    public final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(value = "/score/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public void getScore(@PathVariable  String id, HttpServletResponse response) {
        List<ScoreDTO> scores = scoreService.getScores(id);

        try {
            PrintWriter writer = response.getWriter();
            scores.forEach(score -> writer.write(score.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
