package fu.hao.trust;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import soot.SootMethod;

import com.opencsv.CSVWriter;

import fu.hao.trust.data.Results;
import fu.hao.trust.staticAnalysis.SuspAnalysis;
import fu.hao.trust.utils.Log;
import fu.hao.trust.utils.Settings;

public class Main {
	private final static String TAG = Main.class.getName();

	public static void main(String[] args) {
		try {
			Main main = new Main();
			main.myTestMain(args);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws XmlPullParserException
	 * @Title: myTestMain
	 * @Description: Main
	 * @param @param args
	 * @param @throws IOException
	 * @param @throws InterruptedException
	 * @return void
	 * @throws
	 */
	public void myTestMain(String[] args) throws IOException,
			InterruptedException, XmlPullParserException {
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
			// FIXME
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

			// App.v();
			SuspAnalysis sa = SuspAnalysis.v();
			Set<SootMethod> suspicous = sa.runAnalysis();
			writeCSV(suspicous);
			Log.msg(TAG, "Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds\n");

		}
	}

	/**
	 * @Title: writeCSV
	 * @Description: write results to csv
	 * @param views
	 * @throws IOException
	 * @return: void
	 */
	public void writeCSV(Set<SootMethod> suspicous) throws IOException {
		String csv = "./sootOutput/" + Settings.apkName + ".csv";
		File csvFile = new File(csv);
		Log.msg(TAG, csv);
		if (!csvFile.exists()) {
			csvFile.createNewFile();
		} else {
			csvFile.delete();
			csvFile.createNewFile();
		}
		CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
		List<String[]> results = new ArrayList<>();
		for (SootMethod method : suspicous) {
			Log.msg(TAG, method.getSignature());
			List<String> result = new ArrayList<>();
			result.add(method.getDeclaringClass().getName());
			result.add(method.getName());
			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}

}
