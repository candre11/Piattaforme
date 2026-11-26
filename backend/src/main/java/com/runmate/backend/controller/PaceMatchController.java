package com.runmate.backend.controller;

import com.runmate.backend.service.PaceMatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class PaceMatchController {

    private final PaceMatchService service;

    public PaceMatchController(PaceMatchService service) {
        this.service = service;
    }

    @GetMapping("/{runnerId}")
    public List<PaceMatchService.Match> findMatches(
            @PathVariable Long runnerId) {

        return service.findMatches(runnerId);
    }
}