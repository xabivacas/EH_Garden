package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorArboles {

	private static String HOST="localhost";
	private static String BBDD="eh_garden";
	private static String USER="root";
	private static String PASSWORD="";
	private static Scanner scan = new Scanner (System.in);
	
	public static void main(String[] args) {
		run();
	}

	private static void run() {
		
		
			try {
				//Cargar libreria
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//Crear conexion
				Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
				
				//Crear Statement
				Statement st = conexion.createStatement();
				
				arboles(st);
			} catch (ClassNotFoundException e) {
				System.out.println("Error de importacion de la libreria");
				e.printStackTrace();
			}
		
			catch (SQLException e) {
				System.out.println("Error al conectarse a la base de datos");
				e.printStackTrace();
			}
			
	}
	
	private static ArrayList<Arbol> arboles(Statement st){
		ArrayList<Arbol> arboles = new ArrayList<>();
		
		try {
			//Ejecutar Query
			ResultSet rs = st.executeQuery("SELECT * FROM arboles");
			
			//Recorrer
			while(rs.next()){
				Arbol a = new Arbol();
				a.setId(rs.getInt("id"));
				a.setNombreComun(rs.getString("nombre_comun"));
				a.setNombreCientifico(rs.getString("nombre_cientifico"));
				a.setHabitat(rs.getString("habitat"));
				a.setAltura(rs.getInt("altura"));
				a.setOrigen(rs.getString("origen"));
				
				arboles.add(a);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al cargar la tabla");
			e.printStackTrace();
		}
		return arboles;
		
	}
	

}
