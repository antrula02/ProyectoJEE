package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Reserva;

public class DaoReserva {

    public List<String> obtenerFechasReservadas() {
        List<String> fechas = new ArrayList<>();

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT FECHA_RES FROM reservas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fechas.add(rs.getString("FECHA_RES"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fechas;
    }

    /**
     * @param r
     * @return Se comprueba que el método inserta correctamente una reserva en la base de datos.

Resultado esperado: que la reserva se guarde correctamente.
     */
    public boolean insertarReserva(Reserva r) {
        boolean ok = false;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "INSERT INTO reservas (NOMBRE_RES, TELF, FECHA_RES, EMAIL_RES, NUM_PLAYER, MODE, NOTAS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, r.getNombreRes());
            ps.setString(2, r.getTelf());
            ps.setString(3, r.getFechaRes());
            ps.setString(4, r.getEmailRes());
            ps.setInt(5, r.getNumPlayer());
            ps.setInt(6, r.getMode());
            ps.setString(7, r.getNotas());

            ps.executeUpdate();
            ok = true;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ok;
    }

    /**
     * @return Se comprueba que el método devuelve correctamente la lista de reservas.

Resultado esperado: que retorne todas las reservas guardadas en la base de datos.
     */
    public List<Reserva> obtenerReservas() {
        List<Reserva> lista = new ArrayList<>();

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT * FROM reservas ORDER BY ID_RESERVAS DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();

                r.setIdReserva(rs.getInt("ID_RESERVAS"));
                r.setNombreRes(rs.getString("NOMBRE_RES"));
                r.setTelf(rs.getString("TELF"));
                r.setFechaRes(rs.getString("FECHA_RES"));
                r.setEmailRes(rs.getString("EMAIL_RES"));
                r.setNumPlayer(rs.getInt("NUM_PLAYER"));
                r.setMode(rs.getInt("MODE"));
                r.setNotas(rs.getString("NOTAS"));

                lista.add(r);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Reserva> obtenerReservasPorEmail(String email) {
        List<Reserva> lista = new ArrayList<>();

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT * FROM reservas WHERE EMAIL_RES = ? ORDER BY ID_RESERVAS DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();

                r.setIdReserva(rs.getInt("ID_RESERVAS"));
                r.setNombreRes(rs.getString("NOMBRE_RES"));
                r.setTelf(rs.getString("TELF"));
                r.setFechaRes(rs.getString("FECHA_RES"));
                r.setEmailRes(rs.getString("EMAIL_RES"));
                r.setNumPlayer(rs.getInt("NUM_PLAYER"));
                r.setMode(rs.getInt("MODE"));
                r.setNotas(rs.getString("NOTAS"));

                lista.add(r);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * @param idReserva
     * @return Se comprueba que el método elimina correctamente una reserva de la base de datos.

Resultado esperado: que la reserva deje de existir en la base de datos.
     */
    public boolean eliminarReserva(int idReserva) {
        boolean ok = false;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "DELETE FROM reservas WHERE ID_RESERVAS = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idReserva);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ok = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ok;
    }
}