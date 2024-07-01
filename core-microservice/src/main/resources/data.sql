-- Вставка пользователей
INSERT INTO users (id, name, username, password, balance)
VALUES
    ('778d7f25-f9b3-4367-9bf0-b58ebd2183e2', 'Антонио Пантера', 'user1', '$2a$10$aypcYsO7s9frKbrIp0Fe0uuL2LlINaSFvDknlLDkU7pXOPZeihFJu', 100.00), -- password: password1
    ('8292e043-2a21-44d9-884f-2514d838f641', 'Джордж Клоун', 'user2', '$2a$10$TuAJi2fTWLxCHrNeTHlbPe4Zpw2d3cVzBZpUV6YC2uaaSW7j1ndui', 100.00), -- password: password2
    ('4947ea81-3880-4f39-a806-0c488b2b34aa', 'Серега', 'user3', '$2a$10$iLltM2onz.iTBaise9yzz.lJ/8ky4L9Lm7oAMzMDu147slSd1qcM2', 100.00), -- password: password3
    ('952f9821-8300-432a-9adf-b67fef73fb02', 'Заместитель начальника управления управлений', 'user4', '$2a$10$ElTQ7VZmIzG9TPImtmto/uatgYnlJU/9TMVpUp2VBgTYpnihhIAzW', 100.00), -- password: password4
    ('82485ba3-4485-425f-912c-bd49f0aa2c3c', 'Серега2', 'user5', '$2a$10$J5dmRoJM0bZjWTvON4afZONt5V9RsOIrFU4dVrQXxraeQSBU6/DG6', 100.00), -- password: password5
    ('6ffae18b-d844-42f6-9e5d-48732a34165c', 'ПуЛи_От_БаБуЛи', 'user6', '$2a$10$NRBnFhzjvrR/rCXffA1Squ7ZoMwUfswDbh8ok.qoMoNXYX5U4pEBu', 100.00), -- password: password6
    ('717a6bfd-def7-4fb0-b05c-2689b0d2d2c0', 'Я_в_КС_а_Ты_В_ЧС', 'user7', '$$2a$10$6iH8n64nX.nXMvxUmR687eg1JR8vHjtIZejbQPvzSjAW7O4Q7wmcq', 100.00), -- password: password7
    ('806e1529-df00-4009-b435-d4a23aaaad77', 'Агрессивный карбюратор', 'user8', '$2a$10$9rRkh7v7s5Vn65iv3iCKMuZPfHXUA9BQFOF8PDmzGCRpxubuHOuvS', 100.00), -- password: password8
    ('f8a3b548-0d9d-4a87-9de5-4c22266191d6', 'Владимир Владимирович П...ознер', 'user9', '$2a$10$1rZOA4PWp/V.0YRsY3DQS.rIFPBSCXAAboDV.Ciz5yjvMQAkpj5VS', 100.00), -- password: password9
    ('cab915c7-cbac-4dd9-9f00-8ded705b6fd2', 'Серега21', 'user10', '$2a$10$VfeO202tM5rkrZYTRv2WluwB5tXlVXb95zwQ3C2tEZZ8mEfYFsNrK', 100.00); -- password: password10

-- Вставка ролей для пользователей
INSERT INTO users_roles (user_id, role)
VALUES
    ('778d7f25-f9b3-4367-9bf0-b58ebd2183e2', 'USER'),
    ('8292e043-2a21-44d9-884f-2514d838f641', 'USER'),
    ('4947ea81-3880-4f39-a806-0c488b2b34aa', 'USER'),
    ('952f9821-8300-432a-9adf-b67fef73fb02', 'USER'),
    ('82485ba3-4485-425f-912c-bd49f0aa2c3c', 'USER'),
    ('6ffae18b-d844-42f6-9e5d-48732a34165c', 'USER'),
    ('717a6bfd-def7-4fb0-b05c-2689b0d2d2c0', 'USER'),
    ('806e1529-df00-4009-b435-d4a23aaaad77', 'USER'),
    ('f8a3b548-0d9d-4a87-9de5-4c22266191d6', 'USER'),
    ('cab915c7-cbac-4dd9-9f00-8ded705b6fd2', 'USER'),
    ('778d7f25-f9b3-4367-9bf0-b58ebd2183e2', 'CREATOR'),
    ('6ffae18b-d844-42f6-9e5d-48732a34165c', 'CREATOR'),
    ('cab915c7-cbac-4dd9-9f00-8ded705b6fd2', 'CREATOR');

