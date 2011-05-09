import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.ho.yaml.Yaml;

public class Automaton {

	String initial;
	String finals;
	String finalsAndReset;
	Map<String, Map<String, String>> delta;
	Map<String, Object> automaton;

	@SuppressWarnings("unchecked")
	public Automaton() throws Exception{

		// Setea el autómata
		automaton = Yaml.loadType(new File(Configs.automaton), HashMap.class);

		// Setea el estado inicial
		initial = (String) automaton.get("initial");

		// Setea los estados finalesss
		finals = (String) automaton.get("finals");

		// Setea los estados finales que vuelven un caracter atrás
		finalsAndReset = (String) automaton.get("finalsAndReset");
		
		// Setea la función delta
		delta = Utils.getDelta(automaton);
	}

	public AutomataResponse process(String input, int since) throws Exception {

		AutomataResponse response = new AutomataResponse();

		// Setea el estado inicial cada vez que se intenta procesar
		String q = initial;

		int i = since;
		String lastq = "";

		for (; i < input.length()-1; i++) {

			// Calcula el delta
			String c = CharUtils.toString(input.charAt(i));
			lastq = q;
			q = delta(q,c);

			/*  
			 * Mientras permanece en el estado inicial se 
			 * descartan los caracteres leidos
			 */
			if (initial.equals(q)){
				since = i;
			}

			/* 
			 * Todas las transiciones no definidas en la configuración
			 * del autómata, llevan a un estado de error genérico
			 */
			if(q == null){
				response.set(since, i+1, input.substring(since, i+1), lastq, true, "Error desde # hasta $ - error lexicografico");
				return response;
			}

			/*
			 * Llegó a estado final, debe retroceder y 
			 * recuperar el último caracter leído
			 */
			if(isFinalAndReset(q)){
				i= i-1;
				break;
			}
			
			// Llegó a estado final
			if (isFinal(q)){
				break;
			}
		}

		// Fin de la entrada
		if ( i == input.length()-1){
			return null;
		}

		// Llegó a un estado de error o que no es final
		if( isError(q) || (!isFinal(q) && !isFinalAndReset(q))){
			response.set(since, i+1, input.substring(since, i+1), lastq, true, "Error desde # hasta $ - "+ getErrorMsg(q));
			return response;
		}

		response.set(since, i+1, input.substring(since, i+1), lastq, false, null);
		return response;
	}

	@SuppressWarnings("unchecked")
	private boolean isError(String q) {
		return ((Map<String, String>)automaton.get("errors")).get(q) != null;
	}

	@SuppressWarnings("unchecked")
	private String getErrorMsg(String q) {
		String msg = ((Map<String, String>)automaton.get("errors")).get(q);
		return (msg == null)?((Map<String, String>)automaton.get("default")).get(q):msg;
	}

	private boolean isFinalAndReset(String q) {
		return StringUtils.contains(finalsAndReset, q);
	}

	private boolean isFinal(String q) {
		return StringUtils.contains(finals, q);
	}

	private String delta(String q, String c) {
		// Activar para ver cómo va tomando la función delta: 
		// System.out.println(q+c);
		return delta.get(q).get(c);
	}
}