import React, { useState } from 'react';
import { Container, Form, Button, Modal, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './css/WelcomeScreen.css';

/**
 * WelcomeScreen Component
 * Handles user authentication and admin access
 * Serves as the entry point for participants and administrators
 * Communicates with Furhat robot for participant validation
 * 
 * @param {function} onSubmit - Callback for participant authentication
 * @param {function} onAdminAuth - Callback for admin authentication
 * @param {Object} furhat - Furhat robot connection instance
 */
const WelcomeScreen = ({ onSubmit, onAdminAuth, furhat }) => {
  // State management for form inputs and error handling
  const [participationCode, setParticipationCode] = useState(''); // Participant ID input
  const [showAdminModal, setShowAdminModal] = useState(false); // Admin modal visibility
  const [adminPassword, setAdminPassword] = useState(''); // Admin password input
  const [participantError, setParticipantError] = useState(''); // Participant validation error
  const [adminError, setAdminError] = useState(''); // Admin authentication error
  
  const navigate = useNavigate();

  /**
   * Handles participant authentication submission
   * Validates participant ID with Furhat robot through websocket communication
   * @param {Event} e - Form submission event
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setParticipantError('');
    console.log("DEBUG: Handling participant submit with code:", participationCode);

    if (furhat) {
      // Send validation request to Furhat
      furhat.send({
        event_name: "CHECK_PARTICIPANT_EXISTS",
        participantId: participationCode
      });

      // Listen for validation response from Furhat
      furhat.subscribe('furhatos.app.meetfurhat.events.ParticipantExistsResponse', (data) => {
        console.log("DEBUG: Received ParticipantExistsResponse:", data);
        if (data.exists) {
          onSubmit(participationCode);
        } else {
          setParticipantError('Invalid participant ID. Please enter a valid ID.');
        }
      });
    } else {
      setParticipantError('Error: Unable to communicate with Furhat.');
    }
  };

  /**
   * Handles admin authentication
   * Validates admin password and grants access to admin dashboard
   * Default password is 'admin' as per Furhat documentation
   */
  const handleAdminSubmit = () => {
    if (adminPassword === 'admin') {
      setShowAdminModal(false);
      onAdminAuth(true);
      navigate('/admin');
    } else {
      setAdminError('Incorrect password');
    }
  };

  return (
    <Container fluid className="welcome-container">
      {/* Main content wrapper */}
      <div className="content-wrapper">
        {/* Left panel - Login form */}
        <div className="left-panel">
          <h1 className="welcome-title">Welcome!</h1>
          <Form onSubmit={handleSubmit} className="styled-form">
            <Form.Group controlId="participationCode" className="mb-3">
              <Form.Label>Enter your participation code:</Form.Label>
              <Form.Control
                type="text"
                value={participationCode}
                onChange={(e) => setParticipationCode(e.target.value)}
                placeholder="Enter code"
              />
            </Form.Group>
            {participantError && <Alert variant="danger">{participantError}</Alert>}
            <Button variant="primary" type="submit" className="styled-button">
              Sign in
            </Button>
          </Form>
        </div>
        
        {/* Right panel - Decorative image */}
        <div className="right-panel">
          <img src="../public/meditation.png" alt="Meditation" className="meditation-image" />
        </div>
      </div>

      {/* Admin access button */}
      <Button variant="secondary" onClick={() => setShowAdminModal(true)} className="admin-button">
        Admin Dashboard
      </Button>

      {/* Admin authentication modal */}
      <Modal show={showAdminModal} onHide={() => setShowAdminModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Admin Authentication</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group>
            <Form.Label>Enter admin password:</Form.Label>
            <Form.Control
              type="password"
              value={adminPassword}
              onChange={(e) => setAdminPassword(e.target.value)}
              placeholder="Enter password"
            />
          </Form.Group>
          {adminError && <Alert variant="danger" className="mt-3">{adminError}</Alert>}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowAdminModal(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleAdminSubmit}>
            Submit
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default WelcomeScreen;