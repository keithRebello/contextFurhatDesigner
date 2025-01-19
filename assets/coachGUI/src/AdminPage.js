import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Table, Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './css/AdminPage.css';

/**
 * AdminPage Component
 * Provides interface for managing participant data and system administration
 * Communicates with Furhat robot for participant management
 * 
 * @param {Object} furhat - Furhat robot connection instance
 */
const AdminPage = ({ furhat }) => {
    // State management for participant data
    const [participants, setParticipants] = useState([]); // List of all participants
    const [newParticipantId, setNewParticipantId] = useState(''); // New participant ID input
    const navigate = useNavigate();

    /**
     * Effect hook to initialize participant data
     * Subscribes to participant data updates from Furhat
     */
    useEffect(() => {
        if (furhat) {
            // Subscribe to participant data updates
            furhat.subscribe('furhatos.app.meetfurhat.events.AllParticipantsData', (data) => {
                console.log("DEBUG: Received participants data:", data);
                setParticipants(data.participants);
            });

            // Request initial participant data
            console.log("DEBUG: Requesting participants data");
            furhat.send({ event_name: "REQUEST_ALL_PARTICIPANTS" });
        }
    }, [furhat]);

    /**
     * Handles adding a new participant
     * Sends add participant request to Furhat
     */
    const handleAddParticipant = () => {
        if (newParticipantId && furhat) {
            furhat.send({
                event_name: "ADD_PARTICIPANT",
                participantId: newParticipantId
            });
            setNewParticipantId(''); // Clear input after submission
        }
    };

    /**
     * Handles participant deletion
     * Sends delete request to Furhat
     * @param {string} participantId - ID of participant to delete
     */
    const handleDeleteParticipant = (participantId) => {
        if (furhat) {
            furhat.send({
                event_name: "DELETE_PARTICIPANT",
                participantId: participantId
            });
        }
    };

    return (
        <Container className="admin-container">
            {/* Page Header */}
            <h1 className="admin-title">Admin Dashboard</h1>
            <Button
                variant="secondary"
                onClick={() => navigate('/')}
                className="back-button"
            >
                Back to Welcome Screen
            </Button>

            {/* Add Participant Form */}
            <Row className="add-participant-form">
                <Col md={6}>
                    <Form.Group>
                        <Form.Control
                            type="text"
                            placeholder="Enter new participant ID"
                            value={newParticipantId}
                            onChange={(e) => setNewParticipantId(e.target.value)}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Button onClick={handleAddParticipant}>Add Participant</Button>
                </Col>
            </Row>

            {/* Participants Data Table */}
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Participant ID</th>
                        <th>Character Name</th>
                        <th>Voice Name</th>
                        <th>Mood (Session 1)</th>
                        <th>Mood (Session 2)</th>
                        <th>Mood (Session 3)</th>
                        <th>Completed Sessions</th>
                        <th>Current State</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {participants.map((participant) => (
                        <tr key={participant.id}>
                            <td>{participant.participantId}</td>
                            <td>{participant.characterName || '-'}</td>
                            <td>{participant.voiceName || '-'}</td>
                            <td>{participant.mood_s1 || '-'}</td>
                            <td>{participant.mood_s2 || '-'}</td>
                            <td>{participant.mood_s3 || '-'}</td>
                            <td>{participant.completedSessions || 0}</td>
                            <td>{participant.current_state || '-'}</td>
                            <td>
                                <Button
                                    variant="danger"
                                    size="sm"
                                    onClick={() => handleDeleteParticipant(participant.participantId)}
                                >
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default AdminPage;