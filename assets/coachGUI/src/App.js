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

/**
 * Main App Component
 * Manages the application state, routing, and communication with the Furhat robot
 * Handles user flow through different screens and maintains session state
 */
const App = () => {
  // State management for application data and UI control
  const [characters, setCharacters] = useState([]); // Available character options
  const [voices, setVoices] = useState([]); // Available voice options
  const [selectedCharacter, setSelectedCharacter] = useState(null); // Currently selected character
  const [selectedVoice, setSelectedVoice] = useState(null); // Currently selected voice
  const [furhat, setFurhat] = useState(null); // Furhat robot connection instance
  const [participantId, setParticipantId] = useState(''); // Current participant's ID
  const [isParticipantIdEntered, setIsParticipantIdEntered] = useState(false); // Login state
  const [isAdminAuthenticated, setIsAdminAuthenticated] = useState(false); // Admin authentication state
  const [selectedMood, setSelectedMood] = useState(null); // Selected mood value
  const [selectedSensation, setSelectedSensation] = useState(null); // Selected physical sensation

  const navigate = useNavigate();

  /**
   * Initialize Furhat connection and set up event subscriptions
   * Establishes communication channel with the robot
   */
  useEffect(() => {
    FurhatGUI().then(connection => {
      setFurhat(connection);
      setupSubscriptions(connection);
    }).catch(error => console.error('Failed to connect to Furhat:', error));
  }, []);

  /**
   * Configure Furhat event subscriptions
   * Sets up listeners for various robot events that trigger screen changes
   * @param {Object} furhatConnection - The established Furhat connection
   */
  const setupSubscriptions = (furhatConnection) => {
    console.log("DEBUG: Setting up subscriptions");

    // Character and voice data subscription
    furhatConnection.subscribe('furhatos.app.meetfurhat.events.DataDelivery', (data) => {
      console.log("DEBUG: Received character/voice data:", data);
      if (data.characters && data.voices) {
        setCharacters(data.characters);
        setVoices(data.voices);
      } else {
        console.error("DEBUG: Invalid data format received:", data);
      }
    });

    // State-based navigation subscriptions
    const stateScreenMap = {
      'SHOW_MOOD_SCREEN': '/mood',
      'SHOW_SENSATION_SCREEN': '/sensation',
      'SHOW_CHARACTER_SCREEN': '/select',
      'SHOW_TRANSITION_SCREEN': '/transition',
      'SHOW_LOGIN_SCREEN': '/'
    };

    Object.entries(stateScreenMap).forEach(([event, path]) => {
      furhatConnection.subscribe(event, () => {
        console.log(`DEBUG: Received ${event} event`);
        navigate(path);
      });
    });

  };

  /**
   * Handles participant ID submission
   * Validates and processes the participant login
   * @param {string} code - The participant ID entered
   */
  const handleParticipantIdSubmit = (code) => {
    setParticipantId(code);
    if (furhat) {
      furhat.send({
        event_name: "PARTICIPANT_ID_ENTERED",
        data: code
      });
    }
    setIsParticipantIdEntered(true);
  };

  /**
   * Handles character selection
   * Updates the selected character state
   * @param {Object} character - The selected character object
   */
  const handleCharacterSelect = (character) => {
    if (furhat) {
      furhat.send({
        event_name: "CHARACTER_SELECTED",
        data: character.name
      });
    }
    setSelectedCharacter(character);
  };

  /**
   * Handles voice selection
   * Updates the selected voice state
   * @param {Object} voice - The selected voice object
   */
  const handleVoiceSelect = (voice) => {
    if (furhat) {
      furhat.send({
        event_name: "VOICE_SELECTED",
        data: voice.name
      });
    }
    setSelectedVoice(voice);
  };

  /**
   * Handles mood selection
   * Updates the selected mood state
   * @param {number} mood - The selected mood value
   */
  const handleMoodSelect = (mood) => {
    setSelectedMood(mood);
    if (furhat) {
      console.log("DEBUG: Sending MOOD_SELECTED event");
      furhat.send({
        event_name: "MOOD_SELECTED",
        data: mood
      });
    }
  };

  /**
   * Handles mood confirmation
   * Sends the selected mood to Furhat
   */
  const handleMoodConfirm = () => {
    if (furhat && selectedMood !== null) {
      furhat.send({
        event_name: "MOOD_CONFIRMED",
        data: selectedMood,
      });
    }
  };

  /**
   * Handles confirmation of character and voice choices
   * Sends the selections to Furhat and navigates to next screen
   */
  const handleConfirmChoices = () => {
    if (furhat && selectedCharacter && selectedVoice) {
      console.log("DEBUG: Sending CHOICES_CONFIRMED event");
      furhat.send({
        event_name: "CHOICES_CONFIRMED",
        character: selectedCharacter.name,
        voice: selectedVoice.name
      });
      navigate('/transition');
    }
  };

  /**
   * Handles physical sensation selection
   * Updates the selected sensation state
   * @param {string} sensation - The selected sensation
   */
  const handleSensationSelect = (sensation) => {
    setSelectedSensation(sensation);
  };

  /**
   * Handles sensation confirmation
   * Sends the selected sensation to Furhat
   */
  const handleSensationConfirm = () => {
    if (furhat && selectedSensation !== null) {
      furhat.send({
        event_name: "SENSATION_CONFIRMED",
        data: selectedSensation,
      });
    }
  };

  /**
   * Handles admin authentication
   * Updates the admin authentication state
   * @param {boolean} isAuthenticated - Authentication status
   */
  const handleAdminAuthentication = (isAuthenticated) => {
    setIsAdminAuthenticated(isAuthenticated);
  };

  /**
   * Handles navigation back to sign-in screen
   * Resets all relevant state variables
   */
  const handleLeaveSession = () => {
    if (furhat && participantId) {
      furhat.send({
        event_name: "LEAVE_SESSION",
        participantId: participantId,
      });
      setIsParticipantIdEntered(false);
      setSelectedCharacter(null);
      setSelectedVoice(null);
      setParticipantId('');
      navigate('/');
    }
  };

  // Route configuration with authentication guards
  return (
    <Routes>
      {/* Welcome/Login Screen */}
      <Route
        path="/"
        element={
          <WelcomeScreen
            onSubmit={handleParticipantIdSubmit}
            onAdminAuth={handleAdminAuthentication}
            furhat={furhat}
          />
        }
      />

      {/* Admin Dashboard */}
      <Route
        path="/admin"
        element={
          isAdminAuthenticated ?
            <AdminPage furhat={furhat} /> :
            <Navigate to="/" />
        }
      />

      {/* Session routes wrapped in SessionLayout */}
      <Route
        element={
          isParticipantIdEntered ? (
            <SessionLayout onLeaveSession={handleLeaveSession}>
              <Outlet />
            </SessionLayout>
          ) : (
            <Navigate to="/" />
          )
        }
      >
        {/* Character Selection Screen */}
        <Route
          path="/select"
          element={
            <CharacterSelectionScreen
              characters={characters}
              voices={voices}
              selectedCharacter={selectedCharacter}
              selectedVoice={selectedVoice}
              onCharacterSelect={handleCharacterSelect}
              onVoiceSelect={handleVoiceSelect}
              onConfirm={handleConfirmChoices}
            />
          }
        />

        {/* Mood Selection Screen */}
        <Route
          path="/mood"
          element={
            <MoodSelectionScreen
              onMoodSelect={handleMoodSelect}
              onConfirm={handleMoodConfirm}
            />
          }
        />

        {/* Physical Sensation Screen */}
        <Route
          path="/sensation"
          element={
            <PhysicalSensationScreen
              onSensationSelect={handleSensationSelect}
              onConfirm={handleSensationConfirm}
            />
          }
        />

        {/* Transition Screen */}
        <Route
          path="/transition"
          element={<TransitionScreen />}
        />
      </Route>
    </Routes>
  );
};

export default App;