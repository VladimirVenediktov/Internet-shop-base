-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Дек 11 2018 г., 16:31
-- Версия сервера: 5.5.25
-- Версия PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `shoponline`
--

-- --------------------------------------------------------

--
-- Структура таблицы `basket`
--

CREATE TABLE IF NOT EXISTS `basket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_good` int(11) NOT NULL,
  `id_basket` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=116 ;

-- --------------------------------------------------------

--
-- Структура таблицы `goods`
--

CREATE TABLE IF NOT EXISTS `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `goods`
--

INSERT INTO `goods` (`id`, `title`, `price`) VALUES
(1, 'Audi', 1500000),
(2, 'BMW', 1600000),
(3, 'Skoda', 900000),
(4, 'VW', 980000),
(5, 'LADA', 600000);

-- --------------------------------------------------------

--
-- Структура таблицы `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_basket` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=115 ;

--
-- Дамп данных таблицы `orders`
--

INSERT INTO `orders` (`id`, `id_user`, `id_basket`, `status`) VALUES
(114, 13, 800, 'в работе');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `fio` varchar(50) NOT NULL,
  `phone` varchar(30) NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id_user`, `fio`, `phone`) VALUES
(1, 'Petrov', '231219'),
(3, 'Gordeev', '231318'),
(4, 'Sidorov Y.R.', '891320000'),
(5, 'Oleg Ivanov', '567677655'),
(12, 'Венедиктов Владимир Сергеевич', '8(923) 188-50-00'),
(13, 'Ольга Сергеевна', '89231987000');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
