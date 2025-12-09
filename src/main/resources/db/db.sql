CREATE DATABASE IF NOT EXISTS lectureEvaluation
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE lectureEvaluation;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(20) NOT NULL,
    userPassword VARCHAR(64) NOT NULL,
    userEmail VARCHAR(50) NOT NULL,
    CONSTRAINT uq_user_name UNIQUE (userName),
    CONSTRAINT uq_user_email UNIQUE (userEmail)
);


CREATE TABLE evaluation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    lectureName VARCHAR(50) NOT NULL,
    professorName VARCHAR(20) NOT NULL,
    lectureYear INT NOT NULL,
    semesterDivide VARCHAR(20) NOT NULL,
    lectureDivide VARCHAR(10) NOT NULL,
    evaluationTitle VARCHAR(50) NOT NULL,
    evaluationContent TEXT NOT NULL,
    totalScore VARCHAR(5) NOT NULL,
    creditScore VARCHAR(5) NOT NULL,
    comfortableScore VARCHAR(5) NOT NULL,
    lectureScore VARCHAR(5) NOT NULL,
    likeCount INT DEFAULT 0,
    CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE likey (
    userId BIGINT NOT NULL,
    evaluationId BIGINT NOT NULL,
    CONSTRAINT pk_likey PRIMARY KEY (userId, evaluationId),
    CONSTRAINT fk_likey_user FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_likey_evaluation FOREIGN KEY (evaluationId) REFERENCES evaluation(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    evaluationId BIGINT NOT NULL,
    report_title VARCHAR(50) NOT NULL,
    report_content TEXT NOT NULL,
    UNIQUE KEY uq_report (userId, evaluationId),
    CONSTRAINT fk_report_user FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_report_evaluation FOREIGN KEY (evaluationId) REFERENCES evaluation(id) ON DELETE CASCADE
);