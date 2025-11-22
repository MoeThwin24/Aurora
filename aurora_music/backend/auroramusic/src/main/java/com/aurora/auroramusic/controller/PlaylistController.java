package com.aurora.auroramusic.controller;

import com.aurora.auroramusic.dto.*;
import com.aurora.auroramusic.model.*;
import com.aurora.auroramusic.repo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "http://localhost:5173")
public class PlaylistController {

    private final PlaylistRepository playlistRepo;
    private final PlaylistTrackRepository playlistTrackRepo;
    private final TrackRepository trackRepo;
    private final UserRepository userRepo;

    public PlaylistController(
            PlaylistRepository playlistRepo,
            PlaylistTrackRepository playlistTrackRepo,
            TrackRepository trackRepo,
            UserRepository userRepo
    ) {
        this.playlistRepo = playlistRepo;
        this.playlistTrackRepo = playlistTrackRepo;
        this.trackRepo = trackRepo;
        this.userRepo = userRepo;
    }

    // 1) Get playlists for a user
    @GetMapping
    public List<PlaylistDto> getPlaylists(@RequestParam Long ownerId) {
        return playlistRepo.findByOwnerId(ownerId)
                .stream()
                .map(p -> new PlaylistDto(p.getId(), p.getName(), p.getOwner().getId()))
                .toList();
    }

    // 2) Create playlist
    @PostMapping
    public PlaylistDto createPlaylist(@RequestBody NewPlaylistRequest req) {
        User owner = userRepo.findById(req.ownerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Playlist p = new Playlist();
        p.setName(req.name());
        p.setOwner(owner);

        Playlist saved = playlistRepo.save(p);
        return new PlaylistDto(saved.getId(), saved.getName(), saved.getOwner().getId());
    }

    // 3) Get tracks inside a playlist
    @GetMapping("/{playlistId}/tracks")
    public List<Track> getPlaylistTracks(@PathVariable Long playlistId) {
        return playlistTrackRepo.findByPlaylistIdOrderByOrderIndexAsc(playlistId)
                .stream()
                .map(PlaylistTrack::getTrack)
                .toList();
    }

    // 4) Add track to playlist
    @PostMapping("/{playlistId}/tracks")
    public void addTrack(@PathVariable Long playlistId, @RequestBody AddTrackRequest req) {
        Playlist playlist = playlistRepo.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Track track = trackRepo.findById(req.trackId())
                .orElseThrow(() -> new RuntimeException("Track not found"));

        int orderIndex = playlistTrackRepo.findByPlaylistIdOrderByOrderIndexAsc(playlistId).size();

        PlaylistTrack pt = new PlaylistTrack();
        pt.setPlaylist(playlist);
        pt.setTrack(track);
        pt.setOrderIndex(orderIndex);

        playlistTrackRepo.save(pt);
    }

    // 5) Remove track from playlist
    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public void removeTrack(@PathVariable Long playlistId, @PathVariable Long trackId) {
        List<PlaylistTrack> pts =
                playlistTrackRepo.findByPlaylistIdOrderByOrderIndexAsc(playlistId);

        pts.stream()
           .filter(pt -> pt.getTrack().getId().equals(trackId))
           .findFirst()
           .ifPresent(playlistTrackRepo::delete);
    }
}
