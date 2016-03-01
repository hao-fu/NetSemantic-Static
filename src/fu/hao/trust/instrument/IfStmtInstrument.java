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
 * @Description: ������Ҫ������app������if���ǰǶ��method app.DummyClass.ifInvoke, because
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
	
			// ����Ҫ�������ൽSoot
			SootClass klass = Scene.v().getSootClass("fu.hao.trust.instrument.DummyClass");
			// ͨ��������Ѱ�Һ���
			// ��Щ�������ں����Ƕ��
			SootMethod call = klass
					.getMethod("void addTag(int)");
			Value v1 = IntConstant.v(tag);
			Jimple insertStmt = Jimple.v();
			
			if (before) {
				// ����ֲ�������boolean����
				
					// ��������ȫ����Object��ʽ����
					System.out.println("tag: " + tag);
					units.insertBefore(insertStmt.newInvokeStmt(Jimple.v()
							.newStaticInvokeExpr(call.makeRef(), v1)), u);
				
			} else {
				// ����ֲ�������boolean����
				// ��������ȫ����Object��ʽ����
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
		// 10��ʲô���ͣ�
		Options.v().set_output_format(10);
		// DummyClass������ifInvoke������
		Scene.v().addBasicClass("fu.hao.trust.instrument.DummyClass", 2);
		// jimple transform (-> IR) package (phase)
		// �Ժ�������в���
		PackManager.v().getPack("jtp")
				.add(new Transform("jtp.myInstrumenter", new BodyTransformer() {
					@Override
					protected void internalTransform(final Body b,
							String phaseName,
							@SuppressWarnings("rawtypes") Map options) {
						// ��ú��������������
						final PatchingChain<Unit> units = b.getUnits();
						// ѭ����ÿ�����
						for (Iterator<Unit> iter = units.snapshotIterator(); iter
								.hasNext();) {							
							// ��̬: �û���Unit
							final Unit u = (Unit) iter.next();
							System.out.println(u);
							/*
							 * visitor design pattern�۲���ģʽ: �ж��������
							 * apply����StmtSwitch�������������Ϊ���� apply�����������ʵ�� e.g.
							 * public void apply(Switch sw) { ((StmtSwitch)
							 * sw).caseIfStmt(this); }
							 * ��unitʵ�ʵ�������ΪJIfStmtʱ������apply�����sw���ҡ�(this)��if���
							 * StmtSwith�����ж�������Ͳ�������Ӧ���������������������������ָ����
							 */
							u.apply(new AbstractStmtSwitch() {
								private void caseBranch(Stmt stmt) {
									System.err.println(stmt);
									// Value condition = ((IfStmt)
									// stmt).getCondition();
									// ��if���ʹ�õĲ�������expr
									// List<ValueBox> values = condition
									// .getUseBoxes();
									
										insert(units, u, null, true);
										tag++;

									// ò�������ڼ����Ƿ�Ϊ�Ϸ��ĺ����壬��û��ɵ�����
									b.validate();
								}

								// �����˳������caseIfStmt����
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

		// ������������jar(lib��)
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
		// ִ��Soot, �����Ｔ���Ƕ�����
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