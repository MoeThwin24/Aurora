package com.aurora.auroramusic.config;

import com.aurora.auroramusic.model.Track;
import com.aurora.auroramusic.repo.TrackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TrackRepository trackRepository;

    public DataSeeder(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void run(String... args) {
        if (trackRepository.count() == 0) {
            trackRepository.saveAll(List.of(
                new Track(null, "Night Drive", "Aurora", "Vibes", 210, "music/night-drive.mp3"),
                new Track(null, "Rainy City", "Mason Beats", "Chill", 180, "music/rainy-city.mp3"),
                new Track(null, "Sunset Loop", "LoFi Kid", "Study", 200, "music/sunset-loop.mp3")
            ));
        }
    }
}
