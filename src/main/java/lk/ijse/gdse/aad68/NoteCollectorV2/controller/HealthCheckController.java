package lk.ijse.gdse.aad68.NoteCollectorV2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
public class HealthCheckController {
    @GetMapping
    public String healthCheck() {
        return "Note taker is running";
    }
}
