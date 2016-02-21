package fu.hao.trust;

/**  
 * Copyright © 2016 Hao Fu. All rights reserved.
 *
 * @Title: UIDroidMain.java
 * @Prject: INTEREST
 * @Package: 
 * @Description: TODO
 * @author: hao  
 * @date: Jan 26, 2016 2:17:25 PM
 * @version: V1.0  
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import presto.android.gui.graph.NNode;
import presto.android.gui.graph.NObjectNode;
import presto.android.gui.graph.NOpNode;
import presto.android.gui.listener.EventType;
import presto.android.xml.AndroidView;
import soot.Scene;
import soot.SootMethod;

import com.opencsv.CSVWriter;

import fu.hao.trust.data.App;
import fu.hao.trust.data.SensSrc;
import fu.hao.trust.gator.Config;
import fu.hao.trust.gator.DefaultGUIAnalysisOutput;
import fu.hao.trust.gator.FixpointSolver;
import fu.hao.trust.gator.GUIAnalysis;
import fu.hao.trust.noTaint.SensAnalysis;

/**
 * @ClassName: UIDroidMain
 * @Description: Main class
 * @author: hao
 * @date: Jan 26, 2016 2:17:25 PM
 */
public class UIDroidMain {

	private final static Logger logger = LoggerFactory
			.getLogger(UIDroidMain.class);

	public static void main(String[] args) {
		try {
			myTestMain(args);
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
	public static void myTestMain(String[] args) throws IOException,
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
				logger.error("Invalid input file format: " + extension);
				return;
			}
		}

