CREATE TABLE LOGS(
    DATED   DATE           NOT NULL,
    LOGGER  VARCHAR(500)    NOT NULL,
    LEVEL   VARCHAR(100)    NOT NULL,
    MESSAGE VARCHAR(3000)  NOT NULL
   );