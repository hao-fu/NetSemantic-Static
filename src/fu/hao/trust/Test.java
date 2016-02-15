/**  
 * Copyright © 2016 Hao Fu. All rights reserved.
 *
 * @Title: Test.java
 * @Prject: TRUST
 * @Package: fu.hao.trust
 * @Description: TODO
 * @author: hao  
 * @date: Feb 10, 2016 9:26:21 PM
 * @version: V1.0  
 */
package fu.hao.trust;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import patdroid.core.ClassInfo;
import patdroid.core.MethodInfo;
import patdroid.core.ReflectionClassDetailLoader;
import patdroid.dalvik.Instruction;
import patdroid.smali.SmaliClassDetailLoader;

/**
 * @ClassName: Test
 * @Description: TODO
 * @author: hao
 * @date: Feb 10, 2016 9:26:21 PM
 */
public class Test {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		// when a class is not loaded, load it with reflection
		ClassInfo.rootDetailLoader = new ReflectionClassDetailLoader();
		// pick an apk
		ZipFile apkFile;
		try {
			apkFile = new ZipFile(new File("C:/Users/hao/workspace/Button1/app/app-debug.apk"));
			// load all classes, methods, fields and instructions from an apk
			// we are using smali as the underlying engine
			new SmaliClassDetailLoader(apkFile, true).loadAll();
			// get the class representation for the MainActivity class in the
			// apk
			ClassInfo c = ClassInfo.findClass("de.ecspride.Activity1");
			// find all methods with the name "onCreate", most likely there is
			// only one
			MethodInfo[] m = c.findMethodsHere("onCreate");
			// print all instructions
			int counter = 0;
			for (Instruction ins : m[0].insns) {
				System.out.println("[" + counter + "]" + ins.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Title: decompile
	 * @Description: Run apktool to decompile the apk
	 * @param decomPath
	 * @throws IOException
	 * @throws InterruptedException
	 * @return: void
	 */
	public void decompile(String apkPath, String decomPath) throws IOException,
			InterruptedException {
		String cmd = " d " + apkPath + " -o " + decomPath;
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

}
