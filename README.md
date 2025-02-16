# Robotic Mental Well-Being Coach

Documentation Link: https://docs.google.com/document/d/1y31lzqKXXSwaxT6btsLhsswlI8k8hFHQKPtWTEeOwN4/edit?usp=sharing

A therapeutic coaching application for Furhat robots that provides structured mental health support across multiple sessions, featuring a GUI for user interaction and an admin dashboard for participant management.

## Architecture

### Session Management
- Tracks participant progress across 3 structured sessions
- Maintains participant preferences and configuration
- Handles session completion and state transitions
- Automated mood tracking and session progression

### Database
We use SQLite as our local database for several reasons:
- **Simplicity**: No separate server process or configuration required
- **Portability**: Single-file database, easy to backup and transfer
- **Performance**: Efficient for small to medium datasets
- **No user management**: Simplified setup without authentication needs

The database manages:
- Participant profiles and preferences
- Mood ratings across sessions
- Character/voice selections

### Robot Configuration
- Dynamic character and voice selection
- Automated attention and engagement behaviors
- Session-specific interaction patterns

## Dependencies

- Furhat SDK (2.0.0 or later)
- Java Development Kit (JDK) 11 or later
- SQLite3 (included in most operating systems)
- Node.js (v20.15.1 or later) and npm (v10.7.0 or later) for GUI

## Setup and Installation

1. Clone the repository:
```bash
git clone https://github.com/your-repo/Robotic-Coach.git
cd Robotic-Coach
```
2. Set up the GUI:
```bash
cd assets/coachGUI
npm install
npm run build
```

3. Configure the Furhat SDK:
- Ensure the Furhat SDK is running
- Import the skill through the Furhat Web Interface

4. Database Setup:
- The SQLite database will be automatically created on first run
- Default location: `participants.db` in the root directory

## Session Structure

1. **First Session** (reference to C_Session1.kt)
   - Initial mood assessment
   - Introduction to anxiety concepts
   - Physical sensation awareness

2. **Second Session** (reference to E_Session2.kt)
    - TBD

3. **Final Session** (reference to F_Session3.kt)
    - TBD

## Development

### Key Files
- `SessionManager.kt`: Handles session state and progression
- `Database.kt`: Manages data persistence
- `A_Start.kt` through `G_SessionComplete.kt`: Define interaction flow

### Database Management

Access the database directly using:
```bash
sqlite3 participants.db
```

Or use SQLite GUI tools for advanced operations.

## Admin Dashboard

Access the admin dashboard through the GUI to:
- View participant progress
- Manage participant records
- Monitor mood trends
- View completion statistics
#   c o n t e x t F u r h a t D e s i g n e r  
 