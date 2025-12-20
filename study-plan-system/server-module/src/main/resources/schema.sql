-- 用户表，对应 com.group3.common.entity.User
CREATE TABLE IF NOT EXISTS t_user (
                                      id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      username     VARCHAR(64)  NOT NULL,
                                      password     VARCHAR(128) NOT NULL,
                                      phone        VARCHAR(32),
                                      avatar       VARCHAR(255),
                                      email        VARCHAR(128),
                                      status       INTEGER DEFAULT 1,         -- 0:禁用 1:启用
                                      version      BIGINT DEFAULT 0,
                                      create_time  TIMESTAMP,       -- 添加
                                      update_time  TIMESTAMP,
                                      last_login_time  TIMESTAMP
);
-- 管理员表，对应 com.group3.common.entity.Admin
CREATE TABLE IF NOT EXISTS t_admin (
                                       id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       username     VARCHAR(64)  NOT NULL,
                                       password     VARCHAR(128) NOT NULL,
                                       name         VARCHAR(64),
                                       create_time  TIMESTAMP
);
-- 计划表，对应 com.group3.common.entity.Plan
CREATE TABLE IF NOT EXISTS t_plan (
                                      id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id      BIGINT       NOT NULL,     -- 关联 t_user.id
                                      title        VARCHAR(128) NOT NULL,
                                      description  VARCHAR(1024),
                                      priority     INTEGER      NOT NULL,     -- 1:高 2:中 3:低
                                      status       INTEGER      NOT NULL,     -- 0:待办 1:进行中 2:已完成
                                      progress     INTEGER      DEFAULT 0,    -- 0-100
                                      start_time   TIMESTAMP,
                                      end_time     TIMESTAMP,
                                      create_time  TIMESTAMP,
                                      update_time  TIMESTAMP,
                                      version      BIGINT       DEFAULT 0,
                                      CONSTRAINT fk_plan_user FOREIGN KEY (user_id) REFERENCES t_user(id)
);

