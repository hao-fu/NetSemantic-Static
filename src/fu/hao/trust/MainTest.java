package fu.hao.trust;

import static org.junit.Assert.*;

import org.junit.Test;

import fu.hao.trust.analysis.Results;
import fu.hao.trust.utils.Settings;

public class MainTest {
	String[] args = new String[2];

	public MainTest() {
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		Settings.logLevel = 2; //2
	}
	
	@Test
	public void testGeneralJava_Loop1() {
		args[0] = "C:/Users/hao/workspace/GeneralJava_Loop1/app/";
		Main.main(args);
		
		String res1 = "<de.ecspride.LoopExample1: void onCreate(android.os.Bundle)>";
		String res2 = "<dummyMainClass: void dummyMainMethod(java.lang.String[])>";
		assertEquals(Results.results.contains(res1), true);
		assertEquals(Results.results.contains(res2), true);
		assertEquals(Results.results.size(), 2);
	}

	@Test
	public void testGeneralJava_UnreachableCode() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/GeneralJava_UnreachableCode/app/";
		Main.main(args);
		assertEquals(Results.results.isEmpty(), true);
	}
	
	@Test
	public void testGeneralJava_VirtualDispatch1() {
		//args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/VirtualDispatch1/";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/GeneralJava_VirtualDispatch1/app";
		Main.main(args);
		assertEquals(Results.results.isEmpty(), true);
	}
	
	@Test
	public void test() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks";
		Main.main(args);
		
		String res1 = "<de.ecspride.LoopExample1: void onCreate(android.os.Bundle)>";
		String res2 = "<dummyMainClass: void dummyMainMethod(java.lang.String[])>";
		assertEquals(Results.results.contains(res1), true);
		assertEquals(Results.results.contains(res2), true);
		assertEquals(Results.results.size(), 2);
	}
	
	@Test
	public void testFieldAndObjectSensitivity_FieldSensitivity1() {
		//args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/VirtualDispatch1/";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/FieldAndObjectSensitivity_FieldSensitivity1/app";
		Main.main(args);
		String res1 = "<de.ecspride.FieldSensitivity1: void onCreate(android.os.Bundle)>";
		assertEquals(Results.results.contains(res1), true);
		assertEquals(Results.results.size(), 1);
	}
	
	@Test
	public void testLifecycle_ActivityLifecycle1() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle1/app";
		Main.main(args);
		String res1 = "<de.ecspride.FieldSensitivity1: void onCreate(android.os.Bundle)>";
		assertEquals(Results.results.contains(res1), true);
		assertEquals(Results.results.size(), 1);
	}
	
	@Test
	public void testLifecycle_ActivityLifecycle2() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle2/app";
		Main.main(args);
	}
	
	@Test
	public void testLifecycle_ActivityLifecycle3() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle3/app";
		Main.main(args);
	}
	
	@Test
	public void testLifecycle_ActivityLifecycle4() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle4/app";
		Main.main(args);
	}
	
	@Test
	public void testLifecycle_ApplicationLifecycle1() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle1/app";
		Main.main(args);
	}
	
	@Test
	public void testLifecycle_ApplicationLifecycle2() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle2/app";
		Main.main(args);
	}
	
	@Test
	public void testLifecycle_ServiceLifecycle1() {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ServiceLifecycle1/app";
		Main.main(args);
	}

}
