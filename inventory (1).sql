-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2024 at 12:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `menu_items`
--

CREATE TABLE `menu_items` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `options` varchar(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_items`
--

INSERT INTO `menu_items` (`id`, `name`, `price`, `stock`, `options`, `category`) VALUES
(48, 'Fries', 109, 100, 'Solo', 'Appetizers'),
(49, 'Fries', 159, 100, 'Family', 'Appetizers'),
(50, 'Nachos', 109, 100, 'Solo', 'Appetizers'),
(51, 'Nachos', 159, 100, 'Family', 'Appetizers'),
(53, 'Mojos', 79, 100, 'Solo', 'Appetizers'),
(62, 'Mojos', 109, 100, 'Family', 'Appetizers'),
(67, 'Pork Belly', 249, 78, 'W/out drinks', 'Rice Meals'),
(68, 'Pork Belly', 279, 100, 'With drinks', 'Rice Meals'),
(69, 'Tortang Talong', 249, 78, 'W/out drinks', 'Rice Meals'),
(70, 'Tortang Talong', 279, 100, 'With drinks', 'Rice Meals'),
(71, 'Pork Sisig', 249, 78, 'W/out drinks', 'Rice Meals'),
(72, 'Pork Sisig', 279, 100, 'With drinks', 'Rice Meals'),
(81, 'Tea1', 89, 100, 'Short', 'Tea'),
(82, 'Tea2', 89, 100, 'Short', 'Tea'),
(83, 'Tea3', 89, 100, 'Short', 'Tea'),
(84, 'Tea4', 89, 100, 'Short', 'Tea'),
(85, 'Tea5', 89, 100, 'Short', 'Tea'),
(86, 'Tea6', 89, 100, 'Short', 'Tea'),
(87, 'Tea7', 89, 100, 'Short', 'Tea'),
(88, 'Tea8', 89, 100, 'Short', 'Tea'),
(89, 'Tea1', 119, 100, 'Tall', 'Tea'),
(90, 'Tea2', 119, 100, 'Tall', 'Tea'),
(91, 'Tea3', 119, 100, 'Tall', 'Tea'),
(92, 'Tea4', 119, 100, 'Tall', 'Tea'),
(93, 'Tea5', 119, 100, 'Tall', 'Tea'),
(94, 'Adobo', 289, 78, 'With drinks', 'Rice Meals'),
(95, 'Adobo', 259, 78, 'W/out drinks', 'Rice Meals'),
(96, 'Pizza', 859, 100, 'Family', 'Appetizers'),
(97, 'Pizza', 159, 100, 'Slice', 'Appetizers');

-- --------------------------------------------------------

--
-- Table structure for table `user_tbl`
--

CREATE TABLE `user_tbl` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `username` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_tbl`
--

INSERT INTO `user_tbl` (`id`, `name`, `username`, `password`, `email`, `address`) VALUES
(2, 'Admin', 'admin123', 'adminpass', '', ''),
(9, 'Jhay', 'jhay1234', 'jhay1234', 'jhaylord1234@gmail.com', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menu_items`
--
ALTER TABLE `menu_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_tbl`
--
ALTER TABLE `user_tbl`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menu_items`
--
ALTER TABLE `menu_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- AUTO_INCREMENT for table `user_tbl`
--
ALTER TABLE `user_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
