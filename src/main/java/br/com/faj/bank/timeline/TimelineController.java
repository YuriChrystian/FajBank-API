package br.com.faj.bank.timeline;

import br.com.faj.bank.timeline.domain.FetchTimelineUseCase;
import br.com.faj.bank.timeline.model.domain.TimelineDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/timeline")
public class TimelineController {

    private final FetchTimelineUseCase timelineController;

    public TimelineController(
            FetchTimelineUseCase timelineController
    ) {
       this.timelineController = timelineController;
    }

    // Ajustar wrapper no futuro para existir um TimelineResponse
    @GetMapping
    public ResponseEntity<List<TimelineDomain>> fetch() {
        var data = timelineController.getTimeline();

        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.of(Optional.of(data));
    }
}
