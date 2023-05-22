package jadx.plugins.example;

import jadx.api.plugins.JadxPlugin;
import jadx.api.plugins.JadxPluginContext;
import jadx.api.plugins.JadxPluginInfo;

public class JadxExamplePlugin implements JadxPlugin {
	public static final String PLUGIN_ID = "example-plugin";

	private final ExampleOptions options = new ExampleOptions();

	@Override
	public JadxPluginInfo getPluginInfo() {
		return new JadxPluginInfo(PLUGIN_ID, "Jadx example plugin", "Add jadx watermark comment to every class");
	}

	@Override
	public void init(JadxPluginContext context) {
		context.registerOptions(options);
		if (options.isEnable()) {
			context.addPass(new AddCommentPass());
		}
	}
}
