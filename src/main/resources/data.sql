INSERT INTO user(id_user, credit, email, mmr, nbr_loss, nbr_tourn, nbr_tourn_win, nbr_win, pseudo, pwd) VALUES
(1, 0, 'ouioui@gmail.com', 0, 0, 0, 0, 0, 'Ouioui', '$2a$10$8v.ZE5Zd3O9lvknSAaKAOuFZEfbJdpBESJ1dgDc80lJIKfRWTkHoW');
-- pwd = 1234

INSERT INTO user(id_user, credit, email, mmr, nbr_loss, nbr_tourn, nbr_tourn_win, nbr_win, pseudo, pwd) VALUES
(2, 0, 'pomme@gmail.com', 0, 0, 0, 0, 0, 'pomme', '$2a$10$4OG.AxZ6Sn.EeRsCSiafVeqFjvEglaB.78p85zArrgWgvlC6mP.ei');

INSERT INTO user(id_user, credit, email, mmr, nbr_loss, nbr_tourn, nbr_tourn_win, nbr_win, pseudo, pwd) VALUES
(3, 0, 'aaa@gmail.com', 0, 0, 0, 0, 0, 'AmiAAjouter', '$2a$10$1.RCtL11LWHytk8yt1NNru2DzZQo.YYYjXYvwWgUG8jXLFso9Wz12');

INSERT INTO user_amis(user_id_user, amis_id_user) VALUES
(1, 2);

INSERT INTO user_amis(user_id_user, amis_id_user) VALUES
(2, 1);

INSERT INTO roles (ref_role, role, user_id_user) VALUES
(1, 1, 1);

INSERT INTO `objet` (`id_objet`, `nom_objet` ,`price`, `type_item`) VALUES
(1, 'BallBlack', 5, 0),
(2,' BallWhite', 10, 0),
(3, 'BallBlue', 15, 0),
(4, 'BallRed', 15, 0),
(5, 'Skin1', 150, 1),
(6, 'Skin2', 150, 1),
(7, 'BackGround1', 150, 2),
(8, 'Net1', 150, 3);


INSERT INTO `user_objets` (`user_id_user`, `objets_id_objet`) VALUES
(1, 1),
(1, 2),
(1, 3);