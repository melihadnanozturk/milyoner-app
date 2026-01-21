# Milyoner API Documentation

Complete API reference for the Milyoner quiz game application.

## Base URL

```
http://localhost:8080
```

## Authentication

The application uses JWT (JSON Web Tokens) for authentication. There are two types of tokens:

1. **Game Tokens**: Used for game endpoints (`/game/**`)
   - Obtained from `POST /game/start`
   - Contains `gameId` claim for session tracking
   - Valid for 1 hour (configurable)

2. **Admin Tokens**: Used for admin panel endpoints (`/panel/**`)
   - Obtained from authentication endpoints
   - Contains `roles` claim (ADMIN or USER)
   - Valid for 1 hour (configurable)

### Using Tokens

Include the token in the `Authorization` header:

```
Authorization: Bearer <your-token>
```

## Common Response Format

All API responses follow a standardized format:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "httpStatus": "OK"
}
```

### Success Response

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "httpStatus": "OK"
}
```

### Error Response

```json
{
  "success": false,
  "message": "Operation was crashed",
  "data": "Error details",
  "httpStatus": "BAD_REQUEST"
}
```

## Game API

Base path: `/game`

All endpoints except `/game/start` require authentication with a game token.

### Start Game

Start a new game session.

**Endpoint**: `POST /game/start`

**Authentication**: Not required

**Request Body**:

```json
{
  "username": "player123"
}
```

**Request Schema**:
- `username` (string, required): Player's username

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X POST http://localhost:8080/game/start \
  -H "Content-Type: application/json" \
  -d '{"username": "player123"}'
```

---

### Get Questions

Get the current question for the player's game session.

**Endpoint**: `POST /game/questions`

**Authentication**: Required (Game token)

**Request Body**: None

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "game": {
      "questionLevel": 1
    },
    "question": {
      "questionId": 1,
      "questionText": "İnsanlar namaz kılarken nereye döner ?",
      "answers": [
        {
          "id": 1,
          "text": "Kabe"
        },
        {
          "id": 2,
          "text": "Mescid-i Aksa"
        },
        {
          "id": 3,
          "text": "Kubbetus Sahra"
        },
        {
          "id": 4,
          "text": "Mescidi Nebevi"
        }
      ]
    }
  },
  "httpStatus": "OK"
}
```

**Response Schema**:
- `game.questionLevel` (number): Current question level (1-10)
- `question.questionId` (number): Question ID
- `question.questionText` (string): Question text
- `question.answers` (array): Array of answer objects
  - `id` (number): Answer ID
  - `text` (string): Answer text

**Notes**:
- Answers are shuffled before being returned
- Only active answers are included
- First call transitions game state from `START_GAME` to `IN_PROGRESS` and sets level to 1

**cURL Example**:

```bash
curl -X POST http://localhost:8080/game/questions \
  -H "Authorization: Bearer <game-token>" \
  -H "Content-Type: application/json"
```

---

### Submit Answer

Submit an answer to the current question.

**Endpoint**: `POST /game/answer`

**Authentication**: Required (Game token)

**Request Body**:

```json
{
  "questionId": 1,
  "answerId": 1
}
```

**Request Schema**:
- `questionId` (number, required): ID of the question being answered
- `answerId` (number, required): ID of the selected answer

**Response** (Correct Answer):

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "questionLevel": 2,
    "gameState": "IN_PROGRESS"
  },
  "httpStatus": "OK"
}
```

**Response** (Wrong Answer):

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "questionLevel": null,
    "gameState": "LOST"
  },
  "httpStatus": "OK"
}
```

