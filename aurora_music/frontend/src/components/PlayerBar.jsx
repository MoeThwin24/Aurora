import { useEffect, useState } from "react";
import { usePlayer } from "../context/PlayerContext";

export default function PlayerBar() {
  const { currentTrack, isPlaying, togglePlayPause, audioRef } = usePlayer();
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    const audio = audioRef.current;
    if (!audio) return;

    const updateProgress = () => {
      if (!audio.duration) return;
      setProgress((audio.currentTime / audio.duration) * 100);
    };

    audio.addEventListener("timeupdate", updateProgress);
    return () => audio.removeEventListener("timeupdate", updateProgress);
  }, [audioRef]);

  const seek = (e) => {
    const audio = audioRef.current;
    if (!audio || !audio.duration) return;
    const pct = Number(e.target.value);
    audio.currentTime = (pct / 100) * audio.duration;
    setProgress(pct);
  };

  if (!currentTrack) return null;

  return (
    <div
      style={{
        position: "fixed",
        left: 0,
        right: 0,
        bottom: 0,
        padding: "12px 16px",
        borderTop: "1px solid #ddd",
        background: "white",
        display: "grid",
        gridTemplateColumns: "1fr auto",
        gap: "12px",
        alignItems: "center",
      }}
    >
      <div>
        <div style={{ fontWeight: 700 }}>
          {currentTrack.title}
        </div>
        <div style={{ fontSize: "0.9rem", color: "#555" }}>
          {currentTrack.artist}
        </div>

        <input
          type="range"
          value={progress}
          onChange={seek}
          style={{ width: "100%" }}
        />

        <audio ref={audioRef}>
          <source
            src={`http://localhost:8080/api/tracks/${currentTrack.id}/stream`}
            type="audio/mpeg"
          />
        </audio>
      </div>

      <button
        onClick={togglePlayPause}
        style={{
          padding: "10px 16px",
          borderRadius: "999px",
          border: "none",
          background: "#111",
          color: "white",
          fontWeight: 600,
          cursor: "pointer",
        }}
      >
        {isPlaying ? "Pause" : "Play"}
      </button>
    </div>
  );
}
