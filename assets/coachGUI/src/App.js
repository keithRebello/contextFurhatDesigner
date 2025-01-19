import React, { useState, useEffect } from 'react';
import { Route, Routes, Navigate, useNavigate, Outlet } from 'react-router-dom';
import FurhatGUI from 'furhat-gui';
import WelcomeScreen from './WelcomeScreen';
import CharacterSelectionScreen from './CharacterSelectionScreen';
import AdminPage from './AdminPage';
import MoodSelectionScreen from './MoodSelectionScreen';
import TransitionScreen from './TransitionScreen';
import PhysicalSensationScreen from './PhysicalSensationScreen';
import SessionLayout from './SessionLayout';
import ScenarioDisplay from "./ScenarioDisplay";
import ScenarioRating from "./ScenarioRating";
import ScenarioScaling from "./ScenarioScaling";
import ScenarioQuestions from "./ScenarioQuestions";

const App = () => {
  const [iteration, setIteration] = useState(1); // Current iteration (1-8)
  const [currentScenarioScreen, setCurrentScenarioScreen] = useState("scenarioDisplay"); // Current screen
  const navigate = useNavigate();

  // Function to handle navigation between scenario screens
  const goToNextScreen = () => {
    if (currentScenarioScreen === "scenarioDisplay") {
      setCurrentScenarioScreen("scenarioRating");
    } else if (currentScenarioScreen === "scenarioRating") {
      setCurrentScenarioScreen("scenarioScaling");
    } else if (currentScenarioScreen === "scenarioScaling") {
      setCurrentScenarioScreen("scenarioQuestions");
    } else if (currentScenarioScreen === "scenarioQuestions") {
      if (iteration < 8) {
        setIteration(iteration + 1); // Increment iteration
        setCurrentScenarioScreen("scenarioDisplay"); // Reset to the first screen
      } else {
        alert("You have completed all scenarios!");
        // Navigate to a final screen or summary if needed
      }
    }
  };

  // Function to render the current scenario screen
  const renderScenarioScreen = () => {
    switch (currentScenarioScreen) {
      case "scenarioDisplay":
        return <ScenarioDisplay onDone={goToNextScreen} />;
      case "scenarioRating":
        return <ScenarioRating onDone={goToNextScreen} />;
      case "scenarioScaling":
        return <ScenarioScaling onDone={goToNextScreen} />;
      case "scenarioQuestions":
        return <ScenarioQuestions onDone={goToNextScreen} />;
      default:
        return <ScenarioDisplay onDone={goToNextScreen} />;
    }
  };

  // Furhat connection and other state management remain unchanged
  const [furhat, setFurhat] = useState(null);
  const [participantId, setParticipantId] = useState('');
  const [isParticipantIdEntered, setIsParticipantIdEntered] = useState(false);
  const [isAdminAuthenticated, setIsAdminAuthenticated] = useState(false);

  useEffect(() => {
    FurhatGUI().then(connection => {
      setFurhat(connection);
    }).catch(error => console.error('Failed to connect to Furhat:', error));
  }, []);

  const handleParticipantIdSubmit = (code) => {
    setParticipantId(code);
    setIsParticipantIdEntered(true);
  };

  return (
      <Routes>
        {/* Welcome/Login Screen */}
        <Route
            path="/"
            element={
              <WelcomeScreen
                  onSubmit={handleParticipantIdSubmit}
                  onAdminAuth={setIsAdminAuthenticated}
                  furhat={furhat}
              />
            }
        />

        {/* Admin Dashboard */}
        <Route
            path="/admin"
            element={
              isAdminAuthenticated ? <AdminPage furhat={furhat} /> : <Navigate to="/" />
            }
        />

        {/* Session routes wrapped in SessionLayout */}
        <Route
            element={
              isParticipantIdEntered ? (
                  <SessionLayout>
                    <Outlet />
                  </SessionLayout>
              ) : (
                  <Navigate to="/" />
              )
            }
        >
          {/* Character Selection */}
          <Route
              path="/select"
              element={
                <CharacterSelectionScreen
                    furhat={furhat}
                />
              }
          />

          {/* Mood Selection */}
          <Route
              path="/mood"
              element={
                <MoodSelectionScreen
                    furhat={furhat}
                />
              }
          />

          {/* Scenario Flow */}
          <Route
              path="/scenarios"
              element={renderScenarioScreen()} // Dynamically render the current scenario screen
          />
        </Route>
      </Routes>
  );
};

export default App;
