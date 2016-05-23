package fu.hao.trust;

import static org.junit.Assert.assertEquals;


import fu.hao.trust.data.Results;
import fu.hao.trust.utils.Settings;

public class Test {
	public static void main(String[] nargs) {
		String[] args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		Settings.logLevel = 2; //2
		
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Callbacks_Button2/app";
		Main.main(args);
		String res1 = "<de.ecspride.FieldSensitivity1: void onCreate(android.os.Bundle)>";
		assertEquals(Results.results.contains(res1), true);
		assertEquals(Results.results.size(), 1);
	}

}
