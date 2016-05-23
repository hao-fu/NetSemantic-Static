package fu.hao.trust.staticAnalysis;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import soot.SootMethod;
import soot.Unit;
import fu.hao.trust.data.Results;
import fu.hao.trust.utils.Log;
import fu.hao.trust.utils.Settings;

public class EntryFinderTest {
	
	final String TAG = "test";

	@Test
	public void testRunAnalysis() throws IOException {
		Settings.logLevel = 2;
		String[] args = new String[2];
		args[1] = "C:/Users/hao/Downloads/android-sdk-windows/platforms";
		args[0] = "C:/Users/hao/workspace/MultiThreading/app/"; //pjapps"; //droidkungfu";
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
			Log.msg(TAG, "Begin to analyze: " + fileName);
			Settings.apkName = fileName;
			Settings.apkPath = args[0] + File.separator + fileName;
			Settings.platformDir = args[1];
		
			//App.v();
			EntryFinder ef = EntryFinder.v();
			ef.runAnalysis();
			Log.msg(TAG, "Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds\n");

		}
	}

}
