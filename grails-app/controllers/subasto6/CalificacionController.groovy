package subasto6



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CalificacionController {
	static scaffold = true
}
