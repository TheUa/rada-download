CREATE TABLE IF NOT EXISTS DEPUTY_PRESENCE (
    ID BIGINT AUTO_INCREMENT,
    DEPUTY_ID INT,
    NAME VARCHAR(255),
    PRESENCE_COUNT INT,
    PRIMARY KEY (ID)
)