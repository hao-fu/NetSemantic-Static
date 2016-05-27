package fu.hao.trust.instrument;

import org.junit.Test;

public class IfStmtInstrumentTest {

	// @Test
	public void testGeneralJava_Loop1() {
		String[] args = new String[3];
		args[0] = "C:/Users/hao/workspace/GeneralJava_Loop1/app/app-debug.apk";
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[2] = "C:/Users/hao/workspace/DroidBenchProj/apks";
		IfStmtInstrument.main(args);
	}

	@Test
	public void test() {
		String[] args = new String[3];
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/GeneralJava_VirtualDispatch1/app/app-release.apk";
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[2] = "C:/Users/hao/workspace/DroidBenchProj/apks/VirtualDispatch1/";
		IfStmtInstrument.main(args);
	}

}
