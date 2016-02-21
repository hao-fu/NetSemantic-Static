/**  
 * Copyright � 2016 Hao Fu. All rights reserved.
 *
 * @Title: CondDepSub.java
 * @Prject: INTEREST
 * @Package: fu.hao.interest.data
 * @Description: TODO
 * @author: hao  
 * @date: Feb 2, 2016 9:37:32 PM
 * @version: V1.0  
 */
package fu.hao.trust.data;

import java.util.ArrayList;
import java.util.List;

import soot.SootMethod;
import soot.jimple.Stmt;

/**
 * @ClassName: CondDepSub
 * @Description: data structure for subject of the condDep relation
 * @author: hao
 * @date: Feb 2, 2016 9:37:32 PM
 */
public class CondDepSub {
	Stmt stmt;

	SootMethod src;
	SootMethod tgt;
	String tag;
	List<Context> contexts = new ArrayList<>();

	public CondDepSub(Stmt stmt, SootMethod src, SootMethod tgt, String tag) {
		this.stmt = stmt;
		this.src = src;
		this.tgt = tgt;
		this.tag = tag;
	}

	public CondDepSub(Stmt stmt, SootMethod src, SootMethod tgt) {
		this.stmt = stmt;
		this.src = src;
		this.tgt = tgt;
	}

	public List<Context> getContexts() {
		return contexts;
	}

	public void addContext(Context ctx) {
		contexts.add(ctx);
	}

	public void setContexts(List<Context> contexts) {
		this.contexts = contexts;
	}

	public SootMethod getSrc() {
		return src;
	}

	public void setSrc(SootMethod src) {
		this.src = src;
	}

	public SootMethod getTgt() {
		return tgt;
	}

	public void setTgt(SootMethod tgt) {
		this.tgt = tgt;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Stmt getStmt() {
		return stmt;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (!(obj instanceof CondDepSub) || obj == null)
			return false;
		CondDepSub cond = (CondDepSub) obj;
		if (cond.getStmt().equals(stmt)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return stmt.toString();
	}
}
