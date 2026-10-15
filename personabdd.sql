-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 15-10-2026 a las 02:41:35
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `personabdd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ocupaciones`
--

CREATE TABLE `ocupaciones` (
  `id_ocupacion` int(11) NOT NULL,
  `ocupacion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ocupaciones`
--

INSERT INTO `ocupaciones` (`id_ocupacion`, `ocupacion`) VALUES
(1, 'Ingeniero'),
(2, 'Emprendedor'),
(3, 'Profesor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id_persona` int(11) NOT NULL,
  `nombre_persona` varchar(100) NOT NULL,
  `edad_persona` int(11) NOT NULL,
  `telefono_persona` varchar(9) NOT NULL,
  `sexo_persona` varchar(50) NOT NULL,
  `id_ocupacion` int(11) NOT NULL,
  `fecha_nac` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id_persona`, `nombre_persona`, `edad_persona`, `telefono_persona`, `sexo_persona`, `id_ocupacion`, `fecha_nac`) VALUES
(1, 'Alejandro Pineda', 45, '7722-4455', 'Masculino', 1, '1999-01-05'),
(2, 'Fernando Calderón', 21, '7667-7890', 'Masculino', 2, '2001-05-07'),
(3, 'Emerson Torres', 22, '7123-9800', 'Masculino', 3, '1999-08-03'),
(4, 'Salomon', 20, '1234-5678', 'Masculino', 1, '2000-08-03');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ocupaciones`
--
ALTER TABLE `ocupaciones`
  ADD PRIMARY KEY (`id_ocupacion`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id_persona`),
  ADD KEY `fk_persona_ocupacion` (`id_ocupacion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ocupaciones`
--
ALTER TABLE `ocupaciones`
  MODIFY `id_ocupacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `fk_persona_ocupacion` FOREIGN KEY (`id_ocupacion`) REFERENCES `ocupaciones` (`id_ocupacion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
