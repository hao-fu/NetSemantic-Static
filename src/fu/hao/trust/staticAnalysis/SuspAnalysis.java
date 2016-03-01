package fu.hao.trust.staticAnalysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import fu.hao.trust.analysis.Results;
import fu.hao.trust.data.App;
import fu.hao.trust.utils.Log;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.IfStmt;
import soot.jimple.Stmt;
import soot.jimple.infoflow.source.data.SourceSinkDefinition;
import soot.jimple.internal.ConditionExprBox;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;

/**
 * @ClassName: SuspAnalysisCG
 * @Description: BFS Call Graph
 * @author: Hao Fu
 * @date: Feb 25, 2016 10:38:04 AM
 */
public class SuspAnalysis {
	private final String TAG = getClass().toString();

	private static CallGraph cg;
	private static JimpleBasedInterproceduralCFG icfg;

	private static Set<Unit> srcMethods;
	private static Set<Unit> sinkMethods;
	private static Set<SootMethod> suspicous;
	private static Map<Unit, Map<Unit, UnitNode>> sensitives;

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final SuspAnalysis instance = new SuspAnalysis();
	}

	// Get THE instance
	public static final SuspAnalysis v() {
		return SingletonHolder.instance;
	}
	
	class UnitNode {
		IfStmt stmt;
		Unit prev;
		Unit next;
		
		boolean forward = true;
		
		UnitNode(IfStmt stmt, Unit prev, Unit next) {
			this.stmt = stmt;
			this.prev = prev;
			this.next = next;
			Log.warn(TAG, "stmt " + stmt + " " + stmt.getTarget());
			if (stmt.getTarget().equals(prev)) {
				forward = false;
			}
			ConditionExprBox cond = (ConditionExprBox)stmt.getConditionBox();
			Log.warn(TAG, "" + cond + " size " + cond.getValue().getUseBoxes().size());
			if (prev != null) {
				Log.warn(TAG, "prev " + prev);
				Log.warn(TAG, "next " + next);
				Log.warn(TAG, "forward: " + forward);
			}
		}
		
	}

	public Set<SootMethod> runAnalysis() {
		App app = App.v();
		cg = app.getCG();
		icfg = app.getICFG();
		srcMethods = new HashSet<>();
		sinkMethods = new HashSet<>();
		suspicous = new HashSet<>();
		sensitives = new HashMap<>();
		
		for (SootClass sootClass : Scene.v().getClasses()) {
			for (SootMethod method : sootClass.getMethods()) {
				try {
					for (Unit unit : icfg.getCallsFromWithin(method)) {
						for (SourceSinkDefinition srcDef : app.getApp()
								.getSources()) {
							if (unit.toString().contains((srcDef.toString()))) {
								srcMethods.add(unit);
								//sensitives.put(unit, getPrevNodes(unit));
								break;
							}
						}

						for (SourceSinkDefinition sinkDef : app.getApp()
								.getSinks()) {
							if (unit.toString().contains((sinkDef.toString()))) {
								sinkMethods.add(unit);
								//sensitives.put(unit, getPrevNodes(unit));
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
				} catch (java.lang.RuntimeException e) {
					Log.debug(TAG, e.getMessage());
				}

			}
		}

		return suspicous;
	}
	
	public Map<Unit, UnitNode> getPrevNodes(Unit sens) {
		Map<Unit, UnitNode> res = new HashMap<>();
		Queue<Unit> queue = new LinkedList<>();
		Set<Unit> visited = new HashSet<>();
		queue.add(sens);
		
		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				Unit unit = queue.poll();
				if (visited.contains(unit)) {
					continue;
				}
				visited.add(unit);
				
				for(Unit prev : icfg.getPredsOf(unit)) {
					queue.add(prev);
					if ((Stmt)prev instanceof IfStmt) {
						if (!res.containsKey(prev)) {
							//res.get(prev).setForward((IfStmt)prev, unit);
						//} else {
							res.put(prev, new UnitNode((IfStmt)prev, unit, icfg.getPredsOf(prev).get(0)));
						}
						Log.warn(TAG, sens + ", unit: " + prev);
					}
					Log.warn(TAG, "s " + icfg.getPredsOf(unit).size() + " " + sens + ", unit: " + prev);
					Log.warn(TAG, "s " + icfg.getPredsOf(prev).size() + " " + sens + ", unit: " + prev);
				}
			}
		}
		
		return res;
	}

	public boolean isSuspicous(SootMethod target) {
		Queue<SootMethod> queue = new LinkedList<>();
		Set<SootMethod> visited = new HashSet<>();
		queue.add(target);
		boolean srcFound = false;
		boolean sinkFound = false;
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
						Log.debug(TAG, "src found " + unit);
						srcFound = true;
					}
					
					if (sinkMethods.contains(unit)) {
						Log.debug(TAG, "sink found " + unit);
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
