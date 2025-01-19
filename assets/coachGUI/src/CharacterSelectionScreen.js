import React, { useState } from 'react';
import { Container, Row, Col, Button, Alert } from 'react-bootstrap';
import FaceSelection from './FaceSelection';
import VoiceSelection from './VoiceSelection';
import './css/fonts.css';
import './css/CharacterSelectionScreen.css';

/**
 * CharacterSelectionScreen Component
 * Allows users to select a character face and voice for the Furhat robot
 * 
 * @param {Array} characters - Available character options
 * @param {Array} voices - Available voice options
 * @param {Object} selectedCharacter - Currently selected character
 * @param {Object} selectedVoice - Currently selected voice
 * @param {function} onCharacterSelect - Callback for character selection
 * @param {function} onVoiceSelect - Callback for voice selection
 * @param {function} onConfirm - Callback for confirming selections
 */
const CharacterSelectionScreen = ({
  characters,
  voices,
  selectedCharacter,
  selectedVoice,
  onCharacterSelect,
  onVoiceSelect,
  onConfirm,
}) => {
  const [showConfirmation, setShowConfirmation] = useState(false);

  const handleConfirm = () => {
    if (selectedCharacter && selectedVoice) {
      onConfirm(selectedCharacter, selectedVoice);
      setShowConfirmation(true);
    }
  };

  return (
    <Container className="character-selection-container">
      <Row className="page-title">
        <Col>
          <h1 className="aptos-bold">Choose Your Character and Voice</h1>
        </Col>
      </Row>

      {showConfirmation && (
        <Row>
          <Col>
            <Alert variant="success" className="aptos-regular">
              Your choices have been confirmed. Returning to sign-in page...
            </Alert>
          </Col>
        </Row>
      )}

      <div className="selection-content">
        <div className="faces-section">
          <FaceSelection
            characters={characters}
            selectedCharacter={selectedCharacter}
            onCharacterSelect={onCharacterSelect}
          />
        </div>

        <div className="voices-section">
          <VoiceSelection
            voices={voices}
            selectedVoice={selectedVoice}
            onVoiceSelect={onVoiceSelect}
          />
        </div>
      </div>

      <Row className="confirm-button">
        <Col className="text-center">
          <Button
            variant="primary"
            size="lg"
            onClick={handleConfirm}
            disabled={!selectedCharacter || !selectedVoice}
            className="aptos-regular"
          >
            Confirm Selection
          </Button>
        </Col>
      </Row>
    </Container>
  );
};

export default CharacterSelectionScreen;