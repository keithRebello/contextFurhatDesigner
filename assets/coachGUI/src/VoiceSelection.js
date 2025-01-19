import React from 'react';
import { FaVolumeUp } from 'react-icons/fa';
import './css/fonts.css';

const VoiceSelection = ({ voices, selectedVoice, onVoiceSelect }) => {
  return (
    <div className="voices-section">
      <h2 className="aptos-bold">Voices</h2>
      <div className="voice-list">
        {voices.map((voice) => (
          <div
            key={voice.name}
            className={`voice-item ${selectedVoice === voice ? 'selected' : ''}`}
            onClick={() => onVoiceSelect(voice)}
          >
            <FaVolumeUp
              size={40}
              className="voice-icon"
            />
            <p className="voice-name aptos-regular">{voice.name}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default VoiceSelection;