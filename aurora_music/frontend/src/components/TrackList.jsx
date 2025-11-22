import { useEffect, useState } from "react";
import client from "../api/client";
import { usePlayer } from "../context/PlayerContext";

export default function TrackList() {
  const [tracks, setTracks] = useState([]);
  const [loading, setLoading] = useState(true);
  const { playTrack, currentTrack, isPlaying } = usePlayer();

  useEffect(() => {
    client
      .get("/tracks")
      .then((res) => setTracks(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading tracks...</p>;

  return (
    <div style={{ display: "grid", gap: "12px" }}>
      {tracks.map((t) => {
        const active = currentTrack?.id === t.id;
        return (
          <div
            key={t.id}
            style={{
              padding: "12px",
              border: "1px solid #ddd",
              borderRadius: "10px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              background: active ? "#f3f3f3" : "white",
            }}
          >
            <div>
              <div style={{ fontWeight: 600 }}>{t.title}</div>
              <div style={{ fontSize: "0.9rem", color: "#555" }}>
                {t.artist} • {t.album}
              </div>
            </div>

            <button
              onClick={() => playTrack(t)}
              style={{
                padding: "6px 12px",
                borderRadius: "8px",
                border: "none",
                background: "#111",
                color: "white",
                cursor: "pointer",
              }}
            >
              {active && isPlaying ? "Playing" : "Play"}
            </button>
          </div>
        );
      })}
    </div>
  );
}
