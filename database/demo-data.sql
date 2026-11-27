-- 演示账号供本地验证使用。请先执行 schema.sql。
USE `information_audit`;

INSERT INTO `admin` (`name`, `password`, `privilege_level`)
SELECT demo.name, 'AuditDemo2026!', demo.privilege_level
FROM (
    SELECT 'demo-uploader' AS name, 1 AS privilege_level
    UNION ALL SELECT 'demo-reviewer', 2
    UNION ALL SELECT 'demo-final-reviewer', 3
    UNION ALL SELECT 'demo-admin', 4
) AS demo
WHERE NOT EXISTS (SELECT 1 FROM `admin` AS existing WHERE existing.name = demo.name);

INSERT INTO `uploader` (`name`, `study_number`, `gender`, `pic`, `academy`, `major`)
SELECT '演示上传者', 'demo-uploader', 0, '', '演示院系', '演示专业'
WHERE NOT EXISTS (SELECT 1 FROM `uploader` WHERE `study_number` = 'demo-uploader');
