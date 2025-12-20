-- 1. 插入管理员数据，对应接口 1.1 [cite: 60, 65]
INSERT INTO t_admin (username, password, name, create_time)
VALUES ('admin', '123456', '管理员', CURRENT_TIMESTAMP);

-- 2. 插入学生/用户数据，对应接口 2.1 [cite: 92, 105]
-- 状态 1 表示启用 [cite: 85, 156]
INSERT INTO t_user (username, password, email, status, create_time)
VALUES ('user', '123456', 's01@test.com', 1, CURRENT_TIMESTAMP);

-- 2. 插入更多用户数据
INSERT INTO t_user (username, password, phone, email, status, create_time, version)
VALUES
    ('user1', '123456', '13800000001', 'user1@test.com', 1, CURRENT_TIMESTAMP, 0),
    ('user2', '123456', '13800000002', 'user2@test.com', 1, CURRENT_TIMESTAMP, 0),
    ('user3', '123456', '13800000003', 'user3@test.com', 1, CURRENT_TIMESTAMP, 0);

-- 3. 插入学习计划数据
-- priority: 1高 2中 3低, status: 0待办 1进行中 2已完成
INSERT INTO t_plan (user_id, title, description, priority, status, progress, start_time, end_time, version)
VALUES
    (1, 'Java基础学习', '学习Java核心语法、面向对象编程', 1, 1, 50, CURRENT_TIMESTAMP, DATEADD('DAY', 30, CURRENT_TIMESTAMP), 0),
    (1, 'Spring Boot入门', '学习Spring Boot框架基础', 1, 0, 0, DATEADD('DAY', 7, CURRENT_TIMESTAMP), DATEADD('DAY', 37, CURRENT_TIMESTAMP), 0),
    (1, '数据库学习', '学习MySQL基础和MyBatis框架', 2, 2, 100, DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP), 0),
    (2, '前端Vue学习', '学习Vue3框架开发', 1, 1, 30, CURRENT_TIMESTAMP, DATEADD('DAY', 45, CURRENT_TIMESTAMP), 0),
    (2, '算法刷题', 'LeetCode每日一题', 2, 1, 20, CURRENT_TIMESTAMP, DATEADD('DAY', 60, CURRENT_TIMESTAMP), 0),
    (3, '英语学习', '每天背单词30个', 3, 0, 0, DATEADD('DAY', 1, CURRENT_TIMESTAMP), DATEADD('DAY', 90, CURRENT_TIMESTAMP), 0);