package modelo;

public class mascotas {
	
		
	public class Mascota {

	    // Atributos
	    String nombre;
	    int edad;
	    String tipo;

	    // Constructor
	    public Mascota(String nombre, int edad, String tipo) {
	        this.nombre = nombre;
	        this.edad = edad;
	        this.tipo = tipo;
	    }

	    public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public int getEdad() {
			return edad;
		}

		public void setEdad(int edad) {
			this.edad = edad;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		// Método para mostrar información
	    public void mostrarInfo() {
	        System.out.println("Nombre: " + nombre);
	        System.out.println("Edad: " + edad);
	        System.out.println("Tipo: " + tipo);
	    }

		@Override
		public String toString() {
			return "Mascota [nombre=" + nombre + ", edad=" + edad + ", tipo=" + tipo + "]";
		}

		
		}
	}


