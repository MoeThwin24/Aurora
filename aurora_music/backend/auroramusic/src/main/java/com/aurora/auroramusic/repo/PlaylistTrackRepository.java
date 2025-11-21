package com.aurora.auroramusic.repo;

import com.aurora.auroramusic.model.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
    List<PlaylistTrack> findByPlaylistIdOrderByOrderIndexAsc(Long playlistId);
}
