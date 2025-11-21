package com.aurora.auroramusic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="playlists")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="owner_id")
    private User owner;
    
}
