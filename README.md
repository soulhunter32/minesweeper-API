# :bomb: Minesweeper API

---

Minesweeper API is a Java REST API that emulates the classic and well-known [Minesweeper game](https://en.wikipedia.org/wiki/Minesweeper_(video_game)), developed with a wide range of technologies:
- Java 9
- Spring Boot 2 (Spring REST + Spring Security + JPA + Lombok)
- JWT
- Swagger 2 + Springfox UI
- JUnit 4 + Mockito 2 + PowerMock
- Maven
- Kotlin Client - Kotlin v1.3.72
- Deployed on AWS Beanstalk
- [Docsify](https://docsify.js.org/#/) documentation hosted on AWS Amplify

## Test environment
The API has been deployed on AWS Beanstalk and can be accessed in the following URL:
:link: http://minesweeperapi-env-1.eba-yzizmngx.us-east-2.elasticbeanstalk.com/minesweeper

## GitHub Repository
:link: https://github.com/soulhunter32/minesweeper-API

## Application Docsify
:link: [Readme Documentation](https://master.dqfof35q5pvab.amplifyapp.com)

## Project Structure
The application consist of a main Java API core and a Kotlin client to consume it, split into a multi-module Maven project. 
* API Core Module: `api-core`
* Kotlin Client Module: `kotlin-client`
> Kotlin client is mean to be included in a project to be used. Currently, it has a **_disabled_** SpringBoot client application
> in order to make local tests

## Features
* Ability to log into the application with `username` and `password`. The JWT token has a duration of 15 minutes.
* Ability to create a new user for the game. Multiple users can be created, each with a set of games.
* Ability to crate a new game for a specific user with a set of settings:
  * Width size
  * Height size
  * Amount of mines
* A user can start multiple new games, but the elapsed time will continue till the game ends.
* Ability to reveal a cell based on X and Y coordinates.
* Ability to flag a cell based on X and Y coordinates and a type.
The flag type can be `RED_FLAG` or `QUESTION_MARK`
* Time tracking, each game has a start, end and elapsed time set once the game has finished (win/lost game).
* Game difficulty level can be also configured, but it's not available for user access.

## Installation
* Clone the repository:
   * `git clone https://github.com/soulhunter32/minesweeper-API.git`
* Build the app
    * `cd minesweeper-API`
    * `mvn clean install`

## Run API server
* The API project contains an embedded Tomcat server. To run it, execute the following commands:
   * `cd /api-core`
   * `mvn spring-boot:run`
* The API entry point will be accessible on http://localhost:5000/minesweeper
 
## Swagger API Documentation
:link: [Online API Doc](http://minesweeperapi-env-1.eba-yzizmngx.us-east-2.elasticbeanstalk.com/minesweeper/swagger-ui.html) <br>
Locally: http://localhost:5000/minesweeper/swagger-ui.html¨

## Game Instructions
* Before start, the user needs to log into the application with `username` and `password`
* In order to start a game, a new user must be crated. If the user does not exist and a new game or a flag/reveal instruction is sent, an error message will appear.
* Once the user is created, a new game must be sent with its settings: `board width, height and amount of mines`. If the amount of mines exceeds the default 35% of the board size, an error will appear.
* From now on, the user can send cell reveal/flag coordinates to a game Ability to reveal a cell based on X and Y coordinates
* Ability to flag a cell based on X and Y coordinates and a type.
The flag type can be `RED_FLAG` or `QUESTION_MARK`
Game difficulty level can be also configured, but it's not available for user access

## Endpoints
###  Authentication
Logs into the application with `username` and `password` supplied in the request.

    POST /minesweeper/authenticate

#### Request
Headers
> <b>Content-Type:</b> "application/json" <br>

- Login Object
```json
{
	"username": "minesweeper-api-core-user",
	"password": "password"
}
```
#### Response
- Object with generated token
```json
{
	"jwttoken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5lc3dlZXBlci1hcGktY29yZS11c2VyIiwiZXhwIjoxNjA0MjkyNTU0LCJpYXQiOjE2MDQyOTE2NTR9.EH8L2PLgbJBLGNtPN1SvXre-hBHROGXz1KjMiDUTikbRQ0wAKecGzfXomGFDAE5IJ_rHe51BM0zy3ShseL8evg"
}
```
### New User
Creates a new user for the  `username` supplied in the request.
Headers
> <b>Content-Type:</b> "application/json" <br>
> <b>Authorization:</b> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWI..."

    POST /minesweeper/users

#### Request
Headers
> <b>Content-Type:</b> "application/json" <br>
> <b>Authorization:</b> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWI..."

- User
```json
{"username": "user01"}
```
#### Response
- User
```json
{"id":"1", "username": "user01"}
```

### New Game
Creates a game with the board settings provided, for the user  `userId` supplied in the path. 
It populates the board with the settings provided, creating a X*Y matrix using the width and height from the settings.
It also randomly populates the mines and completes the adjacent information for each cell.

    POST /minesweeper/users/{userId}

#### Request
Headers
> <b>Content-Type:</b> "application/json" <br>
> <b>Authorization:</b> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWI..."

- Board Settings
```json
{"width": 10, "height": 10, "totalMines": 35}
```
#### Response
- Game
```json
{
	"createTime": "2020-11-01T15:02:15.336712",
	"score": 0,
	"id": 1,
	"status": "IN_PROGRESS",
	"user": {
		"id": 1,
		"username": "pepe"
	},
	"board": {
		"id": 1,
		"settings": {
			"width": 10,
			"height": 10,
			"totalMines": 35
		},
		"cellList": [
                    {
                        "id": 1,
                        "adjacentMines": 0,
                        "revealed": false,
                        "flagged": false,
                        "xcoordinate": 1,
                        "ycoordinate": 1,
                        "mine": false
                    },
                    {
                        "id": 2,
                        "adjacentMines": 2,
                        "revealed": false,
                        "flagged": false,
                        "xcoordinate": 1,
                        "ycoordinate": 2,
                        "mine": false
                    }
               ]
	}
}
```

### Flag Cell
Flags a cell with the request coordinates supplied and the `gameId` and `flagType` desired. The flag type can be RED_FLAG or QUESTION_MARKS.

    PUT /minesweeper/games/{gameId}/flag-cell?flagType={flagType}

#### Request
Headers
> <b>Content-Type:</b> "application/json" <br>
> <b>Authorization:</b> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWI..."

- Cell
```json
{"xcoordinate": 1,"ycoordinate": 1}
```
#### Response
- Cell
```json
{
	"id": 1,
	"adjacentMines": 0,
	"flagType": "RED_FLAG",
	"revealed": false,
	"flagged": true,
	"xcoordinate": 1,
	"ycoordinate": 1,
	"mine": false
}
```

### Reveal Cell
Reveals a cell with the request coordinates and `gameId`supplied.

    PUT /minesweeper/games/{gameId}/reveal-cell

#### Request
Headers
> <b>Content-Type:</b> "application/json" <br>
> <b>Authorization:</b> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWI..."

- Cell
```json
{"xcoordinate": 1,"ycoordinate": 1}
```
#### Response
- Cell
```json
{
	"id": 1,
	"adjacentMines": 0,
	"flagType": "RED_FLAG",
	"revealed": true,
	"flagged": false,
	"xcoordinate": 1,
	"ycoordinate": 1,
	"mine": false
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)