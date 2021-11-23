-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 23 nov. 2021 à 16:27
-- Version du serveur : 10.4.21-MariaDB
-- Version de PHP : 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `zoovolleyball`
--
CREATE DATABASE IF NOT EXISTS `zoovolleyball` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `zoovolleyball`;

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

CREATE TABLE `amis` (
  `id_lien` int(11) NOT NULL,
  `id_utilisateur_1` int(11) NOT NULL,
  `id_utilisateur_2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `objet`
--

CREATE TABLE `objet` (
  `id_objet` int(11) NOT NULL,
  `nom_objet` varchar(64) NOT NULL,
  `id_utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `partie`
--

CREATE TABLE `partie` (
  `id_partie` int(11) NOT NULL,
  `id_utilisateur_1` int(11) NOT NULL,
  `id_utilisateur_2` int(11) NOT NULL,
  `score_us_1` int(11) NOT NULL,
  `score_us_2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `us_id` int(11) NOT NULL,
  `pseudo` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `credit` int(11) NOT NULL,
  `mmr` int(11) NOT NULL,
  `nbrWin` int(11) NOT NULL,
  `nbrLoss` int(11) NOT NULL,
  `nbrTourn` int(11) NOT NULL,
  `nbrTournWin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `amis`
--
ALTER TABLE `amis`
  ADD PRIMARY KEY (`id_lien`),
  ADD KEY `ami_1` (`id_utilisateur_1`),
  ADD KEY `ami_2` (`id_utilisateur_2`);

--
-- Index pour la table `objet`
--
ALTER TABLE `objet`
  ADD PRIMARY KEY (`id_objet`),
  ADD KEY `possede_par` (`id_utilisateur`);

--
-- Index pour la table `partie`
--
ALTER TABLE `partie`
  ADD KEY `us_1` (`id_utilisateur_1`),
  ADD KEY `us_2` (`id_utilisateur_2`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`us_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `amis`
--
ALTER TABLE `amis`
  MODIFY `id_lien` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `objet`
--
ALTER TABLE `objet`
  MODIFY `id_objet` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `us_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `amis`
--
ALTER TABLE `amis`
  ADD CONSTRAINT `ami_1` FOREIGN KEY (`id_utilisateur_1`) REFERENCES `user` (`us_id`),
  ADD CONSTRAINT `ami_2` FOREIGN KEY (`id_utilisateur_2`) REFERENCES `user` (`us_id`);

--
-- Contraintes pour la table `objet`
--
ALTER TABLE `objet`
  ADD CONSTRAINT `possede_par` FOREIGN KEY (`id_utilisateur`) REFERENCES `user` (`us_id`);

--
-- Contraintes pour la table `partie`
--
ALTER TABLE `partie`
  ADD CONSTRAINT `us_1` FOREIGN KEY (`id_utilisateur_1`) REFERENCES `user` (`us_id`),
  ADD CONSTRAINT `us_2` FOREIGN KEY (`id_utilisateur_2`) REFERENCES `user` (`us_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