**Response** (Game Won - Level 10 completed):

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "questionLevel": 10,
    "gameState": "WON"
  },
  "httpStatus": "OK"
}
```

**Response Schema**:
- `questionLevel` (number|null): Updated question level (null if game ended)
- `gameState` (string): Current game state (`IN_PROGRESS`, `WON`, `LOST`)

**Game States**:
- `IN_PROGRESS`: Answer was correct, game continues
- `WON`: Completed all 10 levels
- `LOST`: Answer was incorrect, game ended

**cURL Example**:

```bash
curl -X POST http://localhost:8080/game/answer \
  -H "Authorization: Bearer <game-token>" \
  -H "Content-Type: application/json" \
  -d '{"questionId": 1, "answerId": 1}'
```

---

### Get Game Result

Get the final result of the completed game.

**Endpoint**: `POST /game/result`

**Authentication**: Required (Game token)

**Request Body**: None

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "username": "player123",
    "score": 10,
    "message": "OYUNU KAZANDINIZ"
  },
  "httpStatus": "OK"
}
```

**Response Schema**:
- `username` (string): Player's username
- `score` (number): Final score (question level reached)
- `message` (string): Result message
  - `"OYUNU KAZANDINIZ"` - Game won
  - `"OYUNU KAYBETTİNİZ"` - Game lost

**Notes**:
- Can only be called when game state is `WON` or `LOST`
- Returns error if game is still `IN_PROGRESS`

**cURL Example**:

```bash
curl -X POST http://localhost:8080/game/result \
  -H "Authorization: Bearer <game-token>" \
  -H "Content-Type: application/json"
```

---

## Admin Panel API

Base path: `/panel`

### Authentication Endpoints

#### Login

Authenticate and receive an admin token.

**Endpoint**: `POST /panel/auth/login`

**Authentication**: Not required

**Request Body**:

```json
{
  "username": "admin",
  "password": "password123"
}
```

**Request Schema**:
- `username` (string, required): Username
- `password` (string, required): Password

**Response**:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Returns a JWT token string (not JSON).

**cURL Example**:

```bash
curl -X POST http://localhost:8080/panel/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password123"}'
```

---

#### Register Admin

Register a new admin user.

**Endpoint**: `POST /panel/auth/admin/register`

**Authentication**: Not required

**Request Body**:

```json
{
  "username": "newadmin",
  "password": "securepassword"
}
```

**Response**:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Returns a JWT token string.

**cURL Example**:

```bash
curl -X POST http://localhost:8080/panel/auth/admin/register \
  -H "Content-Type: application/json" \
  -d '{"username": "newadmin", "password": "securepassword"}'
```

---

#### Register User

Register a new regular user.

**Endpoint**: `POST /panel/auth/user/register`

**Authentication**: Not required

**Request Body**:

```json
{
  "username": "newuser",
  "password": "password123"
}
```

**Response**:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Returns a JWT token string.

**cURL Example**:

```bash
curl -X POST http://localhost:8080/panel/auth/user/register \
  -H "Content-Type: application/json" \
  -d '{"username": "newuser", "password": "password123"}'
```

---

### Question Management

Base path: `/panel/questions`

#### Get All Questions

Retrieve all questions with optional filtering and pagination.

**Endpoint**: `POST /panel/questions`

**Authentication**: Required (Admin token)

**Request Body**:

```json
{
  "questionLevel": 1,
  "sorter": "LEVEL",
  "pageNumber": 0,
  "pageSize": 20
}
```

**Request Schema**:
- `questionLevel` (number, optional): Filter by question level
- `sorter` (string, optional): Sort field (`TEXT` or `LEVEL`)
- `pageNumber` (number, optional): Page number (default: 0)
- `pageSize` (number, optional): Page size (default: 20)

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": [
    {
      "questionId": 1,
      "questionText": "İnsanlar namaz kılarken nereye döner ?",
      "questionLevel": 1,
      "isActivate": true
    },
    {
      "questionId": 2,
      "questionText": "Allah kaç isme sahiptir",
      "questionLevel": 2,
      "isActivate": true
    }
  ],
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X POST http://localhost:8080/panel/questions \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{"pageNumber": 0, "pageSize": 20}'
```

---

#### Get Question by ID

Retrieve a specific question with all its answers.

**Endpoint**: `GET /panel/questions/{id}`

**Authentication**: Required (Admin token)

**Path Parameters**:
- `id` (number): Question ID

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "questionId": 1,
    "questionText": "İnsanlar namaz kılarken nereye döner ?",
    "questionLevel": 1,
    "isActivate": true,
    "answers": [
      {
        "answerId": 1,
        "answerText": "Kabe",
        "isCorrect": true,
        "isActivate": true
      },
      {
        "answerId": 2,
        "answerText": "Mescid-i Aksa",
        "isCorrect": false,
        "isActivate": true
      }
    ]
  },
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X GET http://localhost:8080/panel/questions/1 \
  -H "Authorization: Bearer <admin-token>"
```

