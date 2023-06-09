-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2021 at 08:08 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rpg`
--

-- --------------------------------------------------------

--
-- Table structure for table `hero`
--

CREATE TABLE `hero` (
  `Id` varchar(16) NOT NULL,
  `Nickname` varchar(16) DEFAULT NULL,
  `Exp` int(11) DEFAULT NULL,
  `Level` int(11) DEFAULT NULL,
  `Atk` int(11) DEFAULT NULL,
  `Def` int(11) DEFAULT NULL,
  `Spd` int(11) DEFAULT NULL,
  `hp` int(11) NOT NULL,
  `password` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hero`
--

INSERT INTO `hero` (`Id`, `Nickname`, `Exp`, `Level`, `Atk`, `Def`, `Spd`, `hp`, `password`) VALUES
('', NULL, 0, 1, 100, 10, 30, 1000, ''),
('johan', 'Johan', 100, 26, 1053, 72, 284, 10485, 'johan'),
('johan001', NULL, 0, 1, 100, 10, 30, 1000, 'johan001'),
('johanaaa', NULL, 0, 1, 100, 10, 30, 1000, 'johanaaa');

-- --------------------------------------------------------

--
-- Table structure for table `iventori`
--

CREATE TABLE `iventori` (
  `idiventori` int(11) NOT NULL,
  `idhero` varchar(16) NOT NULL,
  `nama` varchar(16) NOT NULL,
  `tipe` varchar(16) NOT NULL,
  `atk` int(11) NOT NULL,
  `def` int(11) NOT NULL,
  `spd` int(11) NOT NULL,
  `hp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `iventori`
--

INSERT INTO `iventori` (`idiventori`, `idhero`, `nama`, `tipe`, `atk`, `def`, `spd`, `hp`) VALUES
(1, 'johan', 'armor', 'armor', 0, 5, 0, 0),
(2, 'johan', 'helm1', 'helm', 0, 11, 0, 0),
(3, 'johan', 'pedang', 'pedang', 1, 0, 0, 0),
(4, 'johan', 'helm1', 'helm', 0, 15, 0, 0),
(6, 'johan', 'armor', 'armor', 4, 15, 0, 0),
(7, 'johan', 'food', 'food', 0, 0, 0, 200),
(8, 'johan', 'boot', 'boot', 0, 0, 1, 0),
(9, 'johan', 'helm', 'helm', 0, 4, 0, 0),
(10, 'johan', 'helm', 'helm', 0, 1, 0, 0),
(11, 'johan', 'boot1', 'boot', 0, 0, 8, 0),
(12, 'johan', 'helm', 'helm', 0, 3, 0, 0),
(13, 'johan', 'helm', 'helm', 0, 3, 0, 0),
(14, 'johan', 'helm1', 'helm', 0, 14, 0, 0),
(15, 'johan', 'helm1', 'helm', 0, 16, 0, 0),
(16, 'johan', 'armor1', 'armor', 2, 36, 0, 0),
(17, 'johan', 'boot', 'boot', 0, 0, 1, 0),
(18, 'johan', 'pedang', 'pedang', 7, 0, 1, 0),
(19, 'johan', 'helm1', 'helm', 0, 8, 0, 0),
(20, 'johan', 'armor1', 'armor', 2, 39, 0, 0),
(21, 'johan', 'pedang', 'pedang', 6, 0, 0, 0),
(22, 'johan', 'pedang', 'pedang', 2, 0, 3, 0),
(23, 'johan', 'boot', 'boot', 0, 0, 1, 0),
(24, 'johan', 'pedang', 'pedang', 6, 0, 2, 0),
(25, 'johan', 'helm1', 'helm', 0, 10, 0, 0),
(26, 'johan', 'armor1', 'armor', 0, 46, 0, 0),
(28, 'johan', 'boot', 'boot', 0, 0, 4, 0),
(29, 'johan', 'armor1', 'armor', 6, 69, 0, 0),
(30, 'johan', 'helm', 'helm', 0, 1, 0, 0),
(31, 'johan', 'armor1', 'armor', 2, 48, 0, 0),
(32, 'johan', 'pedang', 'pedang', 8, 0, 3, 0),
(34, 'johan', 'armor', 'armor', 1, 12, 0, 0),
(35, 'johan', 'helm', 'helm', 0, 1, 0, 0),
(37, 'johan', 'boot', 'boot', 0, 0, 3, 0),
(38, 'johan', 'pedang', 'pedang', 7, 0, 4, 0),
(39, 'johan', 'helm', 'helm', 0, 1, 0, 0),
(40, 'johan', 'helm', 'helm', 0, 2, 0, 0),
(41, 'johan', 'boot', 'boot', 0, 0, 2, 0),
(42, 'johan', 'boot2', 'boot', 0, 0, 11, 0),
(43, 'johan', 'helm', 'helm', 0, 1, 0, 0),
(45, 'johan', 'helm', 'helm', 0, 3, 0, 0),
(46, 'johan', 'boot1', 'boot', 0, 0, 17, 0),
(47, 'johan', 'pedang1', 'pedang', 47, 0, 4, 0),
(48, 'johan', 'armor1', 'armor', 2, 31, 0, 0),
(49, 'johan', 'helm1', 'helm', 0, 5, 0, 0),
(50, 'johan', 'boot1', 'boot', 0, 0, 10, 0),
(52, 'johan', 'pedang1', 'pedang', 47, 0, 7, 0),
(53, 'johan', 'helm', 'helm', 0, 0, 0, 0),
(54, 'johan', 'helm', 'helm', 0, 1, 0, 0),
(55, 'johan', 'pedang1', 'pedang', 44, 0, 8, 0),
(58, 'johan', 'helm', 'helm', 0, 4, 0, 0),
(59, 'johan', 'armor1', 'armor', 0, 41, 0, 0),
(60, 'johan', 'boot', 'boot', 0, 0, 3, 0),
(61, 'johan', 'pedang', 'pedang', 8, 0, 1, 0),
(62, 'johan', 'boot2', 'boot', 0, 0, 16, 0),
(64, 'johan', 'boot2', 'boot', 0, 0, 4, 0),
(66, 'johan', 'pedang', 'pedang', 3, 0, 4, 0),
(69, 'johan', 'pedang1', 'pedang', 22, 0, 7, 0),
(70, 'johan', 'pedang', 'pedang', 2, 0, 1, 0),
(72, 'johan', 'pedang', 'pedang', 1, 0, 4, 0),
(73, 'johan', 'pedang', 'pedang', 5, 0, 4, 0),
(74, 'johan', 'pedang2', 'pedang', 66, 0, 13, 0),
(75, 'johan', 'pedang1', 'pedang', 41, 0, 1, 0),
(76, 'johan', 'pedang1', 'pedang', 32, 0, 8, 0),
(77, 'johan', 'pedang1', 'pedang', 45, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `monster`
--

CREATE TABLE `monster` (
  `Id` char(8) NOT NULL,
  `Nama` varchar(20) DEFAULT NULL,
  `Atk` int(11) DEFAULT NULL,
  `Def` int(11) DEFAULT NULL,
  `Spd` int(11) DEFAULT NULL,
  `hp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `monster`
--

INSERT INTO `monster` (`Id`, `Nama`, `Atk`, `Def`, `Spd`, `hp`) VALUES
('0', 'ayam', 30, 30, 25, 1000),
('1', 'garuda', 50, 50, 25, 1200),
('2', 'slime', 100, 70, 30, 1500),
('3', 'gazer', 150, 100, 40, 2000),
('4', 'kelalawar', 200, 150, 50, 1000),
('5', 'mimic', 200, 150, 40, 2500),
('6', 'dragon', 300, 300, 100, 10000),
('7', 'monster', 100, 100, 100, 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hero`
--
ALTER TABLE `hero`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Id` (`Id`);

--
-- Indexes for table `iventori`
--
ALTER TABLE `iventori`
  ADD PRIMARY KEY (`idiventori`);

--
-- Indexes for table `monster`
--
ALTER TABLE `monster`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Id` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
