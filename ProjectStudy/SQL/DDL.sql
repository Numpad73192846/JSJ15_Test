-- DB 생성
CREATE DATABASE IF NOT EXISTS study;
USE study;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS
	files,
	persistence_logins,
	review_comment,
	board,
	board_group,
	menu,
	place_food,
	food_category,
	region_category,
	place,
	user_auth,
	users;

SET FOREIGN_KEY_CHECKS = 1;


-- =========================
-- USERS
-- =========================
CREATE TABLE `users` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` VARCHAR(64) NOT NULL COMMENT '(UUID)', 
    `profile_img` VARCHAR(300) NULL DEFAULT '/static/img/default-profile.png',
    `user_id` VARCHAR(100) NOT NULL COMMENT '아이디(이메일)',
    `password` VARCHAR(100) NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    `age` INT DEFAULT 0,
    `sex` VARCHAR(20) DEFAULT '공개안함',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_user_id (`id`),
    UNIQUE KEY uk_user_login (`user_id`),
    UNIQUE KEY uk_user_username (`username`)
);

-- =========================
-- USER_AUTH
-- =========================
CREATE TABLE `user_auth` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `auth` VARCHAR(50) NOT NULL COMMENT 'ROLE_USER, ROLE_ADMIN, ROLE_OWNER',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_user_auth (`user_no`, `auth`),
    CONSTRAINT fk_user_auth FOREIGN KEY (user_no) REFERENCES users(no)
);

-- =========================
-- REGION_CATEGORY
-- =========================
CREATE TABLE `region_category` (
	`no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`name` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_region_category (`name`)
);

-- =========================
-- PLACE
-- =========================
CREATE TABLE `place` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT '사장 회원 번호',
    `placename` VARCHAR(100) NOT NULL,
    `address` VARCHAR(200) NOT NULL,
    `phone` VARCHAR(100) NOT NULL,
    `content` VARCHAR(4000) NULL,
    `amenities` VARCHAR(100) NULL,
    `region_no` INT NOT NULL COMMENT '지역 FK',
    `lat` DOUBLE DEFAULT 0.0 COMMENT '위도 (Lat)',
    `lng` DOUBLE DEFAULT 0.0 COMMENT '경도 (Lng)',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    CONSTRAINT fk_place_user FOREIGN KEY (user_no) REFERENCES users(no),
    CONSTRAINT fk_place_region FOREIGN KEY (region_no) REFERENCES region_category(no),
    INDEX idx_place_region (region_no),
    INDEX idx_place_name (placename)
);

-- =========================
-- FOOD_CATEGORY
-- =========================
CREATE TABLE `food_category` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `foodname` VARCHAR(100) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_foodname (foodname)
);

-- =========================
-- PLACE_FOOD
-- =========================
CREATE TABLE `place_food` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `food_no` INT NOT NULL,
    `place_no` INT NOT NULL,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_place_food (place_no, food_no),
    CONSTRAINT fk_place_place FOREIGN KEY (place_no) REFERENCES place(no),
    CONSTRAINT fk_place_food FOREIGN KEY (food_no) REFERENCES food_category(no),
    INDEX idx_place_food_food (food_no),
    INDEX idx_place_food_place (place_no)
);

-- =========================
-- MENU
-- =========================
CREATE TABLE `menu` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `place_no` INT NOT NULL,
    `menuname` VARCHAR(100) NOT NULL,
    `content` VARCHAR(500) NULL,
    `price` INT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    CONSTRAINT fk_menu_place FOREIGN KEY (place_no) REFERENCES place(no),
    INDEX idx_menu_place (place_no)
);

-- =========================
-- BOARD_GROUP
-- =========================
CREATE TABLE `board_group` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name` VARCHAR(100) NOT NULL COMMENT '게시판 명',
    `seq` INT NOT NULL COMMENT '순서',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_board_group_name (name),
    UNIQUE KEY uk_board_group_seq (seq)
);

-- =========================
-- BOARD
-- =========================
CREATE TABLE `board` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `group_no` INT DEFAULT 1 COMMENT 'FK',
    `place_no` INT DEFAULT NULL COMMENT 'FK',
    `rating` DOUBLE DEFAULT 0.0 COMMENT '평점',
    `title` VARCHAR(200) NULL COMMENT '제목',
    `content` TEXT COMMENT '내용',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    CONSTRAINT fk_board_user FOREIGN KEY (user_no) REFERENCES users (no),
    CONSTRAINT fk_board_group FOREIGN KEY (group_no) REFERENCES board_group (no),
    CONSTRAINT fk_board_place FOREIGN KEY (place_no) REFERENCES place (no),
    INDEX idx_board_place_created (place_no, created_at),
    INDEX idx_board_user_created (user_no, created_at)
);

-- =========================
-- REVIEW_COMMENT
-- =========================
CREATE TABLE `review_comment` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `board_no` INT NOT NULL COMMENT 'FK: 게시글 번호',
    `user_no` INT NOT NULL COMMENT 'FK: 작성자 번호',
    `content` TEXT NOT NULL COMMENT '댓글 내용',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    CONSTRAINT fk_review_comment_board FOREIGN KEY (board_no) REFERENCES board (no),
    CONSTRAINT fk_review_comment_user FOREIGN KEY (user_no) REFERENCES users (no),
    INDEX idx_review_comment_board_created (board_no, created_at)
);

-- =========================
-- FILES
-- =========================
CREATE TABLE `files` (
	`no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id` VARCHAR(64) NOT NULL COMMENT 'UUID',
	`parent_table` VARCHAR(20) NOT NULL COMMENT 'PLACE/MENU/BOARD/BANNER',
	`parent_no` INT NOT NULL COMMENT '부모 PK',
	`original_name` VARCHAR (255) NOT NULL,
	`saved_name` VARCHAR (255) NOT NULL,
	`path` VARCHAR (500) NOT NULL,
	`content_type` VARCHAR (100) NULL,
	`file_size` BIGINT NULL,
	`is_main` CHAR(1) NOT NULL DEFAULT 'N',
	`sort_order` INT NOT NULL DEFAULT 0,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	UNIQUE KEY uk_files_uuid (id),
	INDEX idx_files_parent (parent_table, parent_no),
	INDEX idx_files_main (parent_table, parent_no, is_main)
);

-- =========================
-- PERSISTENCE_LOGINS
-- =========================
CREATE TABLE `persistence_logins` (
	`no`			INT	NOT NULL AUTO_INCREMENT  PRIMARY KEY	COMMENT '번호',
	`id`			VARCHAR(255)	NOT NULL	COMMENT 'ID (UUID)',
	`user_id`		VARCHAR(100)	NOT NULL	COMMENT '회원 아이디',
	`token`			VARCHAR(255)	NOT NULL	COMMENT '인증 토큰',
	`expiry_date`	TIMESTAMP		NOT NULL	COMMENT '만료시간',
	`created_at`		TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '등록일자',
	`updated_at`		TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '수정일자',
	UNIQUE KEY uk_persist_id (id),
	INDEX idx_persist_user_id (user_id),
	INDEX idx_persist_token (token)
);