import { useEffect, useState } from "react";
import { usePlayer } from "../context/PlayerContext";

export default function PlayerBar() {
  const { currentTrack, isPlaying, togglePlayPause, audioRef } = usePlayer();
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    const audio = audioRef.current;
    if (!audio) return;

    const update = () => {
      if (!audio.duration) return;
      setProgress((audio.currentTime / audio.duration) * 100);
    };

    audio.addEventListener("timeupdate", update);
    return () => audio.removeEventListener("timeupdate", update);
  }, [audioRef]);

  const seek = (e) => {
    const audio = audioRef.current;
    const pct = Number(e.target.value);
    audio.currentTime = (pct / 100) * audio.duration;
    setProgress(pct);
  };

  if (!currentTrack) return null;

  return (
    <div style={{
      position: "fixed",
      left: 0,
      right: 0,
      bottom: 0,
      padding: "12px 16px",
      borderTop: "1px solid #ddd",
      background: "white",
    }}>
      <strong>{currentTrack.title}</strong>
      <div>{currentTrack.artist}</div>

      <input type="range" value={progress} onChange={seek} style={{ width: "100%" }} />

      <audio ref={audioRef}>
        <source src={`http://localhost:8080/api/tracks/${currentTrack.id}/stream`} type="audio/mpeg" />
      </audio>

      <button onClick={togglePlayPause}
        style={{ marginTop: "10px", padding: "10px 16px", background: "#111", color: "#fff", border: "none", borderRadius: "8px" }}>
        {isPlaying ? "Pause" : "Play"}
      </button>
    </div>
  );
}
