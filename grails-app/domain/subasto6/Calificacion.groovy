package subasto6

class Calificacion {

	String comentario;
	int puntaje;
	Usuario usuarioCalificador;
	Usuario usuarioCalificado;
	Transaccion transaccion;
	
    static constraints = {
    }
	
	def static Boolean esValidoElPuntaje(int puntaje) {
		return (puntaje >= -5 && puntaje <= 5);
	}

}
