package Objetos;

import java.sql.Date;

public class Arbol {
	
	//Atributos
		private int id;
		private String nombreComun;
		private String nombreCientifico;
		private Habitat habitat;
		private int altura;
		private String origen;
		private Date encontrado;
		private boolean singular;
		
	
	//Getter&Setter
		
		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getNombreComun() {
			return nombreComun;
		}


		public void setNombreComun(String nombreComun) {
			this.nombreComun = nombreComun;
		}


		public String getNombreCientifico() {
			return nombreCientifico;
		}


		public void setNombreCientifico(String nombreCientifico) {
			this.nombreCientifico = nombreCientifico;
		}


		public Habitat getHabitat() {
			return habitat;
		}


		public void setHabitat(Habitat habitat) {
			this.habitat = habitat;
		}


		public int getAltura() {
			return altura;
		}


		public void setAltura(int altura) {
			this.altura = altura;
		}


		public String getOrigen() {
			return origen;
		}


		public void setOrigen(String origen) {
			this.origen = origen;
		}


		public Date getEncontrado() {
			return encontrado;
		}


		public void setEncontrado(Date encontrado) {
			this.encontrado = encontrado;
		}


		public boolean isSingular() {
			return singular;
		}


		public void setSingular(boolean singular) {
			this.singular = singular;
		}


		@Override
		public String toString() {
			return id + ", nombreComun=" + nombreComun + ", nombreCientifico=" + nombreCientifico
					+ ", habitat=" + habitat.getNombre() + ", altura=" + altura + ", origen=" + origen + ", encontrado=" + encontrado
					+ ", singular=" + singular;
		}	
	
	
}
