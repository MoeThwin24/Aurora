import { createContext, useContext, useRef, useState } from "react";

const PlayerContext = createContext(null);

export function PlayerProvider({ children }) {
  const audioRef = useRef(null);

  const [currentTrack, setCurrentTrack] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);

  const playTrack = (track) => {
    setCurrentTrack(track);
    setIsPlaying(true);

    // Wait for React to update <audio src>, then play
    setTimeout(() => {
      if (audioRef.current) {
        audioRef.current.load();
        audioRef.current.play();
      }
    }, 0);
  };

  const togglePlayPause = () => {
    if (!audioRef.current) return;
    if (isPlaying) {
      audioRef.current.pause();
      setIsPlaying(false);
    } else {
      audioRef.current.play();
      setIsPlaying(true);
    }
  };

  return (
    <PlayerContext.Provider
      value={{
        currentTrack,
        isPlaying,
        playTrack,
        togglePlayPause,
        audioRef,
      }}
    >
      {children}
    </PlayerContext.Provider>
  );
}

export function usePlayer() {
  return useContext(PlayerContext);
}
