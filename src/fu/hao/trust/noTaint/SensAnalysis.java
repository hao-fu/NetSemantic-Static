package fu.hao.trust.noTaint;

/**  
 * Copyright 漏 2016 Hao Fu. All rights reserved.
 *
 * @Title: SensAnalysis.java
 * @Prject: INTEREST
 * @Package: 
 * @author: hao  
 * @date: Jan 26, 2016 10:58:24 AM
 * @version: V1.0  
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;








import fu.hao.trust.FlowDroidMain;
import fu.hao.trust.data.App;
import fu.hao.trust.data.SensSrc;
import fu.hao.trust.depTaint.EntrySolver;
import fu.hao.trust.gator.Config;
import fu.hao.trust.gator.GUIAnalysis;
import soot.Body;
import soot.G;
import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.infoflow.InfoflowConfiguration.CallgraphAlgorithm;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.source.AndroidSourceSinkManager.LayoutMatchingMode;
import soot.jimple.infoflow.cfg.LibraryClassPatcher;
import soot.jimple.infoflow.data.pathBuilders.DefaultPathBuilderFactory.PathBuilder;
import soot.jimple.infoflow.handlers.PreAnalysisHandler;
import soot.jimple.infoflow.source.data.SourceSinkDefinition;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.options.Options;
import soot.util.dot.DotGraph;
import soot.util.queue.QueueReader;

/**
 * @ClassName: SensAnalysis
 * @Description: Search the call graph to get sensitive invocations with their
 *               entries, notice the call graph may still not be complete.
 * @author: hao
 * @date: Jan 26, 2016 10:58:24 AM
 */
public class SensAnalysis extends FlowDroidMain {
	SetupApplication app;
	private static CallGraph cg;
	private static JimpleBasedInterproceduralCFG icfg;
	// sensitive permission related
	@SuppressWarnings("unused")
	private static List<String> PscoutMethod;
	public static Map<SootMethod, List<SootMethod>> sensEntries = new HashMap<>();
	private static DotGraph dot = new DotGraph("callgraph");
	private static String apkPath = Config.apkPath;
	private static String platformDir = Config.platformDir;
	private final static Logger logger = LoggerFactory
			.getLogger(SensAnalysis.class);

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final SensAnalysis instance = new SensAnalysis();
	}

	// Get THE instance
	public static final SensAnalysis v() {
		return SingletonHolder.instance;
	}

	/*
	 * get all onCreate()
	 */
	public static List<SootMethod> getAllOnCreate() {
		QueueReader<Edge> edges = cg.listener();
		List<SootMethod> res = new ArrayList<>();
		while (edges.hasNext()) {
			Edge edge = (Edge) edges.next();
			SootMethod target = (SootMethod) edge.getTgt();
			String tgtMethod = target.toString();

			// onCreate()
			if (tgtMethod.contains("onCreate")) {
				res.add(target);
			}
		}

		return res;
	}

	public static void getEntries(JimpleBasedInterproceduralCFG icfg) {
		Queue<Unit> queue = new LinkedList<>();
		// Queue<SootMethod> queue = new LinkedList<>();
		SootMethod dummyMain = Scene.v().getEntryPoints().get(0);
		// queue.add(dummyMain);

		queue.addAll(dummyMain.retrieveActiveBody().getUnits());

		/*
		 * for (Unit unit : icfg.getCallsFromWithin(dummyMain)) {
		 * System.out.println("haha: " + unit); queue.add(unit); }
		 */

		Set<Unit> visited = new HashSet<>();
		// Set<SootMethod> visited = new HashSet<>();

		File resultFile = new File("./sootOutput/CGTest.log");
		PrintWriter out = null;

		try {
			out = new PrintWriter(resultFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		out.println("CG begins==================");
		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				Unit src = queue.poll();
				// SootMethod src = queue.poll();
				if (visited.contains(src)) {
					continue;
				}
				visited.add(src);
				dot.drawNode(src.toString());
				try {
					// for (Unit unit: icfg.getCallsFromWithin(src)) {
					for (Unit unit : icfg.getSuccsOf(src)) {
						queue.add(unit);
						System.out.println(unit);
						Stmt stmt = (Stmt) unit;
						if (stmt.containsInvokeExpr()) {

							SootMethod target = stmt.getInvokeExpr()
									.getMethod();
							queue.addAll(target.retrieveActiveBody().getUnits());
							dot.drawEdge(src.toString(), target.toString());
							out.println(src + "  -->   " + target);
							// queue.add(target);
						}
					}

				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}

		out.println("CG ends==================");
		out.close();
		System.out.println(cg.size());
		String fileNameWithOutExt = FilenameUtils.getName(apkPath);
		fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithOutExt);
		String destination = "./sootOutput/" + fileNameWithOutExt;
		@SuppressWarnings("static-access")
		String dotPath = destination + dot.DOT_EXTENSION;
		dot.plot(dotPath);
	}

	/*
	 * Traverse over Call Graph by visit edges one by one check whether is a
	 * sensitive permission related API call. if is, get the entry
	 */
	@SuppressWarnings("static-access")
	public void getEntries(CallGraph cg) {
		QueueReader<Edge> edges = cg.listener();
		Set<String> visited = new HashSet<>();

		File resultFile = new File("./sootOutput/CGTest.log");
		PrintWriter out = null;
		try {
			out = new PrintWriter(resultFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		out.println("CG begins==================");
		// iterate over edges of call graph
		while (edges.hasNext()) {
			Edge edge = (Edge) edges.next();
			SootMethod target = (SootMethod) edge.getTgt();
			SootMethod src = edge.getSrc().method();
			if (!visited.contains(src.toString())) {
				dot.drawNode(src.toString());
				visited.add(src.toString());
			}
			dot.drawEdge(src.toString(), target.toString());
			out.println(src + "  -->   " + target);
			
			Set<SensSrc> sensSrcs = new HashSet<>();
			for (SourceSinkDefinition srcDef : app.getSources()) {
				  if (srcDef.toString().contains(target.toString())) {
					  
					  SensSrc sensSrc = new SensSrc((Stmt) null, null);
					  bfsCG(target, sensSrc, null);
					  sensSrcs.add(sensSrc);
					  logger.info("srcs " + sensSrc);
					  
				  }
			  } 
			
		}

		out.println("CG ends==================");
		out.close();
		System.out.println(cg.size());
		String fileNameWithOutExt = FilenameUtils.getName(apkPath);
		fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithOutExt);
		String destination = "./sootOutput/" + fileNameWithOutExt;
		String dotPath = destination + dot.DOT_EXTENSION;
		dot.plot(dotPath);
	}

	/*
	 * bfs the CG to get the entries of all sensitive methods
	 */
	public static void bfsCG(SootMethod target, SensSrc sensSrc, GUIAnalysis ga) {
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
					
					if (in.getTgt().method().toString()
							.contains("android.support.v")) {
						break;
					}
					Unit unit = in.srcUnit();
					
					EntrySolver.checkView(unit, icfg, sensSrc, ga);
					if (in.getSrc().method().toString().contains("dummy")) {
						if (!sensEntries.containsKey(target)) {
							sensEntries
									.put(target, new ArrayList<SootMethod>());
						}
						List<SootMethod> mList = sensEntries.get(target);
						mList.add(in.getTgt().method());
						System.out
								.println(target + ": " + in.getTgt().method());
					}
					queue.add(in.getSrc().method());
				}
			}
		}
	}

	/**
	 * @Title: completeCG
	 * @Description: Complete cg by considering run()
	 * @param
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unused")
	private static void completeCG() {
		QueueReader<Edge> edges = cg.listener();
		while (edges.hasNext()) {
			Edge edge = (Edge) edges.next();
			SootMethod target = (SootMethod) edge.getTgt();
			MethodOrMethodContext src = edge.getSrc();
			if (target.toString().contains("Thread: void run()")) {
				SootClass tgtClass = src.method().getDeclaringClass();
				SootMethod run = null;
				try {
					run = tgtClass.getMethodByName("run");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				Edge newEdge = new Edge(src.method(), edge.srcStmt(), run);
				cg.addEdge(newEdge);
				Body body = run.retrieveActiveBody();
				for (Unit callee : icfg.getCallsFromWithin(run)) {
					// print(callee.toString());
					Stmt stmt = (Stmt) callee;
					newEdge = new Edge(run, stmt, stmt.getInvokeExpr()
							.getMethod());
					cg.addEdge(newEdge);
				}
			}
		}
	}
	
	
	public void deriveFlowDroidCG() {
		try {
			final long beforeRun = System.nanoTime();
			app = new SetupApplication(platformDir, apkPath);

			// Set configuration object
			// Support layout mode. --Hao
			// config.setLayoutMatchingMode(LayoutMatchingMode.MatchAll);
			config.setLayoutMatchingMode(LayoutMatchingMode.NoMatch);
			// ctx sensitive
			config.setPathBuilder(PathBuilder.ContextSensitive);
			// --safemode
			InfoflowAndroidConfiguration.setUseThisChainReduction(false);
			InfoflowAndroidConfiguration
					.setAccessPathLength(Integer.valueOf(2));
			config.setLogSourcesAndSinks(true);
			
			config.setCallgraphAlgorithm(CallgraphAlgorithm.CHA);
			
			
			app.setConfig(config);

			final ITaintPropagationWrapper taintWrapper;
			if (noTaintWrapper)
				taintWrapper = null;
			else if (summaryPath != null && !summaryPath.isEmpty()) {
				System.out.println("Using the StubDroid taint wrapper");
				taintWrapper = createLibrarySummaryTW();
				if (taintWrapper == null) {
					System.err.println("Could not initialize StubDroid");
					return;
				}
			} else {
				final EasyTaintWrapper easyTaintWrapper;
				if (new File("../soot-infoflow/EasyTaintWrapperSource.txt")
						.exists())
					easyTaintWrapper = new EasyTaintWrapper(
							"../soot-infoflow/EasyTaintWrapperSource.txt");
				else
					easyTaintWrapper = new EasyTaintWrapper(
							"EasyTaintWrapperSource.txt");
				easyTaintWrapper.setAggressiveMode(aggressiveTaintWrapper);
				taintWrapper = easyTaintWrapper;
			}
			
			app.setTaintWrapper(taintWrapper);
			app.calculateSourcesSinksEntrypoints("SourcesAndSinks.txt");

			System.out.println("Running data flow analysis...");
			app.runInfoflow();
			System.out.println("Analysis has run for "
					+ (System.nanoTime() - beforeRun) / 1E9 + " seconds");
			cg = Scene.v().getCallGraph();
			icfg = new JimpleBasedInterproceduralCFG();

		} catch (IOException ex) {
			System.err.println("Could not read file: " + ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} catch (XmlPullParserException ex) {
			System.err.println("Could not read Android manifest file: "
					+ ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public Set<SensSrc> runAnalysis(GUIAnalysis ga) throws FileNotFoundException {	
		//deriveFlowDroidCG();
		//getCG2();
		App app = App.v();
		cg = app.getCG();
		icfg = app.getICFG();
		File resultFile = new File("./sootOutput/CGTest2.log");
		PrintWriter out = new PrintWriter(resultFile);
		Set<SensSrc> sensSrcs = new HashSet<>();
		for (SootClass sootClass : Scene.v().getClasses()) {
			 for (SootMethod method : sootClass.getMethods()) {
				 try {
					  for (Unit unit : icfg.getCallsFromWithin(method)) {
						  for (SourceSinkDefinition srcDef : app.getApp().getSources()) {
							  if (unit.toString().contains((srcDef.toString()))) {
								  SensSrc sensSrc = new SensSrc((Stmt) unit, icfg.getMethodOf(unit));
								  EntrySolver.getEntry((Stmt) unit, icfg, 
											out, sensSrc, ga);
								  sensSrcs.add(sensSrc);
								  logger.info("srcs " + sensSrc);
							  }
						  } 
					  }
				 } catch (java.lang.ClassCastException e) {
					 e.printStackTrace();
				 } catch (java.lang.RuntimeException e2) {
					 logger.debug(e2.getMessage());
				 }
				 
			 }		
		}
		
		return sensSrcs;
	}

	public void getCG2() {
		app = new SetupApplication(platformDir, apkPath);
		try {
			app.calculateSourcesSinksEntrypoints("./SourcesAndSinks.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		// setup
		G.reset();
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_process_dir(Collections.singletonList(apkPath));
		Options.v().set_android_jars(platformDir);
		Options.v().set_whole_program(true);
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_output_format(13);
		// This statement is necessary to make XMLParser in gator to run
		// correcly
		Scene.v().addBasicClass("android.widget.RelativeLayout",
				SootClass.SIGNATURES);
		Scene.v().loadNecessaryClasses();

		// 鍒涘缓dummy main骞朵綔涓篴pp鐨刴ain鍑芥暟(鍒嗭拷?鍏ワ拷?锟�), 锟�?锟紾uiAnalysis鎵�闇�
		SootMethod entryPoint = app.getEntryPointCreator().createDummyMain();
		Options.v().set_main_class(entryPoint.getSignature());
		Scene.v().setEntryPoints(Collections.singletonList(entryPoint));
		
		try {
			PscoutMethod = FileUtils.readLines(new File(
			// "./jellybean_publishedapimapping_parsed.txt"));
					"./sensitive_srcs.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Transform CGtransform = new Transform("wjtp.checkCG",
				new SceneTransformer() {
					@Override
					protected void internalTransform(String phaseName,
							Map<String, String> options) {
						cg = Scene.v().getCallGraph();
						icfg = new JimpleBasedInterproceduralCFG();
						//completeCG();
						
					}
				});

		PackManager.v().getPack("wjtp").add(CGtransform);
		try {
			logger.info("RUN PACKSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS!");
			PackManager.v().runPacks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collection<? extends PreAnalysisHandler> preProcessors = Collections.emptyList();
		// Run the preprocessors
        for (PreAnalysisHandler tr : preProcessors)
            tr.onBeforeCallgraphConstruction();
        
        // Patch the system libraries we need for callgraph construction
        LibraryClassPatcher patcher = new LibraryClassPatcher();
        patcher.patchLibraries();
		
        // To cope with broken APK files, we convert all classes that are still
        // dangling after resolution into phantoms
        for (SootClass sc : Scene.v().getClasses())
        	if (sc.resolvingLevel() == SootClass.DANGLING) {
        		sc.setResolvingLevel(SootClass.BODIES);
        		sc.setPhantomClass();
        	}
        
     // Run the preprocessors
        for (PreAnalysisHandler tr : preProcessors)
            tr.onAfterCallgraphConstruction();
		
	}
	
	public void run2() {
		try {
			PscoutMethod = FileUtils.readLines(new File(
			// "./jellybean_publishedapimapping_parsed.txt"));
					"./sensitive_srcs.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		deriveFlowDroidCG();
		getEntries(cg);
	}

	public static void main(String[] args) {
		apkPath = args[0];
		platformDir = args[1];
		SensAnalysis analysis = SensAnalysis.v();
		try {
			//analysis.runAnalysis(null);
			analysis.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CallGraph getCG() {
		return cg;
	}
	
	public void run() {
		getCG2();
		getEntries(cg);
	}
	
}