import java.sql.Timestamp;

import subasto6.Categoria;
import subasto6.Subasta;
import subasto6.Transaccion;
import subasto6.Usuario;

class BootStrap {

    def init = {
		
		long ahora = System.currentTimeMillis();
		
		Usuario jose = new Usuario("Jose", "Perez", "Josepe", "123456", "josepe@correog.com")
		jose.save()
		
		Usuario pepe = new Usuario("Pepe", "Rodriguez", "Pepe123", "123456", "pepito@correocaliente.com") 
		pepe.save()
		
		Usuario lopez = new Usuario("Jorge", "Lopez", "jorgelopez", "123456", "jorgelopez@correocaliente.com")
		lopez.save()
		
		Timestamp dentroDeUnaHora = new Timestamp (ahora + 1 * 60 * 60 * 1000); //Dentro de una hora
		Timestamp dentroDeMedioMinuto = new Timestamp (ahora + 30 * 1000); //Dentro de medio minuto
		
		Categoria calzado = new Categoria("Calzado")
		calzado.save()
		
		String descripcion = "Buenisimos zapatos de primera marca importados de Noruega en todos los talles. Colores en rojo, marron, negro, azul, blancos y grises. Consultar por otros modelos."
		
		Subasta s1 = new Subasta("Zapatos negros nuevos", descripcion , 200, dentroDeMedioMinuto, jose, calzado)
		s1.save();
		
		s1.ofertar(10, lopez)
		
		//s1.save();
		
		
		
		
	}
    def destroy = {
    }
}