---

#### Create Question

Create a new question with answers.

**Endpoint**: `POST /panel/questions/operation`

**Authentication**: Required (Admin token with ADMIN role)

**Request Body**:

```json
{
  "questionText": "What is the capital of France?",
  "questionLevel": 1,
  "activate": true,
  "answers": [
    {
      "answerText": "Paris",
      "isCorrect": true
    },
    {
      "answerText": "London",
      "isCorrect": false
    },
    {
      "answerText": "Berlin",
      "isCorrect": false
    },
    {
      "answerText": "Madrid",
      "isCorrect": false
    }
  ]
}
```

**Request Schema**:
- `questionText` (string, required): Question text
- `questionLevel` (number, required): Question level (1-10)
- `activate` (boolean, optional): Whether question is active (default: true)
- `answers` (array, required): Array of answers (4-10 answers required)
  - `answerText` (string, required): Answer text
  - `isCorrect` (boolean, required): Whether answer is correct

**Validation Rules**:
- Question level must be between 1 and 10
- Must have between 4 and 10 answers
- At least one answer must be marked as correct

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "questionId": 4,
    "questionText": "What is the capital of France?",
    "questionLevel": 1,
    "answers": [
      {
        "answerId": 10,
        "answerText": "Paris",
        "isCorrect": true,
        "isActivate": true
      },
      {
        "answerId": 11,
        "answerText": "London",
        "isCorrect": false,
        "isActivate": true
      }
    ]
  },
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X POST http://localhost:8080/panel/questions/operation \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "questionText": "What is the capital of France?",
    "questionLevel": 1,
    "answers": [
      {"answerText": "Paris", "isCorrect": true},
      {"answerText": "London", "isCorrect": false},
      {"answerText": "Berlin", "isCorrect": false},
      {"answerText": "Madrid", "isCorrect": false}
    ]
  }'
```

---

#### Update Question

Update an existing question.

**Endpoint**: `PUT /panel/questions/operation/{questionId}`

**Authentication**: Required (Admin token with ADMIN role)

**Path Parameters**:
- `questionId` (number): Question ID to update

**Request Body**:

```json
{
  "questionText": "Updated question text",
  "questionLevel": 2,
  "isActivate": true
}
```

**Request Schema**:
- `questionText` (string, required): Updated question text
- `questionLevel` (number, required): Updated question level (1-10)
- `isActivate` (boolean, required): Whether question is active

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "questionId": 1,
    "questionText": "Updated question text",
    "questionLevel": 2,
    "isActivate": true
  },
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X PUT http://localhost:8080/panel/questions/operation/1 \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "questionText": "Updated question text",
    "questionLevel": 2,
    "isActivate": true
  }'
```

---

#### Delete Question

Delete a question (cascades to answers).

**Endpoint**: `DELETE /panel/questions/operation/{questionId}`

**Authentication**: Required (Admin token with ADMIN role)

**Path Parameters**:
- `questionId` (number): Question ID to delete

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": "Question deleted successfully",
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X DELETE http://localhost:8080/panel/questions/operation/1 \
  -H "Authorization: Bearer <admin-token>"
