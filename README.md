# Bar Padilla Tatooine - Domino Game

Welcome to the Tatooine Cantina! This project is a multi-player Domino game built with Hexagonal Architecture, Java 21, Spring Boot, and Angular.

## Prerequisites
- **Java 21**: Installed via `sdkman` (recommended).
- **Node.js 20+** and **npm**.
- **Maven 3.9+**.

## Local Testing Instructions

### 1. Start the Backend (Spring Boot)
Ensure you are using Java 21.
```bash
# From the project root
./mvnw spring-boot:run
```
The server will start at `http://localhost:8080`. You can verify Swagger at `http://localhost:8080/swagger-ui/index.html`.

### 2. Start the Frontend (Angular)
```bash
# From the project root
cd frontend
npm install
npm start
```
The application will be available at `http://localhost:4200`.

### 3. How to Play (Quick Test)
1. Open `http://localhost:4200` in your browser.
2. In the center of the board, click the **"START NEW GAME"** button. This will automatically initialize the game and deal the tiles.
3. Once the game starts, you (as Player1) will see your 7 tiles at the bottom.
4. You can also still use the API call manually if needed:
   ```bash
   curl -X POST http://localhost:8080/api/domino/start/tatooine-1 \
   -H "Content-Type: application/json" \
   -d '["Player1", "Player2", "Player3", "Player4"]'
   ```
4. Once sent, the frontend for `Player1` (default) will receive 7 tiles and the cantina atmosphere will begin.

---
**Next Step:** Once local verification is successful, we will proceed with Docker configuration and Render deployment.
