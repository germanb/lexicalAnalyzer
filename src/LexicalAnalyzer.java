import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.ho.yaml.Yaml;

public class LexicalAnalyzer {

	int pos = 0;

	public ArrayList<String> analize(String input) throws Exception {

		ArrayList<String> array = new ArrayList<String>();
		Automata automata = new Automata();

		while (pos < input.length()-1){

			AutomataResponse response = automata.process(input, pos);

			// Checkeo de errores
			if (response.isError()){

				array.add("(99," + response.getErrorDetail() + ") ");

			}else{

				String lexeme = StringUtils.trimToEmpty(response.getLexeme());

				if (true && ("q1".equals(response.lastStatus)) && (lexeme.length()>16)){ //TODO: agregar control solo para java
					lexeme = StringUtils.substring(lexeme, 0, 16);
					array.add("warning: longitud mayor a 16 caracteres");
				}

				String token =  getToken(lexeme);
	
				array.add("(" + token +","+lexeme+")");

			}

			pos = response.getTo();
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	private String getToken(String token) throws FileNotFoundException {
		File tokenConfig = new File("tokenJAVA.config");
		Map<String, String> map = Yaml.loadType(tokenConfig, HashMap.class);
		String result = map.get(token);
		return (result == null) ? "9" : result;
	}
}