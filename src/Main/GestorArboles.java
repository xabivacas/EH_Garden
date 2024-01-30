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
							 visualizar();
							 break;
							 
						 case UPDATE:
							 modificar();
							 break;
							 
						 case DELETE:
							 
							 System.out.println("Inserte el id del arbol que quiere modificar");
							 int id=Integer.parseInt(scan.nextLine());
							 
							 delete(id);
							 break;
							 
						 case SALIR:
							 System.out.println("Agur");
							 break;
							 
						 default:
							 System.out.println("Opcion no valida");
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
	
	private static  ArrayList<Arbol> arboles(){
		//Variable ArrayList
			ArrayList<Arbol> arboles = new ArrayList<>();
			
		try {
			//Variables
				Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
				Statement st = conexion.createStatement();
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
		
		return arboles;
	}
	
	private static void menu() {
		System.out.println("\n--Menu--");
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
			ArrayList<Arbol> arboles = new ArrayList<>();
			int select=0;
			
		//Preguntar uno o todos
			System.out.println("1-Visualizar uno arbol");
			System.out.println("2-Visualizar todos");
			select=Integer.parseInt(scan.nextLine());
			
		if(select==1) {
			//pedir id
				System.out.println("Inserte el id");
				int id=Integer.parseInt(scan.nextLine());
				Arbol a =arbol(id);
				
			//Pintar arbol
				System.out.println(a);
				
		}else if(select==2) {
			arboles=arboles();
			
			for(Arbol a:arboles) {
				System.out.println(a);
			}
			
		}else {
			System.out.println("Opcion no valida");
		}
	}
	
	private static Arbol arbol(int id) {
		//Variable
			Arbol a = new Arbol();
		
			try {
				//Variables
					Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
					
					String sql= "SELECT * FROM arboles WHERE id= ?";
					
					PreparedStatement pst = conexion.prepareStatement(sql);
				//buscar id
					pst.setInt(1, id);
					ResultSet rs = pst.executeQuery();
					rs.next();
					
				//Darle atributos al arbol
					a.setId(rs.getInt("id"));
					a.setNombreComun(rs.getString("nombre_comun"));
					a.setNombreCientifico(rs.getString("nombre_cientifico"));
					a.setHabitat(rs.getString("habitat"));
					a.setAltura(rs.getInt("altura"));
					a.setOrigen(rs.getString("origen"));
						
			} catch (SQLException e) {
				System.out.println("Id no encontrado");
				e.printStackTrace();
			}

			return a;
	}
	
	private static void visualizarTodo() {
			 
			try {
				//variablees
					ArrayList<Arbol> arboles = new ArrayList<>();
					String sql= "SELECT * FROM arboles";
					
					Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
					PreparedStatement pst = conexion.prepareStatement(sql);
					ResultSet rs = pst.executeQuery();
				
				System.out.println("Cargando los arboles...\n");
				
				while(rs.next()) {
					Arbol a = new Arbol();
					
					a.setId(rs.getInt("id"));
					a.setNombreComun(rs.getString("nombre_comun"));
					a.setNombreCientifico(rs.getString("nombre_cientifico"));
					a.setHabitat(rs.getString("habitat"));
					a.setAltura(rs.getInt("altura"));
					a.setOrigen(rs.getString("origen"));
					
					arboles.add(a);
				}
				
				for(Arbol a:arboles) {
					System.out.println(a);
				}
				
			} catch (SQLException e) {
				System.out.println("Error al cargar los arboles");
				e.printStackTrace();
			}
	}
	
	private static void modificar() {
		
		try {
			//Variables
				String sqlRead= "SELECT * FROM arboles WHERE id=?";
				String sqlUpdate="UPDATE arboles SET nombre_comun= ? ,nombre_cientifico= ? ,habitat= ? ,altura= ? , origen= ? WHERE id= ?";
	
				Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
				PreparedStatement pst = conexion.prepareStatement(sqlRead);
				
			//Pedir id para buscar
				System.out.println("Inserte el id del arbol que quiere modificar");
				int id=Integer.parseInt(scan.nextLine());
				
				pst.setInt(1, id);
				ResultSet rs= pst.executeQuery();
			
			//Si encuentra Id ejecutar
				if(rs.next()) {
					//Cargar datos del arbol y pintar
						Arbol a = new Arbol();
						
						a.setId(rs.getInt("id"));
						a.setNombreComun(rs.getString("nombre_comun"));
						a.setNombreCientifico(rs.getString("nombre_cientifico"));
						a.setHabitat(rs.getString("habitat"));
						a.setAltura(rs.getInt("altura"));
						a.setOrigen(rs.getString("origen"));
						
						System.out.println("\n"+a);
					System.out.println("-----------------------------------------");
					
					//Pedir datos para modificar
						pst = conexion.prepareStatement(sqlUpdate);
						
						System.out.println("Inserte el nuevo nombre comun");
						String nombreComun=scan.nextLine();
						
						System.out.println("Inserte el nuevo nombre cientifio");
						String nombreCientifico=scan.nextLine();
						
						System.out.println("Inserte el nuevo habitat");
						String habitat=scan.nextLine();
						
						System.out.println("Inserte la nueva altura");
						int altura = Integer.parseInt(scan.nextLine());
						
						System.out.println("Inserte el nuevo origen");
						String origen=scan.nextLine();
					
					//Modificar
						pst.setString(1, nombreComun);
						pst.setString(2, nombreCientifico);
						pst.setString(3, habitat);
						pst.setInt(4, altura);
						pst.setString(5, origen);
						pst.setInt(6, id);
						
						pst.execute();
						
					System.out.println("Modificacion realizada");
						
				}else {
					System.out.println("Id no encontrado");
				}
		} catch (SQLException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}
	
	private static void delete(int id) {
		try {
			//Variables
				String sql = "DELETE FROM arboles WHERE id = ?";
				
				Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
				PreparedStatement pst = conexion.prepareStatement(sql);
			
				pst.setInt(1, id);
				pst.execute();
			
		} catch (SQLException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	
}
