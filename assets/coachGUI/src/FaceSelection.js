import React from 'react';
import './css/fonts.css';

/**
 * FaceSelection Component
 * Displays a grid of character faces for selection
 * 
 * @param {Array} characters - Array of character objects with name and imagePath
 * @param {Object} selectedCharacter - Currently selected character object
 * @param {function} onCharacterSelect - Callback function when a character is selected
 */
const FaceSelection = ({ characters, selectedCharacter, onCharacterSelect }) => {
  return (
    <div className="faces-section">
      <h2 className="aptos-bold">Characters</h2>
      <div className="character-grid">
        {characters.map((character) => (
          <div key={character.name} className="character-item">
            <img
              src={character.imagePath}
              alt={character.name}
              className={`character-image ${selectedCharacter === character ? 'selected' : ''}`}
              onClick={() => onCharacterSelect(character)}
            />
            <p className="character-name aptos-regular">{character.name}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FaceSelection;