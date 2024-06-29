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
