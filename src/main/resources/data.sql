INSERT INTO user(id_user, credit, email, mmr, nbr_loss, nbr_tourn, nbr_tourn_win, nbr_win, pseudo, pwd) VALUES
(1, 0, 'ouioui@gmail.com', 0, 0, 0, 0, 0, 'Ouioui', '$2a$10$8v.ZE5Zd3O9lvknSAaKAOuFZEfbJdpBESJ1dgDc80lJIKfRWTkHoW');
-- pwd = 1234

INSERT INTO user(id_user, credit, email, mmr, nbr_loss, nbr_tourn, nbr_tourn_win, nbr_win, pseudo, pwd) VALUES
(2, 0, 'pomme@gmail.com', 0, 0, 0, 0, 0, 'pomme', '$2a$10$4OG.AxZ6Sn.EeRsCSiafVeqFjvEglaB.78p85zArrgWgvlC6mP.ei');

INSERT INTO user_amis(user_id_user, amis_id_user) VALUES
(1, 2);

INSERT INTO user_amis(user_id_user, amis_id_user) VALUES
(2, 1);

INSERT INTO roles (ref_role, role, user_id_user) VALUES (1, 1, 1);