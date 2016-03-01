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
	private final String TAG = getClass().toString();

	private static CallGraph cg;
	private static JimpleBasedInterproceduralCFG icfg;

	private static Map<Stmt, Set<SootMethod>> sensUnits;
	private static List<String> PscoutMethod;

	Map<Stmt, Set<SootMethod>> cgPaths;

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final RespAnalysis instance = new RespAnalysis();
	}

	// Get THE instance
	public static final RespAnalysis v() {
		return SingletonHolder.instance;
	}

	public Map<Stmt, Set<SootMethod>> runAnalysis() throws IOException {
		cgPaths = new HashMap<>();

		try {
			PscoutMethod = FileUtils.readLines(new File("./inbound.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		App app = App.v();
		cg = app.getCG();
		icfg = app.getICFG();
		sensUnits = new HashMap<>();

		for (SootClass sootClass : Scene.v().getClasses()) {
			if (sootClass.toString().startsWith("android")
					|| sootClass.toString().startsWith("java")
					|| sootClass.toString().startsWith("org.apache.http")) {
				continue;
			}

			for (SootMethod method : sootClass.getMethods()) {
				try {
					for (Unit unit : icfg.getCallsFromWithin(method)) {
						Stmt stmt = (Stmt) unit;
						if (stmt.containsInvokeExpr()) {
							SootMethod potential = stmt.getInvokeExpr()
									.getMethod();
							Log.debug(TAG, "s: " + unit + " at " + method
									+ " in " + sootClass);
							if (PscoutMethod.contains(potential.toString())
									|| potential.toString().contains(
											"org.apache.http")
									&& potential.toString().contains("exec")) {
								Set<SootMethod> set = new HashSet<>();
								set.add(method);
								sensUnits.put((Stmt)unit, set);
								Log.warn(TAG, "sens: " + unit + " at " + method
										+ " in " + sootClass);
								bfsCG((Stmt) unit, method);
							}
						}

						if (stmt.toString().contains("SMS_RECEIVED")
								|| stmt.toString().contains("content://sms")) {
							Set<SootMethod> set = new HashSet<>();
							set.add(method);
							sensUnits.put((Stmt)unit, set);
							Log.warn(TAG, "sens: " + unit + " at " + method
									+ " in " + sootClass);
							bfsCG((Stmt) unit, method);
						}
					}
				} catch (Exception e) {
					Log.debug(TAG, e.getMessage());
				}
			}

		}

		writeCSV(sensUnits);
		return sensUnits;
	}

	/*
	 * bfs the CG to get the entries of all sensitive methods
	 */
	public void bfsCG(Stmt stmt, SootMethod target) {
		Queue<SootMethod> queue = new LinkedList<>();
		Set<SootMethod> visited = new HashSet<>();
		queue.add(target);
		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				SootMethod node = queue.poll();
				if (visited.contains(node)) {
					continue;
				}
				visited.add(node);
				Iterator<Edge> iterator = cg.edgesInto(node);
				while (iterator.hasNext()) {			
					Edge in = iterator.next();
					Log.debug(TAG, "" + in.getSrc().method());
					String prev = in.getSrc().method().getDeclaringClass().toString();
					if (prev.startsWith("dummy")) {
						//break;
						Set<SootMethod> mList = sensUnits.get(stmt);
						mList.add(in.getTgt().method());
						System.out
								.println(target + ": " + in.getTgt().method());

					}
					queue.add(in.getSrc().method());
				}
			}
		}
	}

	public void dfsCG(Stmt stmt) {
		SootMethod target = stmt.getInvokeExpr().getMethod();
		Set<SootMethod> prevs = new HashSet<>();
		cgPaths.put(stmt, prevs);

		SootMethod node = target;
		// TODO now only consider one path
		while (cg.edgesInto(node).hasNext()) {
			Edge edge = cg.edgesInto(node).next();
			node = edge.getSrc().method();
			prevs.add(node);
		}

	}

	public void writeCSV(Map<Stmt, Set<SootMethod>> cgPaths) throws IOException {
		String csv = "./sootOutput/" + Settings.apkName + "_resp.csv";
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

	public void wriCSV(Map<Unit, SootMethod> sensUnits) throws IOException {
		String csv = "./sootOutput/" + Settings.apkName + "_resp.csv";
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
		for (Unit unit : sensUnits.keySet()) {
			List<String> result = new ArrayList<>();
			result.add(unit.toString());
			result.add(sensUnits.get(unit).toString());

			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}
}
