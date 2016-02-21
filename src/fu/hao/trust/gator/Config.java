/*
 * Using FlowDroid to load class files of the apk
 */
package fu.hao.trust.gator;

import java.io.File;


//import app.MySetupApplication;


import com.google.common.collect.Lists;

import presto.android.Configs;


public class Config extends Configs{
	
	public static String apkPath = null;
	public static String platformDir = null;
	public static GUIAnalysis ga;
	
	public static void processing() {
		File projectFile = new File(project);
		if (projectFile.isFile()) {
			apkModeProcessing();
			return;
		}
		
		bytecodes = "";//project + "/bin/classes";
		//System.out.println(bytecodes);
		
		//String sep = File.separator;
		//String pathSep = File.pathSeparator;
		//String classpath = System.getProperty("java.home") + sep + "lib" + sep
				//+ "rt.jar";
		platformDir = Config.sdkDir + "/platforms";// /android-17/android.jar";
		//classpath += pathSep + platformDir;
		
		System.out.println(apiLevel);
	    numericApiLevel = Integer.parseInt(apiLevel.substring("android-".length()));
	    sysProj = Configs.sdkDir + "/platforms/" + Configs.apiLevel + "/data";
	    // make validate() happy
	    depJars = Lists.newArrayList();
	    extLibs = Lists.newArrayList();


	    validate();
	    System.out.println("[Config] finished.");
	}
}
