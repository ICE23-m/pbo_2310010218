/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pbo_2310010218;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO 
 */
public class CRUD {
    private String database = "pbo_2310010218"; 
    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost/" + database;
    public Connection koneksiDB;
    public boolean validasi = false;
    
    public String var_username_admin, var_password_admin;
    public String var_alamat_lap, var_tgl_lap, var_waktu_lap, var_lap_lap, var_lak_lap,
                  var_jenis_lap, var_luas_lap, var_jumlah_lap, var_taksiran_lap,
                  var_jarak_lap, var_perjalanan_lap, var_armada_lap, var_kekuatan_lap,
                  var_pelapor_lap, var_file_lap;
    
    public String var_nosk_sk, var_tglsk_sk, var_nama_sk, var_ket_sk, var_file_sk;
    
    public String var_no_suratk_skel, var_tglk_skel, var_perihal_skel,
                  var_tujuan_skel, var_pengirimk_skel, var_filek_skel;

    public String var_no_surat_sm, var_tgl_sm, var_tgl_terima_sm, var_perihal_sm,
                  var_tujuan_sm, var_pengirim_sm, var_file_sm;

    public CRUD() {
        try {
            Driver driverKoneksi = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driverKoneksi);
            koneksiDB = DriverManager.getConnection(url, username, password);
            System.out.println("Berhasil koneksi ke DB: " + database);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Koneksi Database: " + e.getMessage());
            System.exit(0);
        }
    }

    public void loadData(JTable tabel, String sql) {
        try (Statement perintah = koneksiDB.createStatement();
             ResultSet ds = perintah.executeQuery(sql)) {

            DefaultTableModel model = new DefaultTableModel();
            ResultSetMetaData metaData = ds.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            model.getDataVector().clear();
            model.fireTableDataChanged();
            
            while (ds.next()) {
                Object[] baris = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    baris[i] = ds.getObject(i + 1);
                }
                model.addRow(baris);
            }
            tabel.setModel(model);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Load Data Gagal: " + e.getMessage());
        }
    }
    
    public void simpanAdminStatement(String id, String username, String password) {
        try {
            Statement checkData = koneksiDB.createStatement();
            String checkPrimary = "SELECT * FROM admin WHERE id='" +id+ "'";
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "Username: " + result.getString("username");
                JOptionPane.showMessageDialog(null, "ID Admin sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_username_admin = result.getString("username");
                this.var_password_admin = result.getString("password");
            }
            else {
                String sql = "INSERT INTO admin (id, username, password) VALUE ('" 
                        +id+ "', '" +username+ "', '" +password+ "')";
                Statement perintah = koneksiDB.createStatement();
                perintah.execute(sql);
            
                JOptionPane.showMessageDialog(null, "Admin Berhasil Disimpan!");
                
                this.validasi = false;
                this.var_username_admin = null;
                this.var_password_admin = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan Admin Gagal: " + e.getMessage());
        }
    }

    public void simpanAdminPrepared(String id, String username, String password) {
        try {
            String checkPrimary = "SELECT * FROM admin WHERE id='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet res = checkData.executeQuery(checkPrimary);
            
            if (res.next()) {
                String isi = "Username: " + res.getString("username");
                JOptionPane.showMessageDialog(null, "ID Admin sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_username_admin = res.getString("username");
                this.var_password_admin = res.getString("password");
            }
            else {
                String sql = "INSERT INTO admin (id, username, password) VALUE (?, ?, ?)";
                PreparedStatement perintah = koneksiDB.prepareStatement(sql);
                perintah.setString(1, id);
                perintah.setString(2, username);
                perintah.setString(3, password);
                perintah.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "Berhasil disimpan!");
                
                this.validasi = false;
                this.var_username_admin = null;
                this.var_password_admin = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void ubahAdminStatement(String id, String username, String password) {
        try {
            String sqlUbah = "UPDATE admin SET username='" +username+ 
                             "', password='" +password+ 
                             "' WHERE id='" +id+ "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahAdminPrepared(String id, String username, String password) {
        try {
            String sqlUbah = "UPDATE admin SET username=?, password=? WHERE id=?";
            PreparedStatement ubah = koneksiDB.prepareStatement(sqlUbah);
            ubah.setString(1, username);
            ubah.setString(2, password);
            ubah.setString(3, id);
            ubah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");            
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusAdminStatement(String id) {
        try {
            String sqlHapus = "DELETE FROM admin WHERE id='" +id+ "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusAdminPrepared(String id) {
        try {
            String sqlHapus = "DELETE FROM admin WHERE id=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanSuratMasukStatement(String id, String no_surat, String tgl, String tgl_terima, String perihal, String tujuan, String pengirim, String file) {
        try {
            String checkPrimary = "SELECT * FROM surat_masuk WHERE idm='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "No. Surat: " + result.getString("no_surat") +
                             "\nTanggal: " + result.getString("tgl") +
                             "\nTgl. Terima: " + result.getString("tgl_terima") +
                             "\nPerihal: " + result.getString("perihal") +
                             "\nTujuan: " + result.getString("tujuan") +
                             "\nPengirim: " + result.getString("pengirim") +
                             "\nFile: " + result.getString("file");
                
                JOptionPane.showMessageDialog(null, "ID Surat Masuk sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_no_surat_sm = result.getString("no_surat");
                this.var_tgl_sm = result.getString("tgl");
                this.var_tgl_terima_sm = result.getString("tgl_terima");
                this.var_perihal_sm = result.getString("perihal");
                this.var_tujuan_sm = result.getString("tujuan");
                this.var_pengirim_sm = result.getString("pengirim");
                this.var_file_sm = result.getString("file");
            }
            else {
                String sql = "INSERT INTO surat_masuk (idm, no_surat, tgl, tgl_terima, perihal, tujuan, pengirim, file) VALUE ('" 
                        +id+ "', '" +no_surat+ "', '" +tgl+ "', '" +tgl_terima+ "', '" 
                        +perihal+ "', '" +tujuan+ "', '" +pengirim+ "', '" +file+ "')";
                Statement perintah = koneksiDB.createStatement();
                perintah.execute(sql);
            
                JOptionPane.showMessageDialog(null, "Surat Masuk Berhasil Disimpan!");
                this.validasi = false;
                this.var_no_surat_sm = null;
                this.var_tgl_sm = null;
                this.var_tgl_terima_sm = null;
                this.var_perihal_sm = null;
                this.var_tujuan_sm = null;
                this.var_pengirim_sm = null;
                this.var_file_sm = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan Surat Masuk Gagal: " + e.getMessage());
        }
    }
    
    public void simpanSuratMasukPrepared(String id, String no_surat, String tgl, String tgl_terima, String perihal, String tujuan, String pengirim, String file) {
        try {
            String checkPrimary = "SELECT * FROM surat_masuk WHERE idm='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "No. Surat: " + result.getString("no_surat") +
                             "\nTanggal: " + result.getString("tgl") +
                             "\nTgl. Terima: " + result.getString("tgl_terima") +
                             "\nPerihal: " + result.getString("perihal") +
                             "\nTujuan: " + result.getString("tujuan") +
                             "\nPengirim: " + result.getString("pengirim") +
                             "\nFile: " + result.getString("file");
                
                JOptionPane.showMessageDialog(null, "ID Surat Masuk sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_no_surat_sm = result.getString("no_surat");
                this.var_tgl_sm = result.getString("tgl");
                this.var_tgl_terima_sm = result.getString("tgl_terima");
                this.var_perihal_sm = result.getString("perihal");
                this.var_tujuan_sm = result.getString("tujuan");
                this.var_pengirim_sm = result.getString("pengirim");
                this.var_file_sm = result.getString("file");
            }
            else {
                String sql = "INSERT INTO surat_masuk (idm, no_surat, tgl, tgl_terima, perihal, tujuan, pengirim, file) VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = koneksiDB.prepareStatement(sql);
                perintah.setString(1, id);
                perintah.setString(2, no_surat);
                perintah.setString(3, tgl);
                perintah.setString(4, tgl_terima);
                perintah.setString(5, perihal);
                perintah.setString(6, tujuan);
                perintah.setString(7, pengirim);
                perintah.setString(8, file);
                perintah.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "Surat Masuk Berhasil Disimpan!");
                this.validasi = false;
                this.var_no_surat_sm = null;
                this.var_tgl_sm = null;
                this.var_tgl_terima_sm = null;
                this.var_perihal_sm = null;
                this.var_tujuan_sm = null;
                this.var_pengirim_sm = null;
                this.var_file_sm = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan Surat Masuk Gagal: " + e.getMessage());
        }
    }

    public void ubahSuratMasukStatement(String id, String no_surat, String tgl, String tgl_terima, String perihal, String tujuan, String pengirim, String file) {
        try {
            String sqlUbah = "UPDATE surat_masuk SET no_surat='" +no_surat+ 
                             "', tgl='" +tgl+ "', tgl_terima='" +tgl_terima+ 
                             "', perihal='" +perihal+ "', tujuan='" +tujuan+
                             "', pengirim='" +pengirim+ "', file='" +file+
                             "' WHERE idm='" +id+ "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Surat Masuk Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah Surat Masuk Gagal: " + e.getMessage());
        }
    }
    
    public void ubahSuratMasukPrepared(String id, String no_surat, String tgl, String tgl_terima, String perihal, String tujuan, String pengirim, String file) {
        try {
            String sqlUbah = "UPDATE surat_masuk SET no_surat=?, tgl=?, tgl_terima=?, perihal=?, tujuan=?, pengirim=?, file=? WHERE idm=?";
            PreparedStatement ubah = koneksiDB.prepareStatement(sqlUbah);
            ubah.setString(1, no_surat);
            ubah.setString(2, tgl);
            ubah.setString(3, tgl_terima);
            ubah.setString(4, perihal);
            ubah.setString(5, tujuan);
            ubah.setString(6, pengirim);
            ubah.setString(7, file);
            ubah.setString(8, id);
            ubah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Surat Masuk Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah Surat Masuk Gagal: " + e.getMessage());
        }
    }

    public void hapusSuratMasukStatement(String id) {
        try {
            String sqlHapus = "DELETE FROM surat_masuk WHERE idm='" +id+ "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Surat Masuk Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus Surat Masuk Gagal: " + e.getMessage());
        }
    }
    
    public void hapusSuratMasukPrepared(String id) {
        try {
            String sqlHapus = "DELETE FROM surat_masuk WHERE idm=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Surat Masuk Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus Surat Masuk Gagal: " + e.getMessage());
        }
    }

    public void simpanSuratKeluarStatement(String id, String no_surat, String tgl, String perihal, String tujuan, String pengirim, String file) {
        try {
            String checkPrimary = "SELECT * FROM surat_keluar WHERE idk='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "No. Surat: " + result.getString("no_suratk") +
                             "\nTanggal: " + result.getString("tglk") +
                             "\nPerihal: " + result.getString("perihal") +
                             "\nTujuan: " + result.getString("tujuan") +
                             "\nPengirim: " + result.getString("pengirimk") +
                             "\nFile: " + result.getString("filek");
                
                JOptionPane.showMessageDialog(null, "ID Surat Keluar sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_no_suratk_skel = result.getString("no_suratk");
                this.var_tglk_skel = result.getString("tglk");
                this.var_perihal_skel = result.getString("perihal");
                this.var_tujuan_skel = result.getString("tujuan");
                this.var_pengirimk_skel = result.getString("pengirimk");
                this.var_filek_skel = result.getString("filek");
            }
            else {
                String sql = "INSERT INTO surat_keluar (idk, no_suratk, tglk, perihal, tujuan, pengirimk, filek) VALUE ('" 
                        +id+ "', '" +no_surat+ "', '" +tgl+ "', '" +perihal+ "', '" 
                        +tujuan+ "', '" +pengirim+ "', '" +file+ "')";
                Statement perintah = koneksiDB.createStatement();
                perintah.execute(sql);
            
                JOptionPane.showMessageDialog(null, "Surat Keluar Berhasil Disimpan!");
                this.validasi = false;
                this.var_no_suratk_skel = null;
                this.var_tglk_skel = null;
                this.var_perihal_skel = null;
                this.var_tujuan_skel = null;
                this.var_pengirimk_skel = null;
                this.var_filek_skel = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan Surat Keluar Gagal: " + e.getMessage());
        }
    }
    
    public void simpanSuratKeluarPrepared(String id, String no_surat, String tgl, String perihal, String tujuan, String pengirim, String file) {
        try {
            String checkPrimary = "SELECT * FROM surat_keluar WHERE idk='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "No. Surat: " + result.getString("no_suratk") +
                             "\nTanggal: " + result.getString("tglk") +
                             "\nPerihal: " + result.getString("perihal") +
                             "\nTujuan: " + result.getString("tujuan") +
                             "\nPengirim: " + result.getString("pengirimk") +
                             "\nFile: " + result.getString("filek");
                
                JOptionPane.showMessageDialog(null, "ID Surat Keluar sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_no_suratk_skel = result.getString("no_suratk");
                this.var_tglk_skel = result.getString("tglk");
                this.var_perihal_skel = result.getString("perihal");
                this.var_tujuan_skel = result.getString("tujuan");
                this.var_pengirimk_skel = result.getString("pengirimk");
                this.var_filek_skel = result.getString("filek");
            }
            else {
                String sql = "INSERT INTO surat_keluar (idk, no_suratk, tglk, perihal, tujuan, pengirimk, filek) VALUE (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = koneksiDB.prepareStatement(sql);
                perintah.setString(1, id);
                perintah.setString(2, no_surat);
                perintah.setString(3, tgl);
                perintah.setString(4, perihal);
                perintah.setString(5, tujuan);
                perintah.setString(6, pengirim);
                perintah.setString(7, file);
                perintah.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "Surat Keluar Berhasil Disimpan!");
                this.validasi = false;
                this.var_no_suratk_skel = null;
                this.var_tglk_skel = null;
                this.var_perihal_skel = null;
                this.var_tujuan_skel = null;
                this.var_pengirimk_skel = null;
                this.var_filek_skel = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan Surat Keluar Gagal: " + e.getMessage());
        }
    }

    public void ubahSuratKeluarStatement(String id, String no_surat, String tgl, String perihal, String tujuan, String pengirim, String file) {
        try {
            String sqlUbah = "UPDATE surat_keluar SET no_suratk='" +no_surat+ 
                             "', tglk='" +tgl+ "', perihal='" +perihal+ 
                             "', tujuan='" +tujuan+ "', pengirimk='" +pengirim+
                             "', filek='" +file+
                             "' WHERE idk='" +id+ "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Surat Keluar Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah Surat Keluar Gagal: " + e.getMessage());
        }
    }
    
    public void ubahSuratKeluarPrepared(String id, String no_surat, String tgl, String perihal, String tujuan, String pengirim, String file) {
        try {
            String sqlUbah = "UPDATE surat_keluar SET no_suratk=?, tglk=?, perihal=?, tujuan=?, pengirimk=?, filek=? WHERE idk=?";
            PreparedStatement ubah = koneksiDB.prepareStatement(sqlUbah);
            ubah.setString(1, no_surat);
            ubah.setString(2, tgl);
            ubah.setString(3, perihal);
            ubah.setString(4, tujuan);
            ubah.setString(5, pengirim);
            ubah.setString(6, file);
            ubah.setString(7, id);
            ubah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Surat Keluar Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah Surat Keluar Gagal: " + e.getMessage());
        }
    }

    public void hapusSuratKeluarStatement(String id) {
        try {
            String sqlHapus = "DELETE FROM surat_keluar WHERE idk='" +id+ "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Surat Keluar Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus Surat Keluar Gagal: " + e.getMessage());
        }
    }
    
    public void hapusSuratKeluarPrepared(String id) {
        try {
            String sqlHapus = "DELETE FROM surat_keluar WHERE idk=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Surat Keluar Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus Surat Keluar Gagal: " + e.getMessage());
        }
    }
    
    public void simpanSKStatement(String id, String nosk, String tglsk, String nama, String ket, String file) {
        try {
            String checkPrimary = "SELECT * FROM sk WHERE ids='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "No. SK: " + result.getString("nosk") +
                             "\nTanggal SK: " + result.getString("tglsk") +
                             "\nNama: " + result.getString("nama") +
                             "\nKeterangan: " + result.getString("ket") +
                             "\nFile: " + result.getString("file");

                JOptionPane.showMessageDialog(null, "ID SK sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_nosk_sk = result.getString("nosk");
                this.var_tglsk_sk = result.getString("tglsk");
                this.var_nama_sk = result.getString("nama");
                this.var_ket_sk = result.getString("ket");
                this.var_file_sk = result.getString("file");
            }
            else {
                String sql = "INSERT INTO sk (ids, nosk, tglsk, nama, ket, file) VALUE ('" 
                        +id+ "', '" +nosk+ "', '" +tglsk+ "', '" +nama+ "', '" 
                        +ket+ "', '" +file+ "')";
                Statement perintah = koneksiDB.createStatement();
                perintah.execute(sql);
            
                JOptionPane.showMessageDialog(null, "SK Berhasil Disimpan!");
                this.validasi = false;
                this.var_nosk_sk = null;
                this.var_tglsk_sk = null;
                this.var_nama_sk = null;
                this.var_ket_sk = null;
                this.var_file_sk = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan SK Gagal: " + e.getMessage());
        }
    }
    
    public void simpanSKPrepared(String id, String nosk, String tglsk, String nama, String ket, String file) {
        try {
            String checkPrimary = "SELECT * FROM sk WHERE ids='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                String isi = "No. SK: " + result.getString("nosk") +
                             "\nTanggal SK: " + result.getString("tglsk") +
                             "\nNama: " + result.getString("nama") +
                             "\nKeterangan: " + result.getString("ket") +
                             "\nFile: " + result.getString("file");

                JOptionPane.showMessageDialog(null, "ID SK sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_nosk_sk = result.getString("nosk");
                this.var_tglsk_sk = result.getString("tglsk");
                this.var_nama_sk = result.getString("nama");
                this.var_ket_sk = result.getString("ket");
                this.var_file_sk = result.getString("file");
            }
            else {
                String sql = "INSERT INTO sk (ids, nosk, tglsk, nama, ket, file) VALUE (?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = koneksiDB.prepareStatement(sql);
                perintah.setString(1, id);
                perintah.setString(2, nosk);
                perintah.setString(3, tglsk);
                perintah.setString(4, nama);
                perintah.setString(5, ket);
                perintah.setString(6, file);
                perintah.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "SK Berhasil Disimpan!");
                this.validasi = false;
                this.var_nosk_sk = null;
                this.var_tglsk_sk = null;
                this.var_nama_sk = null;
                this.var_ket_sk = null;
                this.var_file_sk = null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Simpan SK Gagal: " + e.getMessage());
        }
    }

    public void ubahSKStatement(String id, String nosk, String tglsk, String nama, String ket, String file) {
        try {
            String sqlUbah = "UPDATE sk SET nosk='" +nosk+ 
                             "', tglsk='" +tglsk+ "', nama='" +nama+ 
                             "', ket='" +ket+ "', file='" +file+
                             "' WHERE ids='" +id+ "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "SK Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah SK Gagal: " + e.getMessage());
        }
    }
    
    public void ubahSKPrepared(String id, String nosk, String tglsk, String nama, String ket, String file) {
        try {
            String sqlUbah = "UPDATE sk SET nosk=?, tglsk=?, nama=?, ket=?, file=? WHERE ids=?";
            PreparedStatement ubah = koneksiDB.prepareStatement(sqlUbah);
            ubah.setString(1, nosk);
            ubah.setString(2, tglsk);
            ubah.setString(3, nama);
            ubah.setString(4, ket);
            ubah.setString(5, file);
            ubah.setString(6, id); 
            ubah.executeUpdate();
            JOptionPane.showMessageDialog(null, "SK Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah SK Gagal: " + e.getMessage());
        }
    }

    public void hapusSKStatement(String id) {
        try {
            String sqlHapus = "DELETE FROM sk WHERE ids='" +id+ "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "SK Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus SK Gagal: " + e.getMessage());
        }
    }
    
    public void hapusSKPrepared(String id) {
        try {
            String sqlHapus = "DELETE FROM sk WHERE ids=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "SK Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus SK Gagal: " + e.getMessage());
        }
    }
    
    public void simpanLaporanStatement(String id, String alamat, String tgl, String waktu, String lap, String lak, 
                              String jenis, String luas, String jumlah, String taksiran, String jarak, 
                              String perjalanan, String armada, String kekuatan, String pelapor, String file) {
        try {
            String checkPrimary = "SELECT * FROM laporan_kebakaran WHERE id='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                
                String isi = "Alamat: " + result.getString("alamat") +
                             "\nTanggal: " + result.getString("tgl") +
                             "\nWaktu: " + result.getString("waktu") +
                             "\nLap: " + result.getString("lap") +
                             "\nLak: " + result.getString("lak") +
                             "\nJenis: " + result.getString("jenis") +
                             "\nLuas: " + result.getString("luas") +
                             "\nJumlah: " + result.getString("jumlah") +
                             "\nTaksiran: " + result.getString("taksiran") +
                             "\nJarak: " + result.getString("jarak") +
                             "\nPerjalanan: " + result.getString("perjalanan") +
                             "\nArmada: " + result.getString("armada") +
                             "\nKekuatan: " + result.getString("kekuatan") +
                             "\nPelapor: " + result.getString("pelapor") +
                             "\nFile: " + result.getString("file");

                JOptionPane.showMessageDialog(null, "ID Laporan sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_alamat_lap = result.getString("alamat");
                this.var_tgl_lap = result.getString("tgl");
                this.var_waktu_lap = result.getString("waktu");
                this.var_lap_lap = result.getString("lap");
                this.var_lak_lap = result.getString("lak");
                this.var_jenis_lap = result.getString("jenis");
                this.var_luas_lap = result.getString("luas");
                this.var_jumlah_lap = result.getString("jumlah");
                this.var_taksiran_lap = result.getString("taksiran");
                this.var_jarak_lap = result.getString("jarak");
                this.var_perjalanan_lap = result.getString("perjalanan");
                this.var_armada_lap = result.getString("armada");
                this.var_kekuatan_lap = result.getString("kekuatan");
                this.var_pelapor_lap = result.getString("pelapor");
                this.var_file_lap = result.getString("file");
            }
            else {
                String sql = "INSERT INTO laporan_kebakaran (id, alamat, tgl, waktu, lap, lak, jenis, luas, jumlah, " +
                             "taksiran, jarak, perjalanan, armada, kekuatan, pelapor, file) VALUE ('" 
                             +id+ "', '" +alamat+ "', '" +tgl+ "', '" +waktu+ "', '" +lap+ "', '" +lak+ "', '"
                             +jenis+ "', '" +luas+ "', '" +jumlah+ "', '" +taksiran+ "', '" +jarak+ "', '"
                             +perjalanan+ "', '" +armada+ "', '" +kekuatan+ "', '" +pelapor+ "', '" +file+ "')";
                Statement perintah = koneksiDB.createStatement();
                perintah.execute(sql);
            
                JOptionPane.showMessageDialog(null, "Laporan Berhasil Disimpan!");
                this.validasi = false;
                this.var_alamat_lap = null;
            }
        } catch (SQLException e) {
            this.validasi = true; 
            JOptionPane.showMessageDialog(null, "Simpan Laporan Gagal: " + e.getMessage());
        }
    }
    
    public void simpanLaporanPrepared(String id, String alamat, String tgl, String waktu, String lap, String lak, 
                              String jenis, String luas, String jumlah, String taksiran, String jarak, 
                              String perjalanan, String armada, String kekuatan, String pelapor, String file) {
        try {
            String checkPrimary = "SELECT * FROM laporan_kebakaran WHERE id='" +id+ "'";
            Statement checkData = koneksiDB.createStatement();
            ResultSet result = checkData.executeQuery(checkPrimary);
            
            if (result.next()) {
                
                String isi = "Alamat: " + result.getString("alamat") +
                             "\nTanggal: " + result.getString("tgl") +
                             "\nWaktu: " + result.getString("waktu") +
                             "\nLap: " + result.getString("lap") +
                             "\nLak: " + result.getString("lak") +
                             "\nJenis: " + result.getString("jenis") +
                             "\nLuas: " + result.getString("luas") +
                             "\nJumlah: " + result.getString("jumlah") +
                             "\nTaksiran: " + result.getString("taksiran") +
                             "\nJarak: " + result.getString("jarak") +
                             "\nPerjalanan: " + result.getString("perjalanan") +
                             "\nArmada: " + result.getString("armada") +
                             "\nKekuatan: " + result.getString("kekuatan") +
                             "\nPelapor: " + result.getString("pelapor") +
                             "\nFile: " + result.getString("file");

                JOptionPane.showMessageDialog(null, "ID Laporan sudah terdaftar!\n\n" + isi);
                
                this.validasi = true;
                this.var_alamat_lap = result.getString("alamat");
                this.var_tgl_lap = result.getString("tgl");
                this.var_waktu_lap = result.getString("waktu");
                this.var_lap_lap = result.getString("lap");
                this.var_lak_lap = result.getString("lak");
                this.var_jenis_lap = result.getString("jenis");
                this.var_luas_lap = result.getString("luas");
                this.var_jumlah_lap = result.getString("jumlah");
                this.var_taksiran_lap = result.getString("taksiran");
                this.var_jarak_lap = result.getString("jarak");
                this.var_perjalanan_lap = result.getString("perjalanan");
                this.var_armada_lap = result.getString("armada");
                this.var_kekuatan_lap = result.getString("kekuatan");
                this.var_pelapor_lap = result.getString("pelapor");
                this.var_file_lap = result.getString("file");
            }
            else {
                String sql = "INSERT INTO laporan_kebakaran (id, alamat, tgl, waktu, lap, lak, jenis, luas, jumlah, " +
                             "taksiran, jarak, perjalanan, armada, kekuatan, pelapor, file) " +
                             "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = koneksiDB.prepareStatement(sql);
                perintah.setString(1, id);
                perintah.setString(2, alamat);
                perintah.setString(3, tgl);
                perintah.setString(4, waktu);
                perintah.setString(5, lap);
                perintah.setString(6, lak);
                perintah.setString(7, jenis); 
                perintah.setString(8, luas);
                perintah.setString(9, jumlah);
                perintah.setString(10, taksiran);
                perintah.setString(11, jarak);
                perintah.setString(12, perjalanan);
                perintah.setString(13, armada);
                perintah.setString(14, kekuatan); 
                perintah.setString(15, pelapor);
                perintah.setString(16, file);
                perintah.executeUpdate();
            
                JOptionPane.showMessageDialog(null, "Laporan Berhasil Disimpan!");
                this.validasi = false;
                this.var_alamat_lap = null;
            }
        } catch (SQLException e) {
            this.validasi = true; 
            JOptionPane.showMessageDialog(null, "Simpan Laporan Gagal: " + e.getMessage());
        }
    }

    public void ubahLaporanStatement(String id, String alamat, String tgl, String waktu, String lap, String lak, 
                            String jenis, String luas, String jumlah, String taksiran, String jarak, 
                            String perjalanan, String armada, String kekuatan, String pelapor, String file) {
        try {
            String sqlUbah = "UPDATE laporan_kebakaran SET alamat='" +alamat+ 
                             "', tgl='" +tgl+ "', waktu='" +waktu+ "', lap='" +lap+ 
                             "', lak='" +lak+ "', jenis='" +jenis+ "', luas='" +luas+ 
                             "', jumlah='" +jumlah+ "', taksiran='" +taksiran+ "', jarak='" +jarak+ 
                             "', perjalanan='" +perjalanan+ "', armada='" +armada+ 
                             "', kekuatan='" +kekuatan+ "', pelapor='" +pelapor+ "', file='" +file+ 
                             "' WHERE id='" +id+ "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Laporan Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah Laporan Gagal: " + e.getMessage());
        }
    }
    
    public void ubahLaporanPrepared(String id, String alamat, String tgl, String waktu, String lap, String lak, 
                            String jenis, String luas, String jumlah, String taksiran, String jarak, 
                            String perjalanan, String armada, String kekuatan, String pelapor, String file) {
        try {
            String sqlUbah = "UPDATE laporan_kebakaran SET alamat=?, tgl=?, waktu=?, lap=?, lak=?, jenis=?, luas=?, " +
                             "jumlah=?, taksiran=?, jarak=?, perjalanan=?, armada=?, kekuatan=?, pelapor=?, file=? " +
                             "WHERE id=?";
            PreparedStatement ubah = koneksiDB.prepareStatement(sqlUbah);
            ubah.setString(1, alamat);
            ubah.setString(2, tgl);
            ubah.setString(3, waktu);
            ubah.setString(4, lap);
            ubah.setString(5, lak);
            ubah.setString(6, jenis);
            ubah.setString(7, luas);
            ubah.setString(8, jumlah);
            ubah.setString(9, taksiran);
            ubah.setString(10, jarak);
            ubah.setString(11, perjalanan);
            ubah.setString(12, armada);
            ubah.setString(13, kekuatan);
            ubah.setString(14, pelapor);
            ubah.setString(15, file);
            ubah.setString(16, id); 
            ubah.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Laporan Berhasil Diubah!");
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "Ubah Laporan Gagal: " + e.getMessage());
        }
    }

    public void hapusLaporanStatement(String id) {
        try {
            String sqlHapus = "DELETE FROM laporan_kebakaran WHERE id='" +id+ "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Laporan Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus Laporan Gagal: " + e.getMessage());
        }
    }
    
    public void hapusLaporanPrepared(String id) {
        try {
            String sqlHapus = "DELETE FROM laporan_kebakaran WHERE id=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Laporan Berhasil Dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hapus Laporan Gagal: " + e.getMessage());
        }
    }
}