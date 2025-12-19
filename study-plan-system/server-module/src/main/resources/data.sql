-- 1. 插入管理员数据，对应接口 1.1 [cite: 60, 65]
INSERT INTO t_admin (username, password, name, create_time)
VALUES ('admin', '123456', '管理员', CURRENT_TIMESTAMP);

-- 2. 插入学生/用户数据，对应接口 2.1 [cite: 92, 105]
-- 状态 1 表示启用 [cite: 85, 156]
INSERT INTO t_user (username, password, email, status, create_time)
VALUES ('user', '123456', 's01@test.com', 1, CURRENT_TIMESTAMP);