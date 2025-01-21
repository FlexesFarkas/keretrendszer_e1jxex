-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2025. Jan 21. 16:27
-- Kiszolgáló verziója: 10.4.32-MariaDB
-- PHP verzió: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `gamestore`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `games`
--

CREATE TABLE `games` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `price` int(11) NOT NULL,
  `developer_id` int(11) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `games`
--

INSERT INTO `games` (`id`, `title`, `description`, `price`, `developer_id`, `image_path`) VALUES
(16, 'Minecraft 2', 'Minecraft 2 a Minecraft folytatása, amely a klasszikus blokkos világot még nagyobb mélységekbe és lehetőségekbe repíti. Az új generációs grafika mellett a játék lenyűgöző biomokat, dinamikus időjárást, és még mélyebb föld alatti barlangrendszereket kínál. A továbbfejlesztett mesterséges intelligencia lehetővé teszi a falusiak és mobok interaktívabb viselkedését, míg az új építési és kézműves eszközök a kreativitás határtalanságát biztosítják. Multiplayer funkciókkal és történetvezérelt küldetésekkel gazdagítva a Minecraft 2 az eredeti játék szellemét örökíti meg, miközben új távlatokat nyit a kalandok világában. 🌍🛠️', 28000, 14, 'videos/elveszvekicsi.mp4'),
(17, 'Forza Horizon 6', 'A Forza Horizon 6 a világ legjobb autóversenyzős élményét nyújtja egy teljesen új, lélegzetelállító helyszínen. Az új epizódban még részletesebb autómodellek, dinamikus évszakváltások és fotorealisztikus környezetek várják a játékosokat. A kibővített multiplayer mód lehetőséget ad közösségi versenyekre, közös kihívásokra és új szintű testreszabhatóságra. A játék történetvezérelt kampánya felfedezéssel, egyedi kihívásokkal és versenyekkel vezet át gyönyörű tájakon, miközben több száz ikonikus autóval élhetjük át a sebesség szabadságát. Forza Horizon 6: Az autós kaland soha nem volt ennyire élvezetes! 🚗💨', 32001, 16, 'videos/elveszvekicsi.mp4'),
(18, 'Fortnite 2', 'A Fortnite 2 számos újítást és fejlesztést kínál az első részhez képest:\r\n\r\nFejlettebb grafika és dizájn: Az új verzió jobb vizuális részletességgel, élethűbb környezetekkel és dinamikusabb animációkkal rendelkezik, ami fokozza az élményt.\r\n\r\nÚj játékmódok: A második részben több játékmód is elérhető, például kooperatív módok és új, izgalmas kihívások.\r\n\r\nTovábbfejlesztett építkezési rendszer: Az építkezés dinamikáját finomították, lehetővé téve a gyorsabb és precízebb építkezést, ami taktikai előnyökhöz juttatja a játékosokat.\r\n\r\nInteraktív térkép és élő események: A Fortnite 2 térképe folyamatosan változik a játékosok tevékenységei és az élő események hatására, így minden egyes mérkőzés más és más élményt kínál.', 0, 14, 'videos/elveszvekicsi.mp4');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `purchase_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `purchases`
--

INSERT INTO `purchases` (`id`, `user_id`, `game_id`, `purchase_date`) VALUES
(35, 16, 17, '2025-01-21 10:21:12'),
(37, 14, 17, '2025-01-21 10:24:39'),
(38, 16, 16, '2025-01-21 10:44:40'),
(39, 21, 18, '2025-01-21 12:43:42'),
(40, 14, 18, '2025-01-21 15:23:10'),
(41, 14, 16, '2025-01-21 15:24:51'),
(42, 20, 18, '2025-01-21 15:25:27');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` text DEFAULT NULL,
  `review_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `reviews`
--

INSERT INTO `reviews` (`id`, `user_id`, `game_id`, `rating`, `comment`, `review_date`) VALUES
(7, 16, 16, 8, 'A Minecraft 2 fantasztikusan bővíti az eredeti játék világát. Az új mechanikák és a továbbfejlesztett grafikák igazán friss élményt nyújtanak, miközben hűek maradnak az eredeti varázsához. A generált világok még változatosabbak, és az új eszközök kreatív lehetőségek végtelen tárházát nyitják meg. Kihagyhatatlan folytatás a rajongóknak!', '2025-01-21 10:15:53'),
(8, 16, 17, 7, 'A Forza Horizon 6 lenyűgöző grafikai élményt és pazar vezetési élményt kínál. Az új helyszínek és autók még inkább magával ragadják a játékosokat, miközben a dinamikus időjárás és a nyílt világ felfedezése új dimenziót adnak.', '2025-01-21 10:22:41'),
(9, 14, 16, 6, 'egész jó', '2025-01-21 10:26:03'),
(11, 21, 18, 10, 'legjobb ingyenes game, 11/10', '2025-01-21 12:44:03'),
(13, 20, 18, 10, 'nagyon jó ingyenes játék', '2025-01-21 15:25:43');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(4, 'ADMIN'),
(3, 'DEVELOPER'),
(2, 'MODERATOR'),
(1, 'USER');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `userroles`
--

CREATE TABLE `userroles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `userroles`
--

INSERT INTO `userroles` (`user_id`, `role_id`) VALUES
(14, 4),
(16, 3),
(17, 1),
(18, 2),
(20, 1),
(21, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `balance` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `balance`) VALUES
(14, 'admin1', 'admin1@gmail.com', '$2a$10$fDSyTzo7ytPnntzP7Cw7kO23zNE0U81WtwoPavxJUqzyUMsEoCMLK', 99999999),
(16, 'ujdev', 'detarilevi@gmail.com', '$2a$10$f4fxjISl9DNMgo7kBZhPBOJnCtRQSOIFQ87rFYa10i6zRDYm1eIlq', 9916666),
(17, 'ujuser', 'szesztilany@gmail.com', '$2a$10$DbTMUdsZ/QFa89mXGS2s7.zTiWSFFYbCgEq1cPQcwVCYOtN8NDZMy', 290214),
(18, 'ujmoderator', 'asdasdas@asd.com', '$2a$10$RAbyZdKziXbpN2G1nYqvSug8/jEZWkvd5mGPrOWnGsqCh8UguYqZm', 0),
(20, 'csoro', 'csoro@gmail.com', '$2a$10$Y26iYMhWy1.0BVDjVMjSoe5Vom26Fom.Dd8n142Edxb7/NifgkgGS', 0),
(21, 'felhasznalo', 'asd@asd.com', '$2a$10$KDT3Dac7d/sQZIQJcHgLZ.l51BZ4I7ydIPvxnQvuukIGzdC4rpaIK', 0);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `title` (`title`),
  ADD KEY `games_ibfk_1` (`developer_id`);

--
-- A tábla indexei `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`,`game_id`),
  ADD KEY `purchases_ibfk_2` (`game_id`);

--
-- A tábla indexei `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`,`game_id`),
  ADD KEY `reviews_ibfk_2` (`game_id`);

--
-- A tábla indexei `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- A tábla indexei `userroles`
--
ALTER TABLE `userroles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `userroles_ibfk_2` (`role_id`);

--
-- A tábla indexei `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `games`
--
ALTER TABLE `games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT a táblához `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT a táblához `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT a táblához `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `games`
--
ALTER TABLE `games`
  ADD CONSTRAINT `games_ibfk_1` FOREIGN KEY (`developer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Megkötések a táblához `purchases`
--
ALTER TABLE `purchases`
  ADD CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `purchases_ibfk_2` FOREIGN KEY (`game_id`) REFERENCES `games` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Megkötések a táblához `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`game_id`) REFERENCES `games` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Megkötések a táblához `userroles`
--
ALTER TABLE `userroles`
  ADD CONSTRAINT `userroles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `userroles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
