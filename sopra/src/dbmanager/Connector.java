package dbmanager;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Clase wrapper de una conexion a base de datos que controla reconexiones y cierres cuando conviene.
 * 
 * @author daconcep
 *
 */
public class Connector implements AutoCloseable{

	private Connection con;
	private String url;
	private String usu;
	private String pas;
	private String bas;
	
	/**
	 * @param url
	 * @param usuario
	 * @param pass
	 * @param base
	 * 
	 * Creamos un wrapper de la conexion y comprobamos que la conexion funciona.
	 */
	public Connector(String url,String usuario,String pass,String base) {
		this.url=url;
		usu=usuario;
		pas=pass;
		bas=base;
		
		//Comprobamos la conexion
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), "The jdbc driver cannot be found.\n" +"It must be downloaded and linked.");
		}
		connect();
		close();
	}
	
	/**
	 * Abrimos la conexion
	 */
	public void connect() {
		try {
			con = DriverManager.getConnection ("jdbc:mysql://"+url+"/"+bas,usu,pas);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e.getMessage());
			if (!isNull()){
				close();
				con=null;
			}
		}
	}
	
	/**
	 * Cerramos la conexion
	 */
	public void close() {
		try {
			if (!isNull()){
				if(!con.isClosed()) {
					con.close();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e.getMessage());
		}
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return con;
	}

	public String getBase() {
		// TODO Auto-generated method stub
		return bas;
	}

	public String getUsuario() {
		// TODO Auto-generated method stub
		return usu;
	}
	
	public boolean isNull(){
		return con==null;
	}

}
