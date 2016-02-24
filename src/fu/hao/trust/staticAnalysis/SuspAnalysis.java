package fu.hao.trust.staticAnalysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fu.hao.trust.analysis.Results;
import fu.hao.trust.data.App;
import fu.hao.trust.utils.Log;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.infoflow.source.data.SourceSinkDefinition;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;

public class SuspAnalysis {
	private final String TAG = getClass().toString();

	private static CallGraph cg;
	private static JimpleBasedInterproceduralCFG icfg;

	private static Set<Unit> srcMethods;
	private static Set<Unit> sinkMethods;
	private static Set<SootMethod> suspicous;

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final SuspAnalysis instance = new SuspAnalysis();
	}

	// Get THE instance
	public static final SuspAnalysis v() {
		return SingletonHolder.instance;
	}

	public Set<SootMethod> runAnalysis() {
		App app = App.v();
		cg = app.getCG();
		icfg = app.getICFG();
		srcMethods = new HashSet<>();
		sinkMethods = new HashSet<>();
		suspicous = new HashSet<>();
		
		for (SootClass sootClass : Scene.v().getClasses()) {
			for (SootMethod method : sootClass.getMethods()) {
				try {
					for (Unit unit : icfg.getCallsFromWithin(method)) {
						for (SourceSinkDefinition srcDef : app.getApp()
								.getSources()) {
							if (unit.toString().contains((srcDef.toString()))) {
								srcMethods.add(unit);
								break;
							}
						}

						for (SourceSinkDefinition sinkDef : app.getApp()
								.getSinks()) {
							if (unit.toString().contains((sinkDef.toString()))) {
								sinkMethods.add(unit);
								break;
							}
						}
					}
					
					if (isSuspicous(method)) {
						suspicous.add(method);
						Log.warn(TAG, "found a suspicous method: " + method);
						Results.results.add(method.toString());
					}
				} catch (java.lang.ClassCastException e) {
					e.printStackTrace();
				} catch (java.lang.RuntimeException e2) {
					Log.debug(TAG, e2.getMessage());
				}

			}
		}

		return suspicous;
	}

	public boolean isSuspicous(SootMethod target) {
		Queue<SootMethod> queue = new LinkedList<>();
		Set<SootMethod> visited = new HashSet<>();
		queue.add(target);
		boolean srcFound = false;
		boolean sinkFound = true;
		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				SootMethod node = queue.poll();
				if (visited.contains(node)) {
					continue;
				}
				visited.add(node);
				Iterator<Edge> iterator = cg.edgesOutOf(node);
				while (iterator.hasNext()) {
					Edge out = iterator.next();

					if (out.getTgt().method().toString()
							.contains("android.support.v")) {
						break;
					}
					
					Unit unit = out.srcUnit();
					if (srcMethods.contains(unit)) {
						srcFound = true;
					}
					
					if (sinkMethods.contains(unit)) {
						sinkFound = true;
					}
					
					if (srcFound && sinkFound) {
						return true;
					}
					queue.add(out.getTgt().method());
				}
			}
		}
		
		return false;
	}

}
