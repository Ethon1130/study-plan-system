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










-- 3. 插入学习计划数据
-- priority: 1高 2中 3低, status: 0待办 1进行中 2已完成
INSERT INTO t_plan (user_id, title, description, priority, status, progress, start_time, end_time, create_time, update_time, version)
VALUES
    -- 用户1 (user/123456) 的计划 - 用于报表展示
    (1, 'Java基础学习', '学习Java核心语法、面向对象编程', 1, 2, 100, DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -60, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'Spring Boot入门', '学习Spring Boot框架基础', 1, 2, 100, DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -5, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, '数据库学习', '学习MySQL基础和MyBatis框架', 1, 2, 100, DATEADD('DAY', -45, CURRENT_TIMESTAMP), DATEADD('DAY', -15, CURRENT_TIMESTAMP), DATEADD('DAY', -45, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'MyBatis框架实战', '深入学习MyBatis映射和动态SQL', 2, 2, 100, DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -3, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'Redis缓存技术', '学习Redis数据结构和缓存策略', 1, 1, 75, DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', 10, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'Spring Cloud微服务', '学习微服务架构和Spring Cloud组件', 1, 1, 60, DATEADD('DAY', -5, CURRENT_TIMESTAMP), DATEADD('DAY', 25, CURRENT_TIMESTAMP), DATEADD('DAY', -5, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'Docker容器化部署', '学习Docker基础和容器编排', 2, 1, 40, CURRENT_TIMESTAMP, DATEADD('DAY', 20, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, 'Kubernetes入门', '学习K8s集群管理和服务编排', 2, 0, 0, DATEADD('DAY', 5, CURRENT_TIMESTAMP), DATEADD('DAY', 35, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, '前端Vue3框架', '学习Vue3组合式API和响应式原理', 2, 0, 0, DATEADD('DAY', 7, CURRENT_TIMESTAMP), DATEADD('DAY', 37, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, 'React框架学习', '学习React Hooks和状态管理', 3, 0, 0, DATEADD('DAY', 10, CURRENT_TIMESTAMP), DATEADD('DAY', 50, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, '算法与数据结构', '刷LeetCode算法题，掌握常用数据结构', 1, 1, 55, DATEADD('DAY', -15, CURRENT_TIMESTAMP), DATEADD('DAY', 45, CURRENT_TIMESTAMP), DATEADD('DAY', -15, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, '设计模式学习', '学习23种设计模式及应用场景', 2, 1, 30, DATEADD('DAY', -8, CURRENT_TIMESTAMP), DATEADD('DAY', 22, CURRENT_TIMESTAMP), DATEADD('DAY', -8, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'JVM性能调优', '深入理解JVM内存模型和GC机制', 1, 0, 0, DATEADD('DAY', 15, CURRENT_TIMESTAMP), DATEADD('DAY', 45, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, 'MySQL性能优化', '学习索引优化、SQL调优和分库分表', 1, 1, 45, DATEADD('DAY', -12, CURRENT_TIMESTAMP), DATEADD('DAY', 18, CURRENT_TIMESTAMP), DATEADD('DAY', -12, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, '消息队列RabbitMQ', '学习消息队列原理和RabbitMQ应用', 2, 0, 0, DATEADD('DAY', 20, CURRENT_TIMESTAMP), DATEADD('DAY', 50, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, 'Elasticsearch搜索', '学习全文搜索引擎和分布式搜索', 3, 0, 0, DATEADD('DAY', 25, CURRENT_TIMESTAMP), DATEADD('DAY', 65, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, '项目实战：电商系统', '开发完整的电商后台管理系统', 1, 1, 35, DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', 40, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, '网络安全基础', '学习常见Web安全漏洞和防护措施', 2, 0, 0, DATEADD('DAY', 30, CURRENT_TIMESTAMP), DATEADD('DAY', 60, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (1, 'Git版本控制进阶', '学习Git高级操作和团队协作流程', 3, 2, 100, DATEADD('DAY', -40, CURRENT_TIMESTAMP), DATEADD('DAY', -25, CURRENT_TIMESTAMP), DATEADD('DAY', -40, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    (1, 'Linux系统运维', '学习Linux命令和Shell脚本编程', 2, 1, 50, DATEADD('DAY', -18, CURRENT_TIMESTAMP), DATEADD('DAY', 12, CURRENT_TIMESTAMP), DATEADD('DAY', -18, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 0),
    -- 其他用户的计划
    (2, '前端Vue学习', '学习Vue3框架开发', 1, 1, 30, CURRENT_TIMESTAMP, DATEADD('DAY', 45, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (2, '算法刷题', 'LeetCode每日一题', 2, 1, 20, CURRENT_TIMESTAMP, DATEADD('DAY', 60, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    (3, '英语学习', '每天背单词30个', 3, 0, 0, DATEADD('DAY', 1, CURRENT_TIMESTAMP), DATEADD('DAY', 90, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);