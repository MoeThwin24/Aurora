package com.aurora.auroramusic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="playlist_tracks",
       uniqueConstraints = @UniqueConstraint(columnNames = {"playlist_id","track_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PlaylistTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne(optional=false)
    @JoinColumn(name = "track_id")
    private Track track;

    private Integer orderIndex;
 
}
