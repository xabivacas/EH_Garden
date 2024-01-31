package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Clases.*;

public class GestorArboles {

	private static final String HOST="localhost";
	private static final String BBDD="eh_garden2";
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
					Arbol a;
					int id=0;
					int select =0;
					
				//Menu
					do {
					 menu();
					 select=Integer.parseInt(scan.nextLine());
					 
					 switch (select) {
					 
						 case CREATE:
						 	a = new Arbol();
						 	System.out.println("Inserte el nombre comun");
							a.setNombreComun(scan.nextLine());
							
							System.out.println("Inserte el nombre cientifico");
							a.setNombreCientifico(scan.nextLine());
							
							System.out.println("Inserte el habitat natural");
							a.setHabitat(scan.nextLine());
							
							System.out.println("Inserte la altura");
							a.setAltura(Integer.parseInt(scan.nextLine()));
							
							System.out.println("Inserte el origen");
							a.setOrigen(scan.nextLine());
							
							insert(a);
							break;
							
						 case READ:
							 visualizar();
							 break;
							 
						 case UPDATE:
							 a=new Arbol();
							 
							 System.out.println("Inserte el id del arbol que quiere modificar");
							 id=Integer.parseInt(scan.nextLine());
							 a = arbol(id);
							 System.out.println(a);
							 System.out.println("---------------------");
							 
							 System.out.println("Inserte el nuevo nombre comun");
							 a.setNombreComun(scan.nextLine());
							
							 System.out.println("Inserte el nuevo nombre cientifico");
							 a.setNombreCientifico(scan.nextLine());
							
							 System.out.println("Inserte el nuevo habitat natural");
							 a.setHabitat(scan.nextLine());
							
							 System.out.println("Inserte la nueva altura");
							 a.setAltura(Integer.parseInt(scan.nextLine()));
							
							 System.out.println("Inserte el nuevo origen");
							 a.setOrigen(scan.nextLine());
							 
							 update(a);
							 System.out.println("Modificacion realizada");
							 break;
							 
						 case DELETE:
							 
							 System.out.println("Inserte el id del arbol que quiere borrar");
							 id=Integer.parseInt(scan.nextLine());
							 
							 
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
				ResultSet rs = st.executeQuery("SELECT * FROM arboles INNER JOIN habitat  ON arboles.habitat_id=habitat.id");
			
			//Recorrer y guardar en el arrayList
				while(rs.next()){
					Arbol a = new Arbol();
					Habitat h = new Habitat();
					
					a.setId(rs.getInt("id"));
					a.setNombreComun(rs.getString("nombre_comun"));
					a.setNombreCientifico(rs.getString("nombre_cientifico"));
					
					h.setId(rs.getInt("habitat_id"));
					h.setNombre(rs.getString("nombre"));
					a.setHabitat(h);
					
					a.setAltura(rs.getInt("altura"));
					a.setOrigen(rs.getString("origen"));
					a.setEncontrado(rs.getDate("encontrado"));
					a.setSingular(rs.getBoolean("singular"));
					
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
	
	private static void insert(Arbol a) {
			
		try {
			//Variables
				String sql="INSERT INTO arboles (nombre_comun,nombre_cientifico,habitat,altura,origen) VALUES ( ?, ?, ?, ?, ?)";
				
				Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
				PreparedStatement pst = conexion.prepareStatement(sql);

			//Ejecutar Query
				pst.setString(1, a.getNombreComun());
				pst.setString(2, a.getNombreCientifico());
				pst.setString(3,a.getHabitat() );
				pst.setInt(4, a.getAltura());
				pst.setString(5, a.getOrigen());
			
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
		Habitat h = new Habitat();
			try {
				//Variables
					Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
					
					String sql= "SELECT * FROM arboles a INNER JOIN habitat h ON a.habitat_id = h.id WHERE a.id= ?";
					
					PreparedStatement pst = conexion.prepareStatement(sql);
				//buscar id
					pst.setInt(1, id);
					ResultSet rs = pst.executeQuery();
					rs.next();
					
				//Darle atributos al arbol
					
					
					a.setId(rs.getInt("id"));
					a.setNombreComun(rs.getString("nombre_comun"));
					a.setNombreCientifico(rs.getString("nombre_cientifico"));
					
					h.setId(rs.getInt("habitat_id"));
					h.setNombre(rs.getString("nombre"));
					a.setHabitat(h);
					
					a.setAltura(rs.getInt("altura"));
					a.setOrigen(rs.getString("origen"));
					a.setEncontrado(rs.getDate("encontrado"));
					a.setSingular(rs.getBoolean("singular"));
					
						
			} catch (SQLException e) {
				System.out.println("Id no encontrado");
				e.printStackTrace();
			}

			return a;
	}

	private static void update(Arbol a) {
		
		try {
			//Variables
				String sqlUpdate="UPDATE arboles SET nombre_comun= ? ,nombre_cientifico= ? ,habitat= ? ,altura= ? , origen= ? WHERE id= ?";
	
				Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BBDD,USER,PASSWORD);
				PreparedStatement pst = conexion.prepareStatement(sqlUpdate);
	
					//Modificar
						pst.setString(1, a.getNombreComun());
						pst.setString(2, a.getNombreCientifico());
						pst.setString(3, a.getHabitat());
						pst.setInt(4, a.getAltura());
						pst.setString(5, a.getOrigen());
						pst.setInt(6, a.getId());
						
						pst.execute();
						
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
