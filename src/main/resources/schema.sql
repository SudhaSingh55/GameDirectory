DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
                         USER_ID INT AUTO_INCREMENT PRIMARY KEY,
                         NAME VARCHAR2(50) NOT NULL,
                         NICKNAME VARCHAR2(50) NOT NULL,
                         GEOGRAPHY VARCHAR2(30)  NOT NULL,
                         PHONE_NUMBER VARCHAR2(10),
                         EMAIL VARCHAR2(30) UNIQUE
);

CREATE INDEX USER_ID on USERS(USER_ID);

DROP TABLE IF EXISTS GAME;
CREATE TABLE GAME (
                      GAME_ID INT AUTO_INCREMENT PRIMARY KEY,
                      NAME VARCHAR2(50) NOT NULL,
                      DESCRIPTION VARCHAR2(30)
);
CREATE INDEX GAME_ID on GAME(GAME_ID);

DROP TABLE IF EXISTS USER_GAME_DIRECTORY;
CREATE TABLE USER_GAME_DIRECTORY (
                      LEVEl_ID INT AUTO_INCREMENT PRIMARY KEY,
                      USER_ID INT,
                      GAME_ID INT,
                      GAME_NAME VARCHAR2(50),
                      LEVEL VARCHAR2(50),
                      CREDIT INT,
                      foreign key (USER_ID) references USERS(USER_ID),
                      foreign key (GAME_ID) references GAME(GAME_ID)
);
CREATE INDEX DIR_GAME_ID on USER_GAME_DIRECTORY(GAME_ID);
CREATE INDEX LEVEl_ID on USER_GAME_DIRECTORY(LEVEl_ID);
CREATE INDEX DIR_USER_ID on USER_GAME_DIRECTORY(USER_ID);