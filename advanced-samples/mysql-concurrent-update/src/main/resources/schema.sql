CREATE TABLE `dept` (
     `id` bigint NOT NULL COMMENT 'ID',
     `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
     `pid` bigint DEFAULT NULL COMMENT '父级ID',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门';