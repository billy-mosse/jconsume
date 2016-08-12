package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;

public interface LifeTimeOracle {

	HeapPartition getPartition(NewStatement newStmt);

	HeapPartition bind(HeapPartition calleePartition, CallStatement callStmt);

}
