package com.aurora.auroramusic.repo;

import com.aurora.auroramusic.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepostiory extends JpaRepository<Track, Long>{}
