package subasto6

import java.sql.Time;
import java.sql.Timestamp
import java.util.Formatter.DateTime;

class Oferta {

	double valor;
	Time fechaYHora; 
	Usuario ofertante;
	
	def Oferta(double valorOfertado, Usuario quienOferta) {
		fechaYHora = new Time( new Date().getTime() ); 
		ofertante = quienOferta;
		valor = valorOfertado;
	}
	
    static constraints = {
    }
}
