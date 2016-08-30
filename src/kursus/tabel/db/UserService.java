/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursus.tabel.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import kursus.tabel.MainFrameWithPage;

/**
 *
 * @author linuxluv
 */
public class UserService {

    PreparedStatement ps;

    /* menghitung jumlah record tabel user */
    public int count(String keyword) {
        int jumlahBaris = 0;
        Statement s = null;
        ResultSet r = null;
        try {
            Connection c = KoneksiDb.getKoneksi();
            s = c.createStatement();
            String sql = "select count(id_user) as jml from user where username like '%"
                    + keyword + "%' or id_user like '%" + keyword + "%'";
            r = s.executeQuery(sql);
            while (r.next()) {
                jumlahBaris = r.getInt("jml");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                s.close();
                r.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return jumlahBaris;
        }
    }

    /* mengambil semua record user dari tabel */
    public void getAllDataUser(DefaultTableModel modelTbl, int halaman, int banyakBaris) {
        ResultSet r = null;
        try {
            Connection c = KoneksiDb.getKoneksi();
            String sql = "select * from user limit ?,?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, banyakBaris * (halaman - 1));
            ps.setInt(2, banyakBaris);
            r = ps.executeQuery();
            /* clear tabel, hapus semua baris sebelum menampilkan hanya record terpilih */
            modelTbl.setRowCount(0);
            while (r.next()) {
                Object[] o = new Object[5];
                o[0] = r.getString("id_user");
                o[1] = r.getString("username");
                modelTbl.addRow(o);
            }
            r.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(MainFrameWithPage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                r.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /* searching data user berdasarkan username */
    public void searchDataUser(String keyword, DefaultTableModel modelTbl, int halaman, int banyakBaris) {
        ResultSet r = null;
        try {
            Connection c = KoneksiDb.getKoneksi();
            String sql = "select * from user where username like '%"+keyword+"%' or id_user like ? limit ?,?";
            ps = c.prepareStatement(sql);
//            ps.setString(1, keyword);
            ps.setString(1, keyword);
            ps.setInt(2, banyakBaris * (halaman - 1));
            ps.setInt(3, banyakBaris);
            r = ps.executeQuery();
            /* clear tabel, hapus semua baris sebelum menampilkan hanya record terpilih */
            modelTbl.setRowCount(0);
            while (r.next()) {
                Object[] o = new Object[5];
                o[0] = r.getString("id_user");
                o[1] = r.getString("username");
                modelTbl.addRow(o);
            }
            r.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(MainFrameWithPage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                r.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
