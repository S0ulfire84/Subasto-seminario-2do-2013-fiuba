package subasto6

import java.sql.Timestamp

class Oferta {

	double valor;
	Timestamp fechaYHora; 
	Usuario ofertante;
	
	//static belongsTo = [subasta:Subasta]
	
	def Oferta(double valorOfertado, Usuario quienOferta) {
		fechaYHora = new Timestamp( new Date().getTime() ); 
		ofertante = quienOferta;
		valor = valorOfertado;
	}
	
    static constraints = {
    }
}
