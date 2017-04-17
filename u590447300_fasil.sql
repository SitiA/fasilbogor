-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Waktu pembuatan: 10. April 2017 jam 15:29
-- Versi Server: 5.5.16
-- Versi PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u590447300_fasil`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail`
--

CREATE TABLE IF NOT EXISTS `detail` (
  `id_detail` int(11) NOT NULL AUTO_INCREMENT,
  `id_kategori` int(11) NOT NULL,
  `id_info` int(11) NOT NULL,
  `nama_detail` varchar(40) NOT NULL,
  `alamat_detail` varchar(255) NOT NULL,
  `jam_opr_detail` varchar(40) NOT NULL,
  `lat_detail` varchar(30) NOT NULL,
  `long_detail` varchar(30) NOT NULL,
  `no_telepon` varchar(30) NOT NULL,
  PRIMARY KEY (`id_detail`),
  KEY `testc2` (`id_kategori`),
  KEY `id_kategori` (`id_info`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data untuk tabel `detail`
--

INSERT INTO `detail` (`id_detail`, `id_kategori`, `id_info`, `nama_detail`, `alamat_detail`, `jam_opr_detail`, `lat_detail`, `long_detail`, `no_telepon`) VALUES
(1, 3, 15, 'SMA NEGERI 1 Bogor', 'Jl. Ir. Haji Djuanda No.16, Paledang, Bogor Tengah, Kota Bogor, Jawa Barat 16122', '', '-6.5975587', '106.7909466', '(0251) 8321397'),
(2, 3, 16, 'SMA NEGERI 2 Bogor', 'Jl. Keranji Ujung No. 1, Bantarjati, Bogor Utara, Kota Bogor, Jawa Barat 16165', '', '-6.5530834', '106.7878929', '(0251) 8318761'),
(3, 3, 17, 'SMA NEGERI 3 Bogor', 'Jl. Pakuan No.4, Baranangsiang, Bogor Timur, Kota Bogor, Jawa Barat 16143', '', '-6.6074587', '106.8091779', '(0251) 8321747'),
(4, 3, 18, 'SMA NEGERI 4 Bogor', 'Jl. Dreded V No. 36, Empang, Bogor Selatan,  Kota Bogor.', '', '-6.6162451', '106.7987421', '(0251) 8323951'),
(5, 3, 19, 'SMA NEGERI 5 Bogor', 'Jl. Manunggal No.25, Menteng, Bogor Barat,  Kota Bogor.', '', '-6.5594882', '106.695066', ' 0896-9292-1621'),
(6, 3, 20, 'SMA NEGERI 7 Bogor', 'Jl. Palupuh No.7, Tegal Gundil, Bogor Utara, Kota Bogor.', '', '-6.5663529', '106.7868277', '(0251) 8326739'),
(7, 3, 21, 'SMA NEGERI 8 Bogor', 'Jl. BTN Ciparigi No.60, Kedunghalang, Bogor Utara, Kota Bogor.', '', '-6.5452702', '106.8178243', '(0251) 8652927'),
(8, 3, 22, 'SMA NEGERI 9 Bogor', 'Jl. Kartini No.1, Ciwaringin, Bogor Tengah, Kota Bogor.', '', '-6.5928119', '106.7862997', '(0251) 8324361'),
(9, 3, 23, 'SMA NEGERI 10 Bogor', 'Jl. Pinang Raya, Curug Mekar, Bogor Barat, Kota Bogor.', '', '-6.559669', '106.7629186', '(0251) 7534993'),
(10, 3, 24, 'MA NEGERI 2 Bogor', 'Jl. Raya Pajajaran No. 6, Baranangsiang, Bogor Timur, Kota Bogor.', '', '-6.6087316', '106.805692', '(0251) 8321740'),
(11, 4, 25, 'Lapangan Sempur', 'Jl. Sempur Kaler No.26-31, Sempur, Bogor Tengah, Kota Bogor.', '24 Jam', '-6.5918914', '106.7992413', '-'),
(12, 4, 26, 'Gunung Pancar', 'Jl. Sentul Paradise Park, Bojong Koneng, ', '24 Jam', '-6.6141326', '106.9065852', '-'),
(13, 4, 27, 'Kampung Budaya', 'Jl. Endang Sumawijaya, Sindang Barang, Kab Bogor', '09.00 - 17.00', '-6.6316577', '106.7594447', '0817-406-363'),
(14, 4, 28, 'Kebun Raya Bogor', 'Jl. Ir. Haji Djuanda No.13, Paledang, Bogor Tengah, Kota Bogor.', '08.00 - 17.00', '-6.5976236', '106.7973811', '(0251) 8322187'),
(15, 4, 29, 'Curug Bidadari', 'Jl. Sentul Paradise Park, Bojong Koneng, Bogor.', '08.00 - 17.00', '-6.614016', '106.9062115', '-'),
(16, 4, 30, 'The Jungle Water Adventure', 'Perumahan BNR, Jl. Dreded Pahlawan, Bogor Selatan, Kota Bogor.', '08.00 - 18.00', '-6.6345172', '106.7926724', '(0251) 8212666'),
(17, 4, 31, 'Kebun Raya Cibodas', 'Jl. Kebun Raya Cibodas, Sindangjaya, Cipanas, Kab. Cianjur', '08.00 - 16.00', '-6.7412791', '106.9725896', '(0263) 512233'),
(18, 4, 32, 'Suaka Elang', 'Jl. Ciburayut, Cigombong, Bogor.', '24 Jam', '-6.7232932', '106.7793266', '-'),
(19, 1, 1, 'Polsek Bogor Selatan', 'Jl. Pahlawan No.100, 16, Empang, Bogor, Kota Bogor.', '24 Jam', '-6.6118971', '106.7985533', '(0251) 321972'),
(20, 1, 2, 'Polsek Bogor Timur', 'Jl. Raya Pajajaran No.16, Bogor Timur, Bogor', '24 Jam', '-6.6091867', '106.8104227', '(0251) 8322014'),
(21, 1, 3, 'Polresta Bogor', 'Jl. Raya Kedung Halang No. 64, Kedunghalang, Bogor Utara, Kota Bogor.', '24 Jam', '-6.5571007', '106.7451567', '(0251) 664222'),
(22, 1, 4, 'Polsek Dramaga', 'Jl. Raya Dramaga, Margajaya, Bogor Barat, Kota Bogor. ', '24 Jam', '-6.562847', '106.7245633', '(0251) 624107'),
(23, 1, 5, 'Polsek Sukaraja', 'Jl. Raya Jakarta-Bogor, Cibuluh, Bogor Utara, Kota Bogor.', '24 Jam', '-6.5516075', '106.8214044', '(0251) 8656678'),
(24, 1, 6, 'Polsekta Bogor Barat', 'Jl. DR. Semeru No.89, Menteng, Bogor Barat, Kota Bogor.', '24 Jam', '-6.5802297', '106.7736378', '(0251) 8322054'),
(25, 1, 7, 'Polsek Bogor Utara', 'Jl. Padjadjaran No.26, Bantarjati, Bogor Utara, Kota Bogor.', '24 Jam', '-6.5792377', '106.8046286', '(0251) 8325505'),
(26, 2, 8, 'RS PMI', 'Jl. Padjadjaran No.80, Bantarjati, \r\nBogor Utara.', '24 Jam', '-6.5986259', '106.8029552', '(0251) 8324080'),
(27, 2, 9, 'RS Bogor Medical Center', 'Jl. Pajajaran Indah V No. 97, Baranangsiang, Bogor Timur,\r\nKota Bogor.', '24 Jam', '-6.6080002', '106.811038', '(0251) 8307900'),
(28, 2, 10, 'RS Islam Bogor', 'Jl. Perdana Raya No.22, Kedung Badak,\r\nTanah Sereal, Kota Bogor.', '24 Jam', '-6.558953', '106.7921104', '(0251) 8316822'),
(29, 2, 11, 'RS Salak', 'Jl. Jenderal Sudirman No. 8, Sempur,\r\n Bogor Tengah, Kota Bogor.', '24 Jam', '-6.591073', '106.7951534', '(0251) 8344609'),
(30, 2, 12, 'RS Ummi', 'Jl. Empang II No. 2, Empang, Bogor Selatan, Kota Bogor.', '24 Jam', '-6.6087212', '106.7946497', '(0251) 8341600'),
(31, 2, 13, 'RS Bersalin Pasutri', 'Jl. Merak No.3, Tanah Sareal, Kota Bogor.', '24 Jam', '-6.5710747', '106.7968776', '(0251) 8349707'),
(32, 2, 14, 'RS Bhayangkara', 'Jl. Kapten Muslihat No.18, Paledang, Bogor Tengah,\r\nKota Bogor.', '24 Jam', '-6.5966194', '106.7891747', '(0251) 8348987');

-- --------------------------------------------------------

--
-- Struktur dari tabel `info`
--

CREATE TABLE IF NOT EXISTS `info` (
  `id_info` int(11) NOT NULL AUTO_INCREMENT,
  `id_kategori` int(11) NOT NULL,
  `nama_info` varchar(40) NOT NULL,
  PRIMARY KEY (`id_info`),
  KEY `testc1` (`id_kategori`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data untuk tabel `info`
--

INSERT INTO `info` (`id_info`, `id_kategori`, `nama_info`) VALUES
(1, 1, 'Polsek Bogor Selatan'),
(2, 1, 'Polsek Bogor Timur'),
(3, 1, 'Polresta Bogor'),
(4, 1, 'Polsek Dramaga'),
(5, 1, 'Polsek Sukaraja'),
(6, 1, 'Polsekta Bogor Barat'),
(7, 1, 'Polsek Bogor Utara'),
(8, 2, 'RS PMI'),
(9, 2, 'RS Bogor Medical Center'),
(10, 2, 'RS Islam Bogor'),
(11, 2, 'RS Salak'),
(12, 2, 'RS Ummi'),
(13, 2, 'RS Bersalin Pasutri'),
(14, 2, 'RS Bhayangkara'),
(15, 3, 'SMA NEGERI 1 Bogor'),
(16, 3, 'SMA NEGERI 2 Bogor'),
(17, 3, 'SMA NEGERI 3 Bogor'),
(18, 3, 'SMA NEGERI 4 Bogor'),
(19, 3, 'SMA NEGERI 5 Bogor'),
(20, 3, 'SMA NEGERI 7 Bogor'),
(21, 3, 'SMA NEGERI 8 Bogor'),
(22, 3, 'SMA NEGERI 9 Bogor'),
(23, 3, 'SMA NEGERI 10 Bogor'),
(24, 3, 'MA Negeri 2 Bogor'),
(25, 4, 'Lapangan Sempur'),
(26, 4, 'Gunung Pancar'),
(27, 4, 'Kampung Budaya'),
(28, 4, 'Kebun Raya Bogor'),
(29, 4, 'Curug Bidadari'),
(30, 4, 'The Jungle Water Adventure'),
(31, 4, 'Kebun Raya Cibodas'),
(32, 4, 'Suaka Elang');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategori`
--

CREATE TABLE IF NOT EXISTS `kategori` (
  `id_kategori` int(11) NOT NULL AUTO_INCREMENT,
  `nama_kategori` varchar(40) NOT NULL,
  PRIMARY KEY (`id_kategori`),
  UNIQUE KEY `country` (`nama_kategori`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data untuk tabel `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`) VALUES
(1, 'Kantor Polisi'),
(2, 'Rumah Sakit'),
(3, 'Sekolah'),
(4, 'Tempat Wisata');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
