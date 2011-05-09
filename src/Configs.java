import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.ho.yaml.Yaml;

public class Configs {

	public static String lang;

	public static String input;
	public static String output;
	public static String automaton;
	public static String tokensConfig;

	@SuppressWarnings("unchecked")
	public static void setConfigs(String language) throws Exception {

		lang = language;
		File config = new File("config.yaml");
		Map<String, Map> configs = Yaml.loadType(config, HashMap.class);

		Map<String, String> map = configs.get(language);

		input = map.get("input");
		output = map.get("output");
		automaton = map.get("automaton");
		tokensConfig = map.get("tokensConfig");
	}
}