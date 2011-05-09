import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.ho.yaml.Yaml;

public class LexicalAnalyzer {

	int pos;
	Map<String, String> tokensConfig;

	@SuppressWarnings("unchecked")
	public LexicalAnalyzer() throws Exception {
		this.pos = 0;
		this.tokensConfig = Yaml.loadType(new File(Configs.tokensConfig), HashMap.class);
	}

	public ArrayList<String> analize(String input) throws Exception {

		ArrayList<String> output = new ArrayList<String>();
		Automaton automaton = new Automaton();

		while (pos < input.length() - 1) {

			// Invoca al automata
			AutomataResponse response = automaton.process(input, pos);

			// Fin de la entrada
			if (response == null) {
				break;
			}

			// Checkeo de errores
			if (response.isError()) {

				output.add("(99," + response.getErrorDetail() + ") ");

			} else {

				// Obtiene lexema
				String lexeme = StringUtils.trimToEmpty(response.getLexeme());

				/*
				 * Control solo para Java, si se encuentra un identificador con
				 * longitud > 16, se descartan caracteres y se imprime warning
				 * en la salida
				 */
				if (("JAVA".equals(Configs.lang))
						&& ("q1".equals(response.lastStatus))
						&& (lexeme.length() > 16)) {

					lexeme = StringUtils.substring(lexeme, 0, 16);
					output.add("warning: longitud mayor a 16 caracteres");
				}

				// Obtiene token
				String token = getToken(lexeme);

				// Actualiza la salida
				output.add("(" + token + "," + lexeme + ")");
			}

			// Setea la posición a partir de la cual va a tiene que seguir
			// leyendo
			pos = response.getTo();
		}
		return output;
	}

	/*
	 * Devuelve el token correspondiente al lexema. En caso de ser un
	 * identificador, el token es 9.
	 */
	private String getToken(String lexeme) {
		String result = tokensConfig.get(lexeme);
		return (result == null) ? "9" : result;
	}
}