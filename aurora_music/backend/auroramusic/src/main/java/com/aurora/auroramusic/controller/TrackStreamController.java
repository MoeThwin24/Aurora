package com.aurora.auroramusic.controller;

import com.aurora.auroramusic.model.Track;
import com.aurora.auroramusic.repo.TrackRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.management.RuntimeErrorException;

@RestController
@RequestMapping("/api/tracks")
public class TrackStreamController {

    private final TrackRepository trackRepository;

    public TrackStreamController(TrackRepository trackRepository){
        this.trackRepository = trackRepository;
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<Resource> stream(@PathVariable Long id) throws IOException {
        Track track = trackRepository.findById(id).orElseThrow(()-> new RuntimeException("Track not found"));

        // filePaht Example: "music/night-drive.mp3"
        Resource resource = new ClassPathResource(track.getFilePath());

        if (!resource.exists()) {
            throw new RuntimeException("Audio file not found at: " + track.getFilePath());
        }

        return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                    .body(resource);

    }
    
}