		for (final String fileName : apkFiles) {
			System.gc();
			soot.G.reset();
			final long beforeRun = System.nanoTime();
			logger.info("Begin to analyze: " + fileName);
			Config.apkPath = args[0] + File.separator + fileName;
			// decompile
			String decomPath = args[0] + File.separator + "Decompiled"
					+ File.separator + fileName;
			decompile(decomPath);
			Config.project = decomPath;
			String OS = System.getProperty("os.name").toLowerCase();
			if (OS.indexOf("linux") >= 0) {
				Config.sdkDir = "/home/hao/Android/Sdk";
				String jarPath = Scene.v().getAndroidJarPath(
						Config.sdkDir + "/platforms", Config.apkPath);
				Config.apiLevel = "android-"
						+ jarPath.split("/android.jar")[0].split("-")[1];
				Config.android = "/home/hao/workspace/gator-3.0/AndroidBench/platform/"
						+ Config.apiLevel
						+ "/framework.jar:"
						+ "/home/hao/workspace/gator-3.0/AndroidBench/platform/"
						+ Config.apiLevel
						+ "/bouncycastle.jar:"
						+ "/home/hao/workspace/gator-3.0/AndroidBench/platform/"
						+ Config.apiLevel
						+ "/ext.jar:"
						+ "/home/hao/workspace/gator-3.0/AndroidBench/platform/"
						+ Config.apiLevel
						+ "/android-policy.jar:"
						+ "/home/hao/workspace/gator-3.0/AndroidBench/platform/"
						+ Config.apiLevel
						+ "/services.jar:"
						+ jarPath
						+ "/home/hao/workspace/gator-3.0/SootAndroid/deps/annotations.jar:"
						+ "/home/hao/workspace/gator-3.0/SootAndroid/deps/android-support-v4.jar:";
				Config.jre = "/home/hao/workspace/gator-3.0/AndroidBench/platform/"
						+ Config.apiLevel + "/core.jar";
				Config.listenerSpecFile = "/home/hao/workspace/gator-3.0/SootAndroid/listeners.xml";
				Config.wtgSpecFile = "/home/hao/workspace/gator-3.0/SootAndroid/wtg.xml";
			} else {
				Config.sdkDir = "C:/Users/hao/Downloads/android-sdk-windows/";
				String jarPath = Scene.v().getAndroidJarPath(
						Config.sdkDir + "/platforms", Config.apkPath);
				logger.info("jarPath: " + jarPath);
				String[] tmp = jarPath.split("\\\\android.jar")[0]
						.split("android-");
				Config.apiLevel = "android-" + tmp[tmp.length - 1];
				Config.android = "C:/Users/hao/workspace/Gator/gator/lib/AndroidBench"
						+ Config.apiLevel
						+ "/framework.jar:"
						+ "C:/Users/hao/workspace/Gator/gator/lib/AndroidBench"
						+ Config.apiLevel
						+ "/bouncycastle.jar:"
						+ "C:/Users/hao/workspace/Gator/gator/lib/AndroidBench"
						+ Config.apiLevel
						+ "/ext.jar:"
						+ "C:/Users/hao/workspace/Gator/gator/lib/AndroidBench"
						+ Config.apiLevel
						+ "/android-policy.jar:"
						+ "C:/Users/hao/workspace/Gator/gator/lib/AndroidBench"
						+ Config.apiLevel
						+ "/services.jar:"
						+ jarPath
						+ "C:/Users/hao/workspace/Gator/gator/deps/annotations.jar:"
						+ "C:/Users/hao/workspace/Gator/gator/deps/android-support-v4.jar:";
				Config.jre = "C:/Users/hao/workspace/Gator/gator/lib/AndroidBench"
						+ Config.apiLevel + "/core.jar";
				Config.listenerSpecFile = "C:/Users/hao/workspace/Gator/gator/listeners.xml";
				Config.wtgSpecFile = "C:/Users/hao/workspace/Gator/gator/wtg.xml";
			}

			Config.benchmarkName = fileName;
			Config.guiAnalysis = true;
			Config.apkMode = false;

			Config.processing();

			App.v();

			// contains a xml parser
			final GUIAnalysis ga = GUIAnalysis.v();

			getTriUI(ga);

			
			logger.info("Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds\n");

		}
	}

	public static void getTriUI(GUIAnalysis ga) throws IOException,
			XmlPullParserException {
		Set<SensSrc> sensSrcs;
		SensAnalysis sa = SensAnalysis.v();
		sensSrcs = sa.runAnalysis(ga);
		

		writeCSV(sensSrcs);
	}

	/**
	 * @Title: decompile
	 * @Description: Run apktool to decompile the apk
	 * @param decomPath
	 * @throws IOException
	 * @throws InterruptedException
	 * @return: void
	 */
	public static void decompile(String decomPath) throws IOException,
			InterruptedException {
		String OS = System.getProperty("os.name").toLowerCase();
		logger.info(OS);
		String cmd = " d " + Config.apkPath + " -o " + decomPath;
		Process pr;

		pr = Runtime.getRuntime().exec("java -jar ./res/apktool.jar" + cmd);

		pr.waitFor();
		BufferedReader buf = new BufferedReader(new InputStreamReader(
				pr.getInputStream()));
		String line = "";
		while ((line = buf.readLine()) != null) {
			logger.info(line);
		}
	}

	/**
	 * @Title: getViews
	 * @Description: Get Views: (event handler callback : view)
	 * @param ga
	 * @param solver
	 * @param output
	 * @return: Map<String,AndroidView>
	 */
	public static Map<String, AndroidView> getViews(FixpointSolver solver,
			DefaultGUIAnalysisOutput output, GUIAnalysis ga) {
		Map<String, AndroidView> map = new HashMap<>();
		ga.retrieveIds();

		logger.info("solver: " + solver);
		logger.info("solution: " + solver.solutionResults);
		for (Entry<NOpNode, Set<NNode>> entry : solver.solutionResults
				.entrySet()) {
			// logger.info("Operation: " + entry + ":: ");
			for (NNode node : entry.getValue()) {
				if (node == null || node.idNode == null) {
					continue;
				}
				if (ga.xmlParser.findViewById(node.idNode.getIdValue()) != null) {
					logger.info("Operation: " + entry + ":: ");
					logger.info(ga.xmlParser.findViewById(
							node.idNode.getIdValue()).getText());
					logger.info("event handler: "
							+ output.getExplicitEventsAndTheirHandlers(
									(NObjectNode) node).entrySet().toString());
				}
				// logger.info(output.getExplicitEventsAndTheirHandlers((NObjectNode)
				// node).toString());
				for (Entry<EventType, Set<SootMethod>> et : output
						.getExplicitEventsAndTheirHandlers((NObjectNode) node)
						.entrySet()) {
					for (SootMethod method : et.getValue()) {
						// logger.info(node.idNode.getIdValue().toString());
						map.put(method.getSignature(), ga.xmlParser
								.findViewById(node.idNode.getIdValue()));
						logger.info(method.getSignature());
						logger.info(ga.xmlParser.findViewById(
								node.idNode.getIdValue()).getText());
					}
				}
			}
		}

		return map;
	}

	/**
	 * @Title: writeCSV
	 * @Description: write results to csv
	 * @param views
	 * @throws IOException
	 * @return: void
	 */
	public static void writeCSV(Set<SensSrc> sensSrcs) throws IOException {
		String csv = "./sootOutput/" + Config.benchmarkName + ".csv";
		String apkName = Config.benchmarkName;
		File csvFile = new File(csv);
		logger.info(csv);
		if (!csvFile.exists()) {
			csvFile.createNewFile();
		} else {
			csvFile.delete();
			csvFile.createNewFile();
		}
		CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
		List<String[]> results = new ArrayList<>();
		for (SensSrc sensSrc : sensSrcs) {
			logger.info(sensSrc.toString());
			List<String> result = new ArrayList<>();

			result.add(apkName);
			result.add(sensSrc.getLoc().getSignature());
			result.add(sensSrc.getSrc().getSource().toString());
			result.add(sensSrc.getSinks().toString());
			result.add(sensSrc.printPaths());
			result.add(sensSrc.getConds().toString());
			result.add(sensSrc.getEntries().toString());
			result.add(sensSrc.getViews().toString());
			result.add(sensSrc.getViewIds().toString());

			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}

}