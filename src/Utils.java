import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

public class Utils {

	static ArrayList<Character> ascii = new ArrayList<Character>();
	static ArrayList<Character> Az = new ArrayList<Character>();
	static ArrayList<Character> numero = new ArrayList<Character>();

	static {
		for (int i = 0; i < 127; i++) {

			if (i >= 33 && i < 127)
				ascii.add(CharUtils.toChar((char) i));

			if ((i >= 65 && i < 91) || (i >= 97 && i < 123))
				Az.add(CharUtils.toChar((char) i));

			if (i >= 48 && i < 58)
				numero.add(CharUtils.toChar((char) i));
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getDelta(Map<String, Object> automaton) {

		// Retorno
		HashMap<String, Map<String, String>> ret;
		ret = new HashMap<String, Map<String, String>>();

		HashMap<String, Map<String, String>> delta;
		delta = (HashMap<String, Map<String, String>>) automaton.get("delta");

		for (Entry<String, Map<String, String>> entry : delta.entrySet()) {

			Map<String, String> temp = entry.getValue();

			Map<String, String> temp2 = new HashMap<String, String>();

			for (Entry<String, String> entry2 : temp.entrySet()) {

				String key = entry2.getKey();

				if ("ASCII-".equals(key))
					putDiff(ascii, "<>/", temp2, entry2.getValue());

				else if ("ASCII--".equals(key))
					putDiff(ascii, "<>/=", temp2, entry2.getValue());

				else if ("ASCII---".equals(key))
					putDiff(ascii, "!", temp2, entry2.getValue());

				else if ("ASCII----".equals(key))
					putDiff(ascii, "-", temp2, entry2.getValue());

				else if ("ASCII----".equals(key))
					putDiff(ascii, "->", temp2, entry2.getValue());

				else if ("letra".equals(key))
					putDiff(Az, "", temp2, entry2.getValue());

				else if ("numero".equals(key))
					putDiff(numero, "", temp2, entry2.getValue());

				else if ("caracter".equals(key))
					putDiff(ascii, "", temp2, entry2.getValue());

				else if ("caracter-".equals(key))
					putDiff(ascii, "\"", temp2, entry2.getValue());

				else if ("caracter--".equals(key))
					putDiff(ascii, "'", temp2, entry2.getValue());

				else if ("caracter---".equals(key))
					putDiff(ascii, "*", temp2, entry2.getValue());

				else if ("caracter----".equals(key))
					putDiff(ascii, "*/", temp2, entry2.getValue());

				else if ("enter".equals(key))
					temp2.put("\n", entry2.getValue());

				else if ("enterytab".equals(key)) {

					temp2.put("\n", entry2.getValue());
					temp2.put("\t", entry2.getValue());

				}else if ("blancoenterytab".equals(key)) {

					temp2.put(" ", entry2.getValue());
					temp2.put("\n", entry2.getValue());
					temp2.put("\t", entry2.getValue());

				}else
					temp2.put(key, entry2.getValue());
			}

			ret.put(entry.getKey(), temp2);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	private static void putDiff(ArrayList<Character> array, String delete, Map<String, String> ret, String q)
	{
		for (Iterator iterator = array.iterator(); iterator.hasNext();) {
			Character c = (Character) iterator.next();
			if (!StringUtils.contains(delete, c)) {
				ret.put(CharUtils.toString(c), q);
			}
		}
	}
}