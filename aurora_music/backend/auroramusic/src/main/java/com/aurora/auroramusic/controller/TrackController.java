package com.aurora.auroramusic.controller;

import com.aurora.auroramusic.model.Track;
import com.aurora.auroramusic.repo.TrackRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@CrossOrigin(origins = "http://localhost:5173")
public class TrackController {
    private final TrackRepository trackRepository;

    public TrackController(TrackRepository trackRepository){
        this.trackRepository = trackRepository;
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
}
