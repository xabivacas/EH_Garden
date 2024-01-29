package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
					int select =0;
					
				//Menu
					do {
					 menu();
					 select=Integer.parseInt(scan.nextLine());
					 
					 switch (select) {
					 
						 case CREATE:
							insert(st,conexion);
							break;
							
						 case READ:
							 
							 break;
						 case UPDATE:
							 break;
						 case DELETE:
							 break;
						 case SALIR:
							 break;
						 default:
							 break;
					 }
					}while(select!=0);
					
					
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
	
	private static void insert(Statement st,Connection conexion) {
		//Variables
			String comun;
			String cientifico;
			String habitat;
			int altura;
			String origen;
			
			String sql="INSERT INTO arboles (nombre_comun,nombre_cientifico,habitat,altura,origen) VALUES ( ?, ?, ?, ?, ?)";
			
		try {
			PreparedStatement pst = conexion.prepareStatement(sql);
			
			//Pedir datos
				System.out.println("Inserte el nombre comun");
				comun=scan.nextLine();
				
				System.out.println("Inserte el nombre cientifico");
				cientifico=scan.nextLine();
				
				System.out.println("Inserte el habitat natural");
				habitat=scan.nextLine();
				
				System.out.println("Inserte la altura");
				altura=Integer.parseInt(scan.nextLine());
				
				System.out.println("Inserte el origen");
				origen=scan.nextLine();
				
			//Ejecutar Query
				pst.setString(1, comun);
				pst.setString(2, cientifico);
				pst.setString(3,habitat );
				pst.setInt(4, altura);
				pst.setString(5, origen);
			
				pst.execute();
				
			System.out.println("Insert completado");
		} catch (SQLException e) {
			System.out.println("Error en la Query");
			e.printStackTrace();
		}
	}
	
	private static void visualizar() {
		//Variables
			int select=0;
			
		//Preguntar uno o todos
			System.out.println("1-Visualizar uno arbol");
			System.out.println("2-Visualizar todos");
			select=Integer.parseInt(scan.nextLine());
			
		if(select==1) {
			visualizarUno();
		}else if(select==2) {
			visualizarTodo();
		}else {
			System.out.println("Opcion no valida");
		}
	}
	private static void visualizarUno() {
		//Variables
			
			try {
				//Variables
					Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
					String sql= "SELECT * FROM arboles WHERE id= ?";
					PreparedStatement pst = conexion.prepareStatement(sql);
					int id = 0;
					//pedir id
					System.out.println("Inserte el id");
					id=Integer.parseInt(scan.nextLine());
				/*
				 * buscar id
				 * set en arbol
				 * pintar en pantalla
				 */
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	}
	
	private static void visualizarTodo() {
		//variablees
			/*
			 * rellenar array
			 * foreach
			 * pintar en pantalla
			 */
	}
}
