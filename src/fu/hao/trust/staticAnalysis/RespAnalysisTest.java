package fu.hao.trust.staticAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fu.hao.trust.data.Results;
import fu.hao.trust.utils.Log;
import fu.hao.trust.utils.Settings;

public class RespAnalysisTest {
	final String TAG = "test";

	String[] args = new String[2];

	public static void main(String[] args) {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_MultiHandlers1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/MultiHandlers1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_MethodOverride1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/MethodOverride1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_LocationLeak3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LocationLeak3/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_LocationLeak2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LocationLeak2/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_LocationLeak1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/LocationLeak1/app";

		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button5() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button5/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button4/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button3/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button2/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchCallbacks_Button1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Button1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_JavaThread2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/JavaThread2/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_JavaThread1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/JavaThread1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_Executor1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Executor1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchThreading_AsyncTask1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/AsyncTask1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_SharedPreferenceChanged1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/SharedPreferenceChanged1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_AsynchronousEventOrdering1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/AsynchronousEventOrdering1/app";
		// + "GeneralJava_VirtualDispatch1/app"; LocationLeak1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_EventOrdering1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/EventOrdering1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_FragmentLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/FragmentLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_FragmentLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/FragmentLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ServiceLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ServiceLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjBroadcastReceiverLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/BroadcastReceiverLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ServiceLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ServiceLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjBroadcastReceiverLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_BroadcastReceiverLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ApplicationLifecycle3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle3/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ApplicationLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ApplicationLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ApplicationLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle4() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle4/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle3() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle3/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjLifecycle_ActivityLifecycle1() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/Lifecycle_ActivityLifecycle1/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testDroidBenchProjActivityCommunication2() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/ActivityCommunication2/app";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testIshehui() {
		RespAnalysisTest test = new RespAnalysisTest();
		args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/benign/ishehua/com.ishehui.wjh/";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test91deec899c6df09ef68f802979c2697d8a8803be() {
		RespAnalysisTest test = new RespAnalysisTest();

		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/91deec899c6df09ef68f802979c2697d8a8803be/";
		try {
			test.testRunAnalysis(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void prepare() {
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
	}

	@Test
	public void test0177c2775de43572eb37e5de2803ff57eb297a9f()
			throws IOException {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/0177c2775de43572eb37e5de2803ff57eb297a9f/"; // droidkungfu";
		RespAnalysisTest test = new RespAnalysisTest();
		test.testRunAnalysis(args);
	}

	@Test
	public void test71d2f241f2cb8f4208dd3574df3c3ce0dacdd1c0()
			throws IOException {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/71d2f241f2cb8f4208dd3574df3c3ce0dacdd1c0/"; // droidkungfu";
		RespAnalysisTest test = new RespAnalysisTest();
		test.testRunAnalysis(args);
	}

	public void testRunAnalysis(String[] args) throws IOException {
		Settings.logLevel = 1;
		List<String> apkFiles = new ArrayList<>();
		File apkFile = new File(args[0]);
		if (apkFile.isDirectory()) {
			String[] dirFiles = apkFile.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return (name.endsWith(".apk"));
				}
			});
			for (String s : dirFiles) {
				apkFiles.add(s);
			}
		} else {
			// 获得文件类型
			String extension = apkFile.getName().substring(
					apkFile.getName().lastIndexOf("."));
			if (extension.equalsIgnoreCase(".txt")) {
				BufferedReader rdr = new BufferedReader(new FileReader(apkFile));
				String line = null;
				while ((line = rdr.readLine()) != null)
					apkFiles.add(line);
				rdr.close();
			} else if (extension.equalsIgnoreCase(".apk"))
				apkFiles.add(args[0]);
			else {
				Log.err(TAG, "Invalid input file format: " + extension);
				return;
			}
		}

		for (final String fileName : apkFiles) {
			Results.reset();
			System.gc();
			soot.G.reset();
			final long beforeRun = System.nanoTime();
			Settings.setApkName(fileName);
			Log.msg(TAG, "Begin to analyze: " + fileName);
			Settings.setApkPath(args[0] + File.separator + fileName);
			Settings.platformDir = args[1];

			// App.v();
			RespAnalysis ra = RespAnalysis.v();
			ra.runAnalysis();
			Log.msg(TAG, "Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds\n");

		}
	}

}
