package jadx.plugins.example;

import jadx.api.plugins.options.OptionDescription;
import jadx.api.plugins.options.impl.BaseOptionsParser;

import java.util.List;

import static jadx.api.plugins.options.impl.JadxOptionDescription.booleanOption;

public class ExampleOptions extends BaseOptionsParser {
	public static final String ENABLE_OPTION = JadxExamplePlugin.PLUGIN_ID + ".enable";

	private boolean enable = true;

	@Override
	public void parseOptions() {
		enable = getBooleanOption(ENABLE_OPTION, true);
	}

	@Override
	public List<OptionDescription> getOptionsDescriptions() {
		return List.of(booleanOption(ENABLE_OPTION, "enable comment", true));
	}

	public boolean isEnable() {
		return enable;
	}

	@Override
	public String toString() {
		return "ExampleOptions{enable=" + enable + '}';
	}
}
