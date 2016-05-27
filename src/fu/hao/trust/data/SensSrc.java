/**  
 * Copyright � 2016 Hao Fu. All rights reserved.
 *
 * @Title: SensSrc.java
 * @Prject: INTEREST
 * @Package: fu.hao.interest.data
 * @Description: TODO
 * @author: hao  
 * @date: Feb 2, 2016 8:18:39 PM
 * @version: V1.0  
 */
package fu.hao.trust.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

import presto.android.xml.AndroidView;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.infoflow.results.ResultSinkInfo;
import soot.jimple.infoflow.results.ResultSourceInfo;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;

/**
 * @ClassName: SensSrc
 * @Description: Data structure to store a sensitive source
 * @author: hao
 * @date: Feb 2, 2016 8:18:39 PM
 */
public class SensSrc {
	ResultSourceInfo src;
	/**
	 * @fieldName: loc
	 * @fieldType: SootMethod
	 * @Description: where the src locates
	 */
	SootMethod loc;
	Map<ResultSinkInfo, SootMethod> sinks = new HashMap<ResultSinkInfo, SootMethod>();
	Set<Unit> uiCalls;
	Map<ResultSinkInfo, Stmt[]> paths = new HashMap<>();
	Set<CondDepSub> conds = new HashSet<>();
	Set<SootMethod> entries = new HashSet<>();
	// trigger views
	Set<AndroidView> triViews = new HashSet<>();
	boolean isRealSrc = true;
	Set<Integer> viewIds = new HashSet<>();
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @Title: isReal
	 * @Description: Is real ResultSourceInfo or just a stmt
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean isReal() {
		return isRealSrc;
	}

	public void addViewID(int id) {
		viewIds.add(id);
	}

	public Set<Integer> getViewIds() {
		return viewIds;
	}

	public void addEntry(SootMethod entry) {
		entries.add(entry);
	}

	public Set<SootMethod> getEntries() {
		return entries;
	}

	public Set<CondDepSub> getConds() {
		return conds;
	}

	public Set<AndroidView> getViews() {
		return triViews;
	}

	public Map<ResultSinkInfo, SootMethod> getSinks() {
		return sinks;
	}

	public void addView(AndroidView view) {
		triViews.add(view);
	}

	public SensSrc(ResultSourceInfo src, SootMethod loc) {
		this.src = src;
		this.loc = loc;
	}

	public SensSrc(Stmt srcStmt, SootMethod loc) {
		src = new ResultSourceInfo(null, srcStmt);
		this.loc = loc;
	}

	public SootMethod getLoc() {
		return loc;
	}

	public void addSink(ResultSinkInfo sink, SootMethod sinkLoc) {
		sinks.put(sink, sinkLoc);
	}

	public void addSinks(InfoflowResults results,
			JimpleBasedInterproceduralCFG icfg) {
		for (ResultSinkInfo sink : results.getResults().keySet()) {
			for (ResultSourceInfo source : results.getResults().get(sink)) {
				if (src.equals(source)) {
					addSink(sink, icfg.getMethodOf(sink.getSink()));
				}
			}
		}
	}

	public void setPath(ResultSinkInfo sink, Stmt[] path) {
		paths.put(sink, path);
	}

	public ResultSourceInfo getSrc() {
		return src;
	}

	public Map<ResultSinkInfo, Stmt[]> getPaths() {
		return paths;
	}

	public String printPaths() {
		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Stmt[] path : paths.values()) {
			try {
				for (int i = 0; i < path.length - 1; i++) {
					sb.append(path[i]);
					sb.append(" -> ");
				}
				sb.append(path[path.length - 1]);
				sb.append("; ");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		sb.append("]");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(src.getSource() + ": ");
		sb.append(loc.getSignature());
		sb.append(sinks);
		sb.append(printPaths());
		sb.append(conds);
		sb.append(entries);
		sb.append(triViews);
		sb.append(viewIds);
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (super.equals(o))
			return true;
		if (o == null) {
			return false;
		}
		if (o instanceof SensSrc) {
			SensSrc si = (SensSrc) o;
			if (si.getSrc().equals(src)) {
				return true;
			}
		}

		return false;
	}

	public void addCondDepSub(CondDepSub cond) {
		conds.add(cond);
	}

}