```

---

### Answer Management

Base path: `/panel/answers`

#### Get Answer by ID

Retrieve a specific answer.

**Endpoint**: `GET /panel/answers/{id}`

**Authentication**: Required (Admin token)

**Path Parameters**:
- `id` (number): Answer ID

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "answerId": 1,
    "answerText": "Kabe",
    "isCorrect": true,
    "isActivate": true
  },
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X GET http://localhost:8080/panel/answers/1 \
  -H "Authorization: Bearer <admin-token>"
```

---

#### Create Answer

Create a new answer for a question.

**Endpoint**: `POST /panel/answers/operation`

**Authentication**: Required (Admin token with ADMIN role)

**Request Body**:

```json
{
  "questionId": 1,
  "answerText": "New answer option",
  "isCorrect": false,
  "isActive": true
}
```

**Request Schema**:
- `questionId` (number, required): ID of the question this answer belongs to
- `answerText` (string, required): Answer text
- `isCorrect` (boolean, required): Whether this answer is correct
- `isActive` (boolean, required): Whether this answer is active

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "answerId": 15,
    "answerText": "New answer option",
    "isCorrect": false,
    "isActivate": true
  },
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X POST http://localhost:8080/panel/answers/operation \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "questionId": 1,
    "answerText": "New answer option",
    "isCorrect": false,
    "isActive": true
  }'
```

---

#### Delete Answer

Delete an answer.

**Endpoint**: `DELETE /panel/answers/operation/{id}`

**Authentication**: Required (Admin token with ADMIN role)

**Path Parameters**:
- `id` (number): Answer ID to delete

**Response**:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": "Answer deleted successfully",
  "httpStatus": "OK"
}
```

**cURL Example**:

```bash
curl -X DELETE http://localhost:8080/panel/answers/operation/1 \
  -H "Authorization: Bearer <admin-token>"
```

---

## Error Codes

The application uses standardized error codes:

### Common Errors

| Code | Message | Description |
|------|---------|-------------|
| 2001 | Record not exists | Requested resource was not found |

### Game Play Errors

| Code | Message | Description |
|------|---------|-------------|
| 5001 | Answer is incorrect | The submitted answer is wrong |
| 6001 | Player Status is incorrect | Game is not in a valid state for the requested operation |

### HTTP Status Codes

- `200 OK`: Request successful
- `400 BAD_REQUEST`: Invalid request (validation errors)
- `401 UNAUTHORIZED`: Missing or invalid authentication token
- `403 FORBIDDEN`: Insufficient permissions (e.g., non-admin trying admin operation)
- `500 INTERNAL_SERVER_ERROR`: Server error

### Error Response Format

```json
{
  "success": false,
  "message": "Operation was crashed",
  "data": "Detailed error message",
  "httpStatus": "BAD_REQUEST"
}
```

### Validation Errors

When request validation fails:

```json
{
  "success": false,
  "message": "Operation was crashed",
  "data": "Validation error message",
  "httpStatus": "BAD_REQUEST"
}
```

## Game Flow Example

Complete game flow example:

```bash
# 1. Start a new game
TOKEN=$(curl -s -X POST http://localhost:8080/game/start \
  -H "Content-Type: application/json" \
  -d '{"username": "player123"}' | jq -r '.data.token')

# 2. Get first question
curl -X POST http://localhost:8080/game/questions \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# 3. Submit answer (assuming questionId=1, answerId=1)
curl -X POST http://localhost:8080/game/answer \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"questionId": 1, "answerId": 1}'

# 4. Continue answering questions until game ends...

# 5. Get final result
curl -X POST http://localhost:8080/game/result \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

## Notes

- All timestamps are in UTC
- JWT tokens expire after 1 hour (configurable)
- Game sessions are tracked via `gameId` in the JWT token
- Questions must have at least 4 active answers (1 correct + 3 incorrect)
- Question levels range from 1 to 10
- Only active questions and answers are used in games
- Admin operations require ADMIN role in the JWT token
