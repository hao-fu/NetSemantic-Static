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
	public void test0177c2775de43572eb37e5de2803ff57eb297a9f() throws IOException {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/0177c2775de43572eb37e5de2803ff57eb297a9f/"; //droidkungfu";
		RespAnalysisTest test = new RespAnalysisTest();
		test.testRunAnalysis(args);
	}
	
	
	@Test
	public void test71d2f241f2cb8f4208dd3574df3c3ce0dacdd1c0() throws IOException {
		args[0] = "C:/Users/hao/workspace/DroidBenchProj/apks/pjapps/71d2f241f2cb8f4208dd3574df3c3ce0dacdd1c0/"; //droidkungfu";
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
			Settings.apkName = fileName + "_resp";
			Log.msg(TAG, "Begin to analyze: " + fileName);
			Settings.apkPath = args[0] + File.separator + fileName;
			Settings.platformDir = args[1];
		
			//App.v();
			RespAnalysis ra = RespAnalysis.v();
			ra.runAnalysis();
			Log.msg(TAG, "Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds\n");

		}
	}

}
