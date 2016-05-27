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
import fu.hao.trust.data.Results;
import fu.hao.trust.utils.Log;
import fu.hao.trust.utils.Settings;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.infoflow.source.data.SourceSinkDefinition;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;

/**
 * @ClassName: EntryFinder
 * @Description: BFS Call Graph
 * @author: Hao Fu
 * @date: Feb 25, 2016 10:38:04 AM
 */
public class EntryFinder {
	private final String TAG = getClass().toString();

	private static CallGraph cg;
	private static JimpleBasedInterproceduralCFG icfg;

	private static App app;

	private static List<String> PscoutMethod;
	private static Set<SootMethod> entries;

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final EntryFinder instance = new EntryFinder();
	}

	// Get THE instance
	public static final EntryFinder v() {
		return SingletonHolder.instance;
	}

	public Set<SootMethod> runAnalysis() throws IOException {
		app = App.v();
		cg = app.getCG();
		icfg = app.getICFG();
		entries = new HashSet<>();

		try {
			PscoutMethod = FileUtils.readLines(new File(
					"./jellybean_publishedapimapping_parsed.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		SootMethod dummyMain = Scene.v().getMethod(
				"<dummyMainClass: void dummyMainMethod(java.lang.String[])>");

		Set<SootMethod> visited = new HashSet<>();
		for (Unit mainUnit : icfg.getCallsFromWithin(dummyMain)) {
			Stmt stmt = (Stmt) mainUnit;
			if (stmt.containsInvokeExpr()) {
				SootMethod topMethod = stmt.getInvokeExpr().getMethod();

				if (topMethod.getDeclaringClass().getName()
						.startsWith("android")
						|| topMethod.getDeclaringClass().getName()
								.startsWith("java")
						|| topMethod.getDeclaringClass().getName()
								.startsWith("org.apache.http")) {
					continue;
				}

				Queue<SootMethod> queue = new LinkedList<>();
				queue.add(topMethod);

				Log.warn(TAG, "top :: " + topMethod);

				while (!queue.isEmpty()) {
					int len = queue.size();
					for (int i = 0; i < len && !queue.isEmpty(); i++) {
						SootMethod node = queue.poll();
						if (visited.contains(node)) {
							continue;
						}
						visited.add(node);
						Iterator<Edge> iterator = cg.edgesOutOf(node);
						while (iterator.hasNext()) {
							Edge out = iterator.next();
							Unit unit = out.srcUnit();
							SootMethod potential = out.getTgt().method();
							queue.add(potential);

							if (unit == null) {
								continue;
							}

							if (PscoutMethod.contains(potential.toString())
									|| potential.toString().contains(
											"org.apache.http")) {
								// sensitives.put(unit, getPrevNodes(unit));
								Log.warn(TAG, "Target found " + unit + " from "
										+ topMethod);
								entries.add(topMethod);
								queue.clear();
							}
						}
					}
				}
			}
		}

		writeCSV(entries);

		return entries;
	}

	public static void writeCSV(Set<SootMethod> entries) throws IOException {
		String csv = "./sootOutput/" + Settings.apkName + "_entry.csv";
		File csvFile = new File(csv);
		if (!csvFile.exists()) {
			csvFile.createNewFile();
		} else {
			csvFile.delete();
			csvFile.createNewFile();
		}
		CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
		List<String[]> results = new ArrayList<>();

		for (SootMethod method : entries) {
			List<String> result = new ArrayList<>();
			result.add(method.getDeclaringClass() + ":" + method.getName());
			String[] resultArray = (String[]) result.toArray(new String[result
					.size()]);
			results.add(resultArray);
		}

		writer.writeAll(results);
		writer.close();
	}

}