-- Вставка определений подписок для пользователей с ролью CREATOR
INSERT INTO subscription_definition (id, title, cost, level, description, creator_id)
VALUES
    -- Определения подписок для пользователя 778d7f25-f9b3-4367-9bf0-b58ebd2183e2 Антонио Пантера
    ('27b44d75-9dc7-4886-878e-50a93abdbcc6', 'Базовая подписка Антонио Пантера', 100, 'BASIC', 'Это база', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2'),
    ('03b6c1f7-7f87-4873-8a5d-772cfc23ec8a', 'Про подписка Антонио Пантера', 200, 'PRO', 'Средний класс', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2'),
    ('23ef3275-26f6-4bea-9b62-4302e47d4ded', 'Платиновая подписка Антонио Пантера', 300, 'PLATINUM', 'Топ порковитель', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2'),

    -- Определения подписок для пользователя 6ffae18b-d844-42f6-9e5d-48732a34165c ПуЛи_От_БаБуЛи
    ('eba9a46b-987d-4717-9328-abd786ca846b', 'Базовая подписка ПуЛи_От_БаБуЛи', 100, 'BASIC', 'Эко раунд', '6ffae18b-d844-42f6-9e5d-48732a34165c'),
    ('dd7de483-d2b5-4017-bf21-09664d0e72d7', 'Про подписка ПуЛи_От_БаБуЛи', 200, 'PRO', 'Полный закуп', '6ffae18b-d844-42f6-9e5d-48732a34165c'),
    ('3a9b01d8-12c9-41dc-92c4-49abbcf2575e', 'Платиновая подписка ПуЛи_От_БаБуЛи', 300, 'PLATINUM', 'drop awp pls', '6ffae18b-d844-42f6-9e5d-48732a34165c'),

    -- Определения подписок для пользователя cab915c7-cbac-4dd9-9f00-8ded705b6fd2 Серега21
    ('e03d2c88-420c-49c4-a129-0ebe548b4412', 'Базовая подписка Серега21', 100, 'BASIC', 'На семечки', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2'),
    ('c6ecef6e-d81c-4f2d-9d98-ee9530106b03', 'Про подписка Серега21', 200, 'PRO', 'На сигареты', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2'),
    ('c1a60650-d1b6-4548-b13a-82cfa1de3d65', 'Платиновая подписка Серега21', 300, 'PLATINUM', 'На сигареты и компы)', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2');

-- Вставка подписок для пользователей (READER)
INSERT INTO subscriptions (id, user_creator_id, user_subscriber_id, subscription_definition_id, type, start_date, end_date, cost, period)
VALUES
    -- Подписки для пользователей на Антонио Пантера
    ('3d0a9b10-ba41-40ec-9751-b7c981814d8d', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', '8292e043-2a21-44d9-884f-2514d838f641', '27b44d75-9dc7-4886-878e-50a93abdbcc6', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 100, 1),
    ('1e71d674-6da0-4cdc-8fda-f1baeb39dabc', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', '4947ea81-3880-4f39-a806-0c488b2b34aa', '03b6c1f7-7f87-4873-8a5d-772cfc23ec8a', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 200, 1),
    ('89891479-891c-462c-8a31-9191896ca8e9', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', '952f9821-8300-432a-9adf-b67fef73fb02', '23ef3275-26f6-4bea-9b62-4302e47d4ded', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 300, 1),

    -- Подписки для пользователей на ПуЛи_От_БаБуЛи
    ('28f2f790-9546-431d-9ded-bb3ce88c13d5', '6ffae18b-d844-42f6-9e5d-48732a34165c', '8292e043-2a21-44d9-884f-2514d838f641', 'eba9a46b-987d-4717-9328-abd786ca846b', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 100, 1),
    ('9480b299-e411-40e0-b362-c7c026f3cab5', '6ffae18b-d844-42f6-9e5d-48732a34165c', '4947ea81-3880-4f39-a806-0c488b2b34aa', 'dd7de483-d2b5-4017-bf21-09664d0e72d7', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 200, 1),
    ('be161cb5-5e22-4bfc-89a8-53863e35da89', '6ffae18b-d844-42f6-9e5d-48732a34165c', '952f9821-8300-432a-9adf-b67fef73fb02', '3a9b01d8-12c9-41dc-92c4-49abbcf2575e', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 300, 1),

    -- Подписки для пользователей на Серега21
    ('279d3e74-dbaf-467f-ad81-9a9e173cae34', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', '8292e043-2a21-44d9-884f-2514d838f641', 'e03d2c88-420c-49c4-a129-0ebe548b4412', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 100, 1),
    ('f626c525-b55b-4872-892b-8c8b95ce963f', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', '4947ea81-3880-4f39-a806-0c488b2b34aa', 'c6ecef6e-d81c-4f2d-9d98-ee9530106b03', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 200, 1),
    ('bd0ff191-ee65-483d-9615-515b8cd3a6ed', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', '952f9821-8300-432a-9adf-b67fef73fb02', 'c1a60650-d1b6-4548-b13a-82cfa1de3d65', 'STANDARD', NOW(), NOW() + INTERVAL '1 month', 300, 1);

-- Вставка постов для подписок
INSERT INTO posts (id, title, content, subscription_id, creator_id, created_at, updated_at, comment_count, like_count, dislike_count)
VALUES
    -- Посты для подписок на Антонио Пантера
    ('8a96163c-bbf5-4f69-bc9d-4eac93f98bb1', 'Добро пожаловать!', 'Добро пожаловать в мою базовую подписку!', '3d0a9b10-ba41-40ec-9751-b7c981814d8d', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', NOW(), NOW(), 15, 120, 3),
    ('add8a649-1539-456e-a558-8fb2ad93be5c', 'Эксклюзивный контент', 'Здесь вы найдете эксклюзивные материалы.', '1e71d674-6da0-4cdc-8fda-f1baeb39dabc', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', NOW(), NOW(), 25, 300, 10),
    ('9c2dd56c-3cb3-4f19-8f79-0f0a5cfb8c32', 'Важное объявление', 'Платиновый уровень подписки получает доступ к уникальным предложениям.', '89891479-891c-462c-8a31-9191896ca8e9', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', NOW(), NOW(), 5, 200, 1),

    -- Посты для подписок на ПуЛи_От_БаБуЛи
    ('b527e3a1-7384-4327-8f66-24a46c0b7a16', 'Первые шаги', 'Спасибо за подписку на базовый уровень!', '28f2f790-9546-431d-9ded-bb3ce88c13d5', '6ffae18b-d844-42f6-9e5d-48732a34165c', NOW(), NOW(), 10, 150, 5),
    ('75a3a7b2-1a94-42ed-8d80-2c2e9f148ae7', 'Продвинутые техники', 'Про подписка предоставляет доступ к продвинутым техникам.', '9480b299-e411-40e0-b362-c7c026f3cab5', '6ffae18b-d844-42f6-9e5d-48732a34165c', NOW(), NOW(), 18, 250, 8),
    ('b872dcfb-676f-4a09-8df4-5db4bff28d4b', 'Секреты успеха', 'Платиновый уровень подписки раскрывает все секреты успеха.', 'be161cb5-5e22-4bfc-89a8-53863e35da89', '6ffae18b-d844-42f6-9e5d-48732a34165c', NOW(), NOW(), 30, 400, 15),

    -- Посты для подписок на Серега21
    ('6e68d8f7-7c1e-4b21-bf95-c2a5dc0c1a2c', 'Начало пути', 'Базовая подписка Серега21: начнем с малого.', '279d3e74-dbaf-467f-ad81-9a9e173cae34', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', NOW(), NOW(), 8, 130, 2),
    ('02f93c57-f432-432e-8dc4-4c86b5d2ef8f', 'Серьезные занятия', 'Про подписка Серега21: серьезные занятия начинаются здесь.', 'f626c525-b55b-4872-892b-8c8b95ce963f', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', NOW(), NOW(), 20, 210, 7),
    ('b24842d7-3299-4a8b-a67c-818ca40ea1a0', 'Мастер-класс', 'Платиновая подписка Серега21: мастер-классы для вас.', 'bd0ff191-ee65-483d-9615-515b8cd3a6ed', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', NOW(), NOW(), 40, 350, 20),

    -- Дополнительные посты для подписок на Антонио Пантера
    ('2390c780-f4dc-4b41-bb4e-1cf9f5678b60', 'Новое видео', 'Посмотрите мое новое видео!', '3d0a9b10-ba41-40ec-9751-b7c981814d8d', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', NOW(), NOW(), 12, 220, 4),
    ('d799601b-f589-4a90-b9b8-bc30e6c62761', 'Вопросы и ответы', 'Про подписка: задавайте ваши вопросы.', '1e71d674-6da0-4cdc-8fda-f1baeb39dabc', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', NOW(), NOW(), 22, 310, 12),
    ('a74e5f3e-34b4-4c55-bb99-e2b8a63c3e4e', 'Эксклюзивные предложения', 'Платиновая подписка: только для вас!', '89891479-891c-462c-8a31-9191896ca8e9', '778d7f25-f9b3-4367-9bf0-b58ebd2183e2', NOW(), NOW(), 35, 410, 18),

    -- Дополнительные посты для подписок на ПуЛи_От_БаБуЛи
    ('51c1d82d-30b0-4169-8e0d-c8a5db3b947f', 'Советы', 'Базовая подписка: мои советы для вас.', '28f2f790-9546-431d-9ded-bb3ce88c13d5', '6ffae18b-d844-42f6-9e5d-48732a34165c', NOW(), NOW(), 7, 140, 3),
    ('617a19ed-25a5-4545-b82d-2c1b8a0048da', 'Тренировки', 'Про подписка: лучшие тренировки.', '9480b299-e411-40e0-b362-c7c026f3cab5', '6ffae18b-d844-42f6-9e5d-48732a34165c', NOW(), NOW(), 28, 240, 9),
    ('f4c1236c-f37e-42c6-8eb2-e7c5f7119b2b', 'Интервью', 'Платиновая подписка: эксклюзивные интервью.', 'be161cb5-5e22-4bfc-89a8-53863e35da89', '6ffae18b-d844-42f6-9e5d-48732a34165c', NOW(), NOW(), 33, 390, 16),

    -- Дополнительные посты для подписок на Серега21
    ('909d5f1e-9e12-47f5-bd7e-500cb939ed16', 'Инструкции', 'Базовая подписка: пошаговые инструкции.', '279d3e74-dbaf-467f-ad81-9a9e173cae34', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', NOW(), NOW(), 9, 160, 5),
    ('fa1284b4-6e82-42f4-b950-b1b7dcdcd58c', 'Секреты мастера', 'Про подписка: секреты от мастера.', 'f626c525-b55b-4872-892b-8c8b95ce963f', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', NOW(), NOW(), 26, 270, 11),
    ('5b96b8f5-bd62-414f-a6f7-e4db5e11534b', 'Эксклюзивные уроки', 'Платиновая подписка: эксклюзивные уроки.', 'bd0ff191-ee65-483d-9615-515b8cd3a6ed', 'cab915c7-cbac-4dd9-9f00-8ded705b6fd2', NOW(), NOW(), 37, 410, 19);
