package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
				
			} catch (ClassNotFoundException e) {
				System.out.println("Error de importacion de la libreria");
				e.printStackTrace();
			}
		
			catch (SQLException e) {
				System.out.println("Error al conectarse a la base de datos");
				e.printStackTrace();
			}
			
	}
	

}
