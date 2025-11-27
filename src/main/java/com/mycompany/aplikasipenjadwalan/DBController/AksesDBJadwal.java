package com.mycompany.aplikasipenjadwalan.DBController;
   
import com.mycompany.aplikasipenjadwalan.Jadwal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AksesDBJadwal {
   private Connection connection;

   public AksesDBJadwal(Connection connection) {
      this.connection = connection;
   }

   public List<Jadwal> getJadwalByUserID(String userId) {
      List<Jadwal> jadwals = new ArrayList<>();
      String query = "SELECT * FROM jadwal WHERE userId = ?";

      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, userId);
         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            Jadwal jadwal = new Jadwal(rs.getInt("id"), rs.getInt("userId"), rs.getString("kodeMatkul"),
                  rs.getString("namaDosen"), rs.getString("ruang"), rs.getDate("tanggal").toLocalDate(),
                  rs.getTime("jamMulai").toLocalTime(), rs.getTime("jamSelesai").toLocalTime(),
                  rs.getString("deskripsi"));
            jadwals.add(jadwal);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return jadwals;
   }

   public void tambahJadwal(Jadwal jadwal) {
      String query = "INSERT INTO jadwal (userId, kodeMatkul, ruang, tanggal, idDosen, jamMulai, jamSelesai, deskripsi) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setInt(1, jadwal.getUserId());
         pst.setString(2, jadwal.getKodeMatkul());
         pst.setString(3, jadwal.getRuang());
         pst.setDate(4, Date.valueOf(jadwal.getTanggal()));
         pst.setString(6, jadwal.getIdDosen());
         pst.setTime(7, Time.valueOf(jadwal.getJamMulai()));
         pst.setTime(8, Time.valueOf(jadwal.getJamSelesai()));
         pst.setString(9, jadwal.getDeskripsi());
         pst.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
