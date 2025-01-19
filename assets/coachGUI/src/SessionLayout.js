import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Button } from 'react-bootstrap';

const SessionLayout = ({ children, onLeaveSession }) => {
    return (
        <div>
            <Button
                variant="warning"
                className="position-fixed top-0 end-0 m-3"
                onClick={onLeaveSession}
            >
                Pause Session
            </Button>
            {children}
        </div>
    );
};

export default SessionLayout;