package fu.hao.trust.staticAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.opencsv.CSVWriter;

import fu.hao.trust.data.App;
import fu.hao.trust.utils.Log;
import fu.hao.trust.utils.Settings;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;

/**
 * @ClassName: RespAnalysisCG
 * @Description: BFS Call Graph and find all responses and SMS receving
 * @author: Hao Fu
 * @date: Feb 25, 2016 10:38:04 AM
 */
public class RespAnalysis {
	private final String TAG = getClass().getSimpleName();

	private static CallGraph cg;
	private static JimpleBasedInterproceduralCFG icfg;
	// <Sensitive invocation, entries>
	private static Map<Stmt, LinkedList<SootMethod>> sinkUnits;
	private static Map<Stmt, LinkedList<SootMethod>> srcUnits;
	private static List<String> PscoutMethod;
	private static List<String> srcMethods;

	Map<Stmt, Set<SootMethod>> cgPaths;
	// The methods might init Intent intent that startActivity/Service
	Map<Stmt, LinkedList<SootMethod>> startActPaths = new HashMap<>();

	Map<SootMethod, SootClass> fragDeclrs;

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final RespAnalysis instance = new RespAnalysis();
	}

	// Get THE instance
	public static final RespAnalysis v() {
		return SingletonHolder.instance;
	}

	public Map<Stmt, LinkedList<SootMethod>> runAnalysis() throws IOException {
		cgPaths = new HashMap<>();

		try {
			PscoutMethod = FileUtils.readLines(new File(
					"./inbound_extended.txt"));// ("./inbound.txt"));
			srcMethods = FileUtils.readLines(new File("./sensitive_srcs.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		App app = App.v();
		cg = app.getCG();
		icfg = app.getICFG();
		sinkUnits = new HashMap<>();
		srcUnits = new HashMap<>();
		fragDeclrs = new HashMap<>();

		for (SootClass sootClass : Scene.v().getClasses()) {
			if (sootClass.toString().startsWith("android")
					|| sootClass.toString().startsWith("java")
					|| sootClass.toString().startsWith("org.apache.http")
					|| sootClass.toString().startsWith("org.xml")) {
				continue;
			}

			for (SootMethod method : sootClass.getMethods()) {
				try {
					if (!method.hasActiveBody()) {
						method.retrieveActiveBody();
						/*
						Iterator<Edge> iterator = cg.edgesInto(method);
						while (iterator.hasNext()) {
							System.err.println(method + ": "
									+ iterator.next().getSrc().method());
						}

						for (Unit caller : icfg.getCallersOf(method)) {
							System.err.println(method + ": " + caller);
						}*/

					}
					for (Unit unit : icfg.getCallsFromWithin(method)) {
						Stmt stmt = (Stmt) unit;
						if (stmt.containsInvokeExpr()) {
							SootMethod potential = stmt.getInvokeExpr()
									.getMethod();
							Log.bb(TAG, "Parse: " + unit + " at " + method
									+ " in " + sootClass);
							if (PscoutMethod.contains(potential.toString())
									|| potential.toString().contains(
											"org.apache.http")
									&& potential.toString().contains("exec")) {
								LinkedList<SootMethod> set = new LinkedList<>();
								set.add(method);
								sinkUnits.put((Stmt) unit, set);
								Log.warn(TAG, "\n");
								Log.warn(TAG, "Sink: " + unit + " at " + method
										+ " in " + sootClass);
								bfsCG((Stmt) unit, method, sinkUnits);
							} else if (srcMethods
									.contains(potential.toString())) {
								LinkedList<SootMethod> set = new LinkedList<>();
								set.add(method);
								srcUnits.put((Stmt) unit, set);
								Log.warn(TAG, "\n");
								Log.warn(TAG, "Src: " + unit + " at " + method
										+ " in " + sootClass);
								bfsCG((Stmt) unit, method, srcUnits);
							}
						}

						if (stmt.toString().contains("SMS_RECEIVED")
								|| stmt.toString().contains("content://sms")) {
							LinkedList<SootMethod> set = new LinkedList<>();
							set.add(method);
							sinkUnits.put((Stmt) unit, set);
							Log.warn(TAG, "\n");
							Log.warn(TAG, "Sens: " + unit + " at " + method
									+ " in " + sootClass);
							bfsCG((Stmt) unit, method, sinkUnits);
						} else if (stmt.toString().contains("startActivity")
								|| stmt.toString().contains("startService")) {
							dfsCG((Stmt) unit, startActPaths);
						} else if (stmt.containsInvokeExpr()) {
							SootMethod met = stmt.getInvokeExpr().getMethod();
							if (met.isConstructor()) {
								SootClass clazz = met.getDeclaringClass();
								SootClass oclazz = clazz;
								boolean found = false;
								while (clazz != null
										&& !clazz.getName().startsWith("java")) {
									if (clazz.getName().contains("Fragment")) {
										found = true;
										break;
									}
									clazz = clazz.getSuperclass();
								}
								if (found) {
									fragDeclrs.put(method, oclazz);
								}
							}
						}
					}
				} catch (RuntimeException e) {
					Log.warn(TAG, e.getMessage());
				}

			}

		}

		writeCSV(sinkUnits, true);
		writeCSV(srcUnits, false);
		writeStartActServ(startActPaths);
		writeFragDeclrs(fragDeclrs);
		return sinkUnits;
	}

	/*
	 * bfs the CG to get the entries of all sensitive methods
	 */
	public void bfsCG(Stmt stmt, SootMethod target,
			Map<Stmt, LinkedList<SootMethod>> sinkUnits) {
		Queue<SootMethod> queue = new LinkedList<>();
		Set<SootMethod> visited = new HashSet<>();
		queue.add(target);
		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				SootMethod node = queue.poll();
				SootMethod entry = null;
				if (visited.contains(node)) {
					continue;
				}
				visited.add(node);
				Iterator<Edge> iterator = cg.edgesInto(node);
				if (!iterator.hasNext()
						&& !node.getDeclaringClass().toString()
								.startsWith("dummyMain")) {
					LinkedList<SootMethod> mList = sinkUnits.get(stmt);
					mList.add(node);
					entry = node;
					Log.msg(TAG, target + ": " + node);
				}

				while (iterator.hasNext()) {
					Edge in = iterator.next();
					Log.debug(TAG, "Src: " + in.getSrc().method() + ", Tgt: "
							+ node);
					String prevClass = in.getSrc().method().getDeclaringClass()
							.toString();
					if (prevClass.startsWith("dummyMain")) {
						// break;
						LinkedList<SootMethod> mList = sinkUnits.get(stmt);
						entry = in.getTgt().method();
						if (!mList.contains(entry)) {
							mList.add(in.getTgt().method());
						}

						Log.msg(TAG, target + ": " + in.getTgt().method());
					}

					queue.add(in.getSrc().method());
				}

				if (entry != null) {
					SootClass entryClass = entry.getDeclaringClass();
					for (SootClass subclass : Scene.v().getActiveHierarchy()
							.getSubclassesOf(entryClass)) {
						try {
							subclass.getMethodByName(entry.getName());
						} catch (java.lang.RuntimeException e) {
							SootMethod newm = new SootMethod(entry.getName(),
									entry.getParameterTypes(),
									entry.getReturnType());
							newm.setDeclaringClass(subclass);
							subclass.addMethod(newm);
							LinkedList<SootMethod> mList = sinkUnits.get(stmt);
							mList.add(newm);
						}
					}
				}
			}
		}
	}

	public void dfsCG(Stmt stmt, Map<Stmt, LinkedList<SootMethod>> cgPaths) {
		SootMethod target = stmt.getInvokeExpr().getMethod();
		LinkedList<SootMethod> prevs = new LinkedList<>();
		cgPaths.put(stmt, prevs);

		SootMethod node = target;
		// TODO Now only consider one path
		while (cg.edgesInto(node).hasNext()) {
			Edge edge = cg.edgesInto(node).next();
			node = edge.getSrc().method();
			if (!node.getSignature().contains(
					"dummyMainClass: void dummyMainMethod")) {
				prevs.add(node);
			}
		}

	}

	public void writeCSV(Map<Stmt, LinkedList<SootMethod>> cgPaths, boolean sink)
			throws IOException {
		String csv;
		if (sink) {
			csv = Settings.getStaticOutDir() + Settings.getApkName()
					+ "_resp.csv";
		} else {
			csv = Settings.getStaticOutDir() + Settings.getApkName()
					+ "_src.csv";
		}
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
		for (Stmt stmt : cgPaths.keySet()) {
			List<String> result = new ArrayList<>();
			result.add(stmt.toString());
			for (SootMethod method : cgPaths.get(stmt)) {
				Log.msg(TAG, method.getSignature());
				result.add(method.toString());
			}
			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}

	public void writeStartActServ(Map<Stmt, LinkedList<SootMethod>> cgPaths)
			throws IOException {
		String csv = Settings.getStaticOutDir() + Settings.getApkName()
				+ "_StartActServ.csv";
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
		for (Stmt stmt : cgPaths.keySet()) {
			List<String> result = new ArrayList<>();
			result.add(stmt.toString());
			for (SootMethod method : cgPaths.get(stmt)) {
				Log.msg(TAG, method.getDeclaringClass().getName() + ": "
						+ method.getName());
				result.add(method.getDeclaringClass().getName() + ": "
						+ method.getName());
			}
			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}

	private void writeFragDeclrs(Map<SootMethod, SootClass> fragDeclrs)
			throws IOException {
		String csv = Settings.getStaticOutDir() + Settings.getApkName()
				+ "_FragDeclrs.csv";
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
		for (SootMethod method : fragDeclrs.keySet()) {
			List<String> result = new ArrayList<>();

			SootClass clazz = fragDeclrs.get(method);
			Log.msg(TAG, "Fragment: " + clazz + ", " + method);
			result.add(clazz.getName());
			result.add(method.getDeclaringClass() + ": " + method.getName());
			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();

	}

	public void wriCSV(Map<Unit, SootMethod> sinkUnits, boolean sink)
			throws IOException {
		String csv;
		if (sink) {
			csv = Settings.getStaticOutDir() + Settings.getApkName()
					+ "_resp.csv";
		} else {
			csv = Settings.getStaticOutDir() + Settings.getApkName()
					+ "_src.csv";
		}

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
		for (Unit unit : sinkUnits.keySet()) {
			List<String> result = new ArrayList<>();
			result.add(unit.toString());
			result.add(sinkUnits.get(unit).toString());

			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}
}
