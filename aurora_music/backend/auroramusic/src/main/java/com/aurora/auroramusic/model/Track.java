package com.aurora.auroramusic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tracks")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Track {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;
    private String album;
    private Integer durationSeconds;

    @Column(nullable = false)
    private String filePath;
}
