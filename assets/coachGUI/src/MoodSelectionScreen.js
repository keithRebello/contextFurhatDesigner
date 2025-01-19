import React, { useState } from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import './css/MoodSelectionScreen.css';

/**
 * MoodSelectionScreen Component
 * Displays a 7-point mood scale with emoji images for mood selection
 * 
 * @param {function} onMoodSelect - Callback function when a mood is selected
 * @param {function} onConfirm - Callback function when selection is confirmed
 * @param {function} onBackToCharacterSelection - Callback function to return to character selection
 */
const MoodSelectionScreen = ({ onMoodSelect, onConfirm, onBackToCharacterSelection }) => {
  // State to track the currently selected mood level (1-7)
  const [selectedMood, setSelectedMood] = useState(null);

  /**
   * Handles the selection of a mood level
   * Updates local state and calls parent callback
   * @param {number} mood - The selected mood level (1-7)
   */
  const handleMoodSelect = (mood) => {
    setSelectedMood(mood);
    onMoodSelect(mood);
  };

  /**
   * Handles the confirmation of mood selection
   * Only triggers if a mood is selected
   */
  const handleSubmit = () => {
    if (selectedMood !== null) {
      onConfirm();
    }
  };

  return (
    <Container className="mood-selection-container">
      {/* Page Title */}
      <Row className="page-title">
        <Col>
          <h1>How are you feeling today?</h1>
        </Col>
      </Row>

      {/* Mood Selection Options */}
      <div className="mood-options">
        {[1, 2, 3, 4, 5, 6, 7].map((mood) => (
          <Col key={mood}>
            <div
              className={`mood-image-container ${selectedMood === mood ? 'selected' : ''}`}
              onClick={() => handleMoodSelect(mood)}
            >
              <img
                src={`../public/mood/${mood}.png`}
                alt={`Mood ${mood}`}
                className="mood-image"
              />
            </div>
            <div className="mood-number">{mood}</div>
          </Col>
        ))}
      </div>

      {/* Submit Button */}
      <Row className="submit-button">
        <Col>
          <Button
            variant="primary"
            onClick={handleSubmit}
            disabled={selectedMood === null}
          >
            Submit
          </Button>
        </Col>
      </Row>
    </Container>
  );
};

export default MoodSelectionScreen;