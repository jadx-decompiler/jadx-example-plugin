package jadx.plugins.example;

import jadx.api.plugins.options.impl.BasePluginOptionsBuilder;

public class ExampleOptions extends BasePluginOptionsBuilder {

	private boolean enable;

	@Override
	public void registerOptions() {
		boolOption(JadxExamplePlugin.PLUGIN_ID + ".enable")
				.description("enable comment")
				.defaultValue(true)
				.setter(v -> enable = v);
	}

	public boolean isEnable() {
		return enable;
	}
}
