package com.aurora.auroramusic.repo;

import com.aurora.auroramusic.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long>{}
