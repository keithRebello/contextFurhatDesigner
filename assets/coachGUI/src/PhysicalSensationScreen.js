import React, { useState } from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import './css/PhysicalSensationScreen.css';

/**
 * PhysicalSensationScreen Component
 * Displays a list of physical anxiety symptoms for user selection
 * 
 * @param {function} onSensationSelect - Callback function when a sensation is selected
 * @param {function} onConfirm - Callback function when selection is confirmed
 */
const PhysicalSensationScreen = ({ onSensationSelect, onConfirm }) => {
  // State to track the currently selected sensation
  const [selectedSensation, setSelectedSensation] = useState(null);

  // List of available physical sensations related to anxiety
  const sensations = [
    'Dry mouth',
    'Heart racing',
    'Chills, hot flushes',
    'Feeling warm, sweaty',
    'Chest pain or pressure',
    'Lightheaded, faint, dizzy',
    'Nausea, upset stomach, diarrhea',
    'Tingling or numbness in arms or legs',
    'Difficulty swallowing, choking sensation',
  ];

  /**
   * Handles the selection of a sensation
   * Updates local state and calls parent callback
   * @param {string} sensation - The selected sensation
   */
  const handleSensationSelect = (sensation) => {
    setSelectedSensation(sensation);
    onSensationSelect(sensation);
  };

  /**
   * Handles the confirmation of selection
   * Only triggers if a sensation is selected
   */
  const handleSubmit = () => {
    if (selectedSensation !== null) {
      onConfirm();
    }
  };

  return (
    <Container className="sensation-container">
      {/* Header Section */}
      <Row className="page-title">
        <Col>
          <h2>Physical Sensations of Anxiety</h2>
          <p>Select any sensation you've experienced:</p>
        </Col>
      </Row>

      {/* Sensation Options Section */}
      <Row className="sensation-options">
        <Col md={6} className="mx-auto">
          {sensations.map((sensation) => (
            <Button
              key={sensation}
              variant={selectedSensation === sensation ? "success" : "outline-success"}
              onClick={() => handleSensationSelect(sensation)}
              className="sensation-button mb-2"
            >
              {sensation}
            </Button>
          ))}
        </Col>
      </Row>

      {/* Confirmation Button Section */}
      <Row className="submit-section">
        <Col>
          <Button
            variant="success"
            onClick={handleSubmit}
            disabled={selectedSensation === null}
          >
            Confirm Selection
          </Button>
        </Col>
      </Row>
    </Container>
  );
};

export default PhysicalSensationScreen;