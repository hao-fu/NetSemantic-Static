/**  
 * Copyright � 2016 Hao Fu. All rights reserved.
 *
 * @Title: TriggerUI.java
 * @Prject: INTEREST
 * @Package: fu.hao.depTaint
 * @Description: TODO
 * @author: hao  
 * @date: Feb 2, 2016 10:48:20 AM
 * @version: V1.0  
 */
package fu.hao.trust.depTaint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fu.hao.trust.data.SensSrc;
import fu.hao.trust.gator.GUIAnalysis;
import presto.android.xml.AndroidView;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.util.dot.DotGraph;

/**
 * @ClassName: TriggerUI
 * @Description: Find all trigger UIs by search call graph (using icfg instead)
 * @author: hao
 * @date: Feb 2, 2016 10:48:20 AM
 */
public class EntrySolver {
	private static DotGraph dot = new DotGraph("callgraph");

	private final static Logger logger = LoggerFactory
			.getLogger("[GetEntries]");

	public static void getEntries(JimpleBasedInterproceduralCFG icfg,
			Set<SensSrc> sensSrcs, GUIAnalysis ga) {
		File resultFile = new File("./sootOutput/CGTest2.log");
		PrintWriter out = null;

		try {
			out = new PrintWriter(resultFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (SensSrc sensSrc : sensSrcs) {
			Stmt source = sensSrc.getSrc().getSource();
			getEntry(source, icfg, out, sensSrc, ga);
		}

		out.println("CG ends==================");
		out.close();
		String fileNameWithOutExt = FilenameUtils.getName("CG_Entry");
		fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithOutExt);
		String destination = "./sootOutput/" + fileNameWithOutExt;
		@SuppressWarnings("static-access")
		String dotPath = destination + dot.DOT_EXTENSION;
		dot.plot(dotPath);
	}

	public static void getEntry(Stmt source,
			JimpleBasedInterproceduralCFG icfg, PrintWriter out,
			SensSrc sensSrc, GUIAnalysis ga) {
		// Queue<Unit> queue = new LinkedList<>();
		Queue<SootMethod> queue = new LinkedList<>();
		// queue.add(dummyMain);

		SootMethod src = icfg.getMethodOf(source);
		checkView(source, icfg, sensSrc, ga);
		queue.add(src);

		// Set<Unit> visited = new HashSet<>();
		Set<SootMethod> visited = new HashSet<>();

		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				// Unit src = queue.poll();
				SootMethod method = queue.poll();
				if (visited.contains(method)) {
					continue;
				}
				visited.add(method);
				dot.drawNode(method.toString());
				try {
					for (Unit unit : icfg.getCallersOf(method)) {
						logger.debug("u: " + unit);
						// for (Unit unit : icfg.getSuccsOf(src)) {
						SootMethod caller = icfg.getMethodOf(unit);
						queue.add(caller);
						dot.drawEdge(caller.toString(), method.toString());
						if (out != null) {
							out.println(caller + "  -->   " + method);
						}

						if (caller.toString().contains("dummy")) {
							logger.debug(source + ": " + method);
							sensSrc.addEntry(method);
							continue;
						}

						checkView(unit, icfg, sensSrc, ga);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	public static void checkView(Unit unit, JimpleBasedInterproceduralCFG icfg,
			SensSrc sensSrc, GUIAnalysis ga) {
		try {
			if (!((Stmt) unit).containsInvokeExpr()
					|| icfg.getMethodOf(unit).toString()
							.contains("android.support")) {
				return;
			}
			SootMethod caller = ((Stmt) unit).getInvokeExpr().getMethod();
			String superClass = caller.getDeclaringClass().getSuperclass()
					.toString();
			if (superClass.contains("view") || superClass.contains("widget")) {
				logger.debug("ui unit: " + unit);
				Value val = null;
				Stmt targetStmt = null;
				for (ValueBox valueBox : unit.getUseBoxes()) {
					val = valueBox.getValue();
					while (!icfg.getPredsOf(unit).isEmpty()) {
						unit = icfg.getPredsOf(unit).get(0);
						Stmt stmt = (Stmt) unit;
						if (stmt instanceof AssignStmt) {
							// System.out.println(stmt);
							AssignStmt assignStmt = (AssignStmt) stmt;
							if (assignStmt.getLeftOp().equals(val)
									|| assignStmt.getLeftOp().toString()
											.contains(val.toString())
									|| val.toString().contains(
											assignStmt.getLeftOp().toString())) {
								logger.debug("unit: " + unit);
								val = assignStmt.getRightOp();
								targetStmt = (Stmt) unit;
								logger.debug("target: " + targetStmt);
								if (ga.xmlParser != null
										&& targetStmt.toString().contains(
												"findViewById")) {
									logger.info("findview " + targetStmt);

									int id = Integer.parseInt(targetStmt
											.getInvokeExpr().getArg(0)
											.toString());
									logger.info("id: " + id);
									AndroidView view = ga.xmlParser
											.findViewById(id);
									sensSrc.addView(view);
									sensSrc.addViewID(id);
									logger.debug(view.getParent().getText());
									logger.debug(view.getText());
									for (String attribute : view.getAttrs()
											.keySet()) {
										logger.debug(attribute);
										logger.debug(view.getAttrs().get(
												attribute));
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
