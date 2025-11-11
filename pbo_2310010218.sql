-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 11 Nov 2025 pada 02.57
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo_2310010218`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin123'),
(2, 'operator', 'operator123'),
(3, 'asepwest', 'user');

-- --------------------------------------------------------

--
-- Struktur dari tabel `laporan_kebakaran`
--

CREATE TABLE `laporan_kebakaran` (
  `id` int(11) NOT NULL,
  `alamat` varchar(200) DEFAULT NULL,
  `tgl` date DEFAULT NULL,
  `waktu` varchar(50) DEFAULT NULL,
  `lap` text DEFAULT NULL,
  `lak` text DEFAULT NULL,
  `jenis` int(11) DEFAULT NULL,
  `luas` varchar(50) DEFAULT NULL,
  `jumlah` varchar(100) DEFAULT NULL,
  `taksiran` varchar(100) DEFAULT NULL,
  `jarak` text DEFAULT NULL,
  `perjalanan` text DEFAULT NULL,
  `armada` varchar(100) DEFAULT NULL,
  `kekuatan` varchar(100) DEFAULT NULL,
  `pelapor` text DEFAULT NULL,
  `file` text DEFAULT NULL,
  `id_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `laporan_kebakaran`
--

INSERT INTO `laporan_kebakaran` (`id`, `alamat`, `tgl`, `waktu`, `lap`, `lak`, `jenis`, `luas`, `jumlah`, `taksiran`, `jarak`, `perjalanan`, `armada`, `kekuatan`, `pelapor`, `file`, `id_admin`) VALUES
(1, 'Jl. Merdeka No.1', '2025-04-10', '14:00', 'Kebakaran Gudang', 'PMK Kota', 1, '200m2', '20', '50jt', '2km', '5 menit', '2025-04-10', '10 personel', 'agus', 'laporan1.pdf', 1),
(2, 'jalan jalan', '2025-09-09', '00:00', 'kebakaran', 'bpk', 2, '1000m2', '21', '10m', '100m', '1menit', '2025-09-09', '10', 'asep', 'pdf', 1),
(3, 'jaln jlan', '2025-09-09', '00:00', 'kebakarna', 'polras', 1, '10o0213n', '100', '100m', '9m', '1jam', 'darat', '1', 'agus', 'pdf', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `sk`
--

CREATE TABLE `sk` (
  `ids` int(11) NOT NULL,
  `nosk` varchar(100) NOT NULL,
  `tglsk` date NOT NULL,
  `nama` varchar(150) DEFAULT NULL,
  `ket` text DEFAULT NULL,
  `file` text DEFAULT NULL,
  `id_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `sk`
--

INSERT INTO `sk` (`ids`, `nosk`, `tglsk`, `nama`, `ket`, `file`, `id_admin`) VALUES
(1, 'SK-001', '2025-03-01', 'SK Pengangkatan', 'Pegawai Baru', 'sk_pengangkatan.pdf', 1),
(2, 'sk-002', '2025-09-09', 'asepwest', 'pns', 'pdf', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `surat_keluar`
--

CREATE TABLE `surat_keluar` (
  `idk` int(11) NOT NULL,
  `no_suratk` varchar(100) NOT NULL,
  `tglk` date NOT NULL,
  `perihal` varchar(200) DEFAULT NULL,
  `tujuan` varchar(150) DEFAULT NULL,
  `pengirimk` varchar(150) DEFAULT NULL,
  `filek` text DEFAULT NULL,
  `id_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `surat_keluar`
--

INSERT INTO `surat_keluar` (`idk`, `no_suratk`, `tglk`, `perihal`, `tujuan`, `pengirimk`, `filek`, `id_admin`) VALUES
(1, 'SK-001', '2025-02-15', 'Laporan Mingguan', 'Walikota', 'Admin', 'laporan.docx', 1),
(2, 'sk-002', '2025-09-09', 'kebakaran', 'jakarta', 'asepwest', 'pdf', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `surat_masuk`
--

CREATE TABLE `surat_masuk` (
  `idm` int(11) NOT NULL,
  `no_surat` varchar(100) NOT NULL,
  `tgl` date NOT NULL,
  `tgl_terima` date NOT NULL,
  `perihal` varchar(200) DEFAULT NULL,
  `tujuan` varchar(150) DEFAULT NULL,
  `pengirim` varchar(150) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `id_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `surat_masuk`
--

INSERT INTO `surat_masuk` (`idm`, `no_surat`, `tgl`, `tgl_terima`, `perihal`, `tujuan`, `pengirim`, `file`, `id_admin`) VALUES
(1, 'SM-001', '2025-01-10', '2025-01-11', 'Undangan Rapat', 'Kepala Dinas', 'Sekretariat', 'undangan.pdf', 1),
(2, 'sm-002', '2025-09-09', '2025-09-10', 'kebakaran', 'jakarta', 'asep', 'pdf', 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `laporan_kebakaran`
--
ALTER TABLE `laporan_kebakaran`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indeks untuk tabel `sk`
--
ALTER TABLE `sk`
  ADD PRIMARY KEY (`ids`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indeks untuk tabel `surat_keluar`
--
ALTER TABLE `surat_keluar`
  ADD PRIMARY KEY (`idk`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indeks untuk tabel `surat_masuk`
--
ALTER TABLE `surat_masuk`
  ADD PRIMARY KEY (`idm`),
  ADD KEY `id_admin` (`id_admin`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `laporan_kebakaran`
--
ALTER TABLE `laporan_kebakaran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `sk`
--
ALTER TABLE `sk`
  MODIFY `ids` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `surat_keluar`
--
ALTER TABLE `surat_keluar`
  MODIFY `idk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `surat_masuk`
--
ALTER TABLE `surat_masuk`
  MODIFY `idm` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `laporan_kebakaran`
--
ALTER TABLE `laporan_kebakaran`
  ADD CONSTRAINT `laporan_kebakaran_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `sk`
--
ALTER TABLE `sk`
  ADD CONSTRAINT `sk_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `surat_keluar`
--
ALTER TABLE `surat_keluar`
  ADD CONSTRAINT `surat_keluar_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `surat_masuk`
--
ALTER TABLE `surat_masuk`
  ADD CONSTRAINT `surat_masuk_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
