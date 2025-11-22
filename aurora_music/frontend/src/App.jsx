import TrackList from "./components/TrackList";
import PlayerBar from "./components/PlayerBar";

export default function App() {
  return (
    <div style={{ padding: "20px", paddingBottom: "120px", fontFamily: "sans-serif" }}>
      <h1>Aurora Music</h1>
      <TrackList />
      <PlayerBar />
    </div>
  );
}
