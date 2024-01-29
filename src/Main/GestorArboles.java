package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorArboles {

	private static final String HOST="localhost";
	private static final String BBDD="eh_garden";
	private static final String USER="root";
	private static final String PASSWORD="";
	private static final Scanner scan = new Scanner (System.in);
	
	private static final int SALIR = 0;
	private static final int CREATE = 1;
	private static final int READ = 2;
	private static final int UPDATE = 3;
	private static final int DELETE = 4;
	
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
				
				
				//Variables
					ArrayList<Arbol> arboles = new ArrayList<>();
				
				//Menu
					menu();
					
			} catch (ClassNotFoundException e) {
				System.out.println("Error de importacion de la libreria");
				e.printStackTrace();
			}
		
			catch (SQLException e) {
				System.out.println("Error al conectarse a la base de datos");
				e.printStackTrace();
			}
			
	}
	
	private static void arboles(Statement st,	ArrayList<Arbol> arboles){
		
		try {
			//Ejecutar Query
				ResultSet rs = st.executeQuery("SELECT * FROM arboles");
			
			//Recorrer y guardar en el arrayList
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
	}
	
	private static void menu() {
		System.out.println("--Menu--");
		System.out.println(CREATE+"-Create");
		System.out.println(READ+"-Read");
		System.out.println(UPDATE+"-Update");
		System.out.println(DELETE+"-Delete");
		System.out.println(SALIR+"-Salir");
	}
}
