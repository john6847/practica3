package services;

import com.modelo.Articulo;
import com.modelo.Comentario;
import com.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by john on 05/06/17.
 */
public class ComentarioServices {
        public List<Comentario> comentarios=new ArrayList<>();
        Connection con=null;


    public List<Comentario> listaEstudiantes() {
        List<Comentario> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {

            String query = "select * from comentario;";
            String queryAutor="select * from Usuario where Usuario.username=?;";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            UsuarioServices usuarioServices = new UsuarioServices();
            PreparedStatement prepareStatement = con.prepareStatement(query);
            PreparedStatement preparedStatement2=con.prepareStatement(queryAutor);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Usuario usuario = new Usuario();

                Comentario com = new Comentario();
                com.setId( rs.getLong("ID"));
                com.setComentario(rs.getString("comentario"));
                com.setArticulo(rs.getLong("articulo"));
                com.setAutor(usuarioServices.getUsuario(rs.getString("username")));//Esperando la funcion de Pierre
                lista.add(com);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ComentarioServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ComentarioServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;
    }

}