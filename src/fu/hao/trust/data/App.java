/**  
 * Copyright � 2016 Hao Fu. All rights reserved.
 *
 * @Title: App.java
 * @Prject: INTEREST
 * @Package: fu.hao.interest.data
 * @Description: TODO
 * @author: hao  
 * @date: Feb 5, 2016 2:38:36 PM
 * @version: V1.0  
 */
package fu.hao.trust.data;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import fu.hao.trust.FlowDroidMain;
import fu.hao.trust.gator.Config;
import fu.hao.trust.noTaint.SetupApplication;
import soot.Scene;
import soot.jimple.infoflow.InfoflowConfiguration.CallgraphAlgorithm;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.source.AndroidSourceSinkManager.LayoutMatchingMode;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;

/**
 * @ClassName: App
 * @Description: Wrapper of SetupApplication and relevant configs and ops
 * @author: hao
 * @date: Feb 5, 2016 2:38:36 PM
 */
public class App extends FlowDroidMain {
	private CallGraph cg;
	private JimpleBasedInterproceduralCFG icfg;
	private SetupApplication app;
	private final Logger logger = LoggerFactory
			.getLogger(getClass());

	// The nested class to implement singleton
	private static class SingletonHolder {
		private static final App instance = new App();
	}

	// Get THE instance
	public static final App v() {
		SingletonHolder.instance.loadApp();
		return SingletonHolder.instance;
	}

	public App() {
		loadApp();
	}
	
	public SetupApplication getApp() {
		return app;
	}
	
	public void loadApp() {
		System.gc();
		soot.G.reset();
		
		app = new SetupApplication(Config.platformDir, Config.apkPath);
		try {
			// Set configuration object
			// Support layout mode. --Hao
			// config.setLayoutMatchingMode(LayoutMatchingMode.MatchAll);
			config.setLayoutMatchingMode(LayoutMatchingMode.NoMatch);
			// ctx sensitive
			//config.setPathBuilder(PathBuilder.ContextSensitive);
			// --safemode
			//InfoflowAndroidConfiguration.setUseThisChainReduction(false);
			InfoflowAndroidConfiguration
					.setAccessPathLength(Integer.valueOf(3));
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

			if (DEBUG) {
				app.printEntrypoints();
				app.printSinks();
				app.printSources();
			}
			
			app.loadClassesAndCG();
			//app.runInfoflow();
			setCG(Scene.v().getCallGraph());
			logger.info("cgs" + cg.size());
			setICFG(new JimpleBasedInterproceduralCFG());
		} catch (IOException ex) {
			logger.error("Could not read file: " + ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} catch (XmlPullParserException ex) {
			logger.error("Could not read Android manifest file: "
					+ ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public JimpleBasedInterproceduralCFG getICFG() {
		return icfg;
	}

	public void setICFG(JimpleBasedInterproceduralCFG icfg) {
		this.icfg = icfg;
	}

	public CallGraph getCG() {
		return cg;
	}

	public void setCG(CallGraph cg) {
		this.cg = cg;
	}

}
