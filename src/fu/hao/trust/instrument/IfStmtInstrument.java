/*
 * reference: app.IfStmtInstrument
 */

package fu.hao.trust.instrument;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import soot.ArrayType;
import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.PackManager;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.Value;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.IfStmt;
import soot.jimple.IntConstant;
import soot.jimple.Jimple;
import soot.jimple.LookupSwitchStmt;
import soot.jimple.Stmt;
import soot.jimple.TableSwitchStmt;
import soot.options.Options;

/**
 * @ClassName: IfStmtInstrument
 * @Description: 用于在要分析的app里所有if语句前嵌入method app.DummyClass.ifInvoke, because
 *               FlowDroid only accepts method as sinks, so trans conditional to
 *               method so that it is able to be a sink method; then we can
 *               extract context factors from them by using taint analysis
 *               (propopgation of natural env var, natural env API calls are
 *               sources).
 * @author: Hao Fu
 * @date: Dec 29, 2015 6:56:52 PM
 */
public class IfStmtInstrument {
	static int tag = 99999;
	
	public static void insert(PatchingChain<Unit> units, Unit u, Value v,
			boolean before) {
	
			// 载入要分析的类到Soot
			SootClass klass = Scene.v().getSootClass("fu.hao.trust.instrument.DummyClass");
			// 通过函数名寻找函数
			// 这些函数用于后面的嵌入
			SootMethod call = klass
					.getMethod("void addTag(int)");
			Value v1 = IntConstant.v(tag);
			Jimple insertStmt = Jimple.v();
			
			if (before) {
				// 如果局部变量是boolean类型
				
					// 其余类型全部以Object形式传入
					System.out.println("tag: " + tag);
					units.insertBefore(insertStmt.newInvokeStmt(Jimple.v()
							.newStaticInvokeExpr(call.makeRef(), v1)), u);
				
			} else {
				// 如果局部变量是boolean类型
				// 其余类型全部以Object形式传入
				System.out.println("tag: " + tag);
				units.insertAfter(insertStmt.newInvokeStmt(Jimple.v()
						.newStaticInvokeExpr(call.makeRef(), v1)), u);
			}
	}

	public static void main(String[] args) {
		Options.v().set_src_prec(5);
		// apk file path
		String apkFileLocation = args[0];
		// platform path
		String androidJar = args[1];
		// libdir
		// output dir path
		String outputDir = args[2];

		Options.v().set_process_dir(Collections.singletonList(apkFileLocation));
		Options.v().set_android_jars(androidJar);
		Options.v().set_output_dir(outputDir);
		Options.v().set_allow_phantom_refs(true);
		// 10是什么类型？
		Options.v().set_output_format(10);
		// DummyClass包含了ifInvoke的声明
		Scene.v().addBasicClass("fu.hao.trust.instrument.DummyClass", 2);
		// jimple transform (-> IR) package (phase)
		// 对函数体进行操作
		PackManager.v().getPack("jtp")
				.add(new Transform("jtp.myInstrumenter", new BodyTransformer() {
					@Override
					protected void internalTransform(final Body b,
							String phaseName,
							@SuppressWarnings("rawtypes") Map options) {
						// 获得函数体内所有语句
						final PatchingChain<Unit> units = b.getUnits();
						// 循环对每条语句
						for (Iterator<Unit> iter = units.snapshotIterator(); iter
								.hasNext();) {							
							// 多态: 用基类Unit
							final Unit u = (Unit) iter.next();
							System.out.println(u);
							/*
							 * visitor design pattern观察者模式: 判断语句类型
							 * apply接收StmtSwitch的派生类对象作为参数 apply的派生类具体实现 e.g.
							 * public void apply(Switch sw) { ((StmtSwitch)
							 * sw).caseIfStmt(this); }
							 * 当unit实际的派生类为JIfStmt时，调用apply会告诉sw“我”(this)是if语句
							 * StmtSwith用于判断语句类型并根据相应类型作出操作（具体操作由重载指定）
							 */
							u.apply(new AbstractStmtSwitch() {
								private void caseBranch(Stmt stmt) {
									System.err.println(stmt);
									// Value condition = ((IfStmt)
									// stmt).getCondition();
									// 此if语句使用的操作数和expr
									// List<ValueBox> values = condition
									// .getUseBoxes();
									
										insert(units, u, null, true);
										tag++;

									// 貌似是用于检验是否为合法的函数体，但没完成的样子
									b.validate();
								}

								// 重载了抽象类的caseIfStmt函数
								@Override
								public void caseIfStmt(IfStmt stmt) {
									caseBranch(stmt);
								}

								@Override
								public void caseTableSwitchStmt(
										TableSwitchStmt stmt) {
									caseBranch(stmt);
								}

								@Override
								public void caseLookupSwitchStmt(
										LookupSwitchStmt stmt) {
									caseBranch(stmt);
								}
							});
						}
					}
				}));

		// 设置运行所需jar(lib等)
		String class_path = "." + File.pathSeparator + "bin" + File.pathSeparator
				+ androidJar + File.pathSeparator
				+ System.getProperty("java.class.path") + File.pathSeparator
				+ System.getProperty("java.home") + File.separator + "lib";

		String dummyclasspath = Scene.v().defaultClassPath()
				+ File.pathSeparator + class_path;
		System.out.println(dummyclasspath);
		Options.v().set_soot_classpath(dummyclasspath);
		Scene.v().loadNecessaryClasses();

		//Scene.v().forceResolve("org.bouncycastle.asn1.DERObjectIdentifier", 3);
		// 执行Soot, 在这里即完成嵌入操作
		PackManager.v().runPacks();
		PackManager.v().writeOutput();
	}

	public static String findJarfiles(String dirName) {
		File dir = new File(dirName);

		File[] jarFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".jar");
			}

		});
		StringBuffer sb = new StringBuffer();
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = jarFiles).length;
		for (int i = 0; i < j; i++) {
			File f = arrayOfFile1[i];
			sb.append(f.getAbsolutePath());
			sb.append(File.pathSeparator);
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private static Local addTmpArray(Body body) {
		Local tmpArray = Jimple.v().newLocal("tmpArray",
				ArrayType.v(RefType.v("java.lang.Object"), 1));
		body.getLocals().add(tmpArray);
		return tmpArray;
	}
}