package jadx.plugins.example;

import jadx.api.plugins.JadxPlugin;
import jadx.api.plugins.JadxPluginContext;
import jadx.api.plugins.JadxPluginInfo;
import jadx.api.plugins.JadxPluginInfoBuilder;

public class JadxExamplePlugin implements JadxPlugin {
	public static final String PLUGIN_ID = "example-plugin";

	private final ExampleOptions options = new ExampleOptions();

	@Override
	public JadxPluginInfo getPluginInfo() {
		return JadxPluginInfoBuilder.pluginId(PLUGIN_ID)
				.name("Jadx example plugin")
				.description("Add jadx watermark comment to every class")
				.homepage("https://github.com/jadx-decompiler/jadx-example-plugin")
				.requiredJadxVersion("1.5.1, r2333")
				.build();
	}

	@Override
	public void init(JadxPluginContext context) {
		context.registerOptions(options);
		if (options.isEnable()) {
			context.addPass(new AddCommentPass());
		}
	}
}
