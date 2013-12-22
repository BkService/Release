package juniors.server.core.data.coefficientcorrecter;

import static org.junit.Assert.*;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;

import org.apache.tomcat.util.net.AbstractEndpoint.Acceptor;
import org.junit.Test;

public class NormalizeTest{
	final double error = 1e-5;
	
	private boolean equalsWithError(double v1, double v2) {
		return Math.abs(v1 - v2) < error;
	}
	@Test
	public void test() {
		Market m = new Market(1);
		Outcome out = new Outcome(1, 2.7);
		m.addOutcome(out);
		out = new Outcome(2, 5.0);
		m.addOutcome(out);
		out = new Outcome(3, 1.3);
		m.addOutcome(out);
		out = new Outcome(4, 1.7);
		m.addOutcome(out);
		
		CoefficientCorrecter.normalize(m);
		
		double sumP = 0;
		for (Outcome o : m.getOutcomeCollection()) {
			sumP += 1 / o.getCoefficient();
		}
		assertTrue(equalsWithError(sumP, 1));
	}
	@Test
	public void test1() {
		Market m = new Market(1);
		for (int i = 0; i < 100; i++) {
			Outcome out = new Outcome(i + 1, 2.0);
			m.addOutcome(out);
		}
		CoefficientCorrecter.normalize(m);
		double sumP = 0;
		for (Outcome o : m.getOutcomeCollection()) {
			sumP += 1 / o.getCoefficient();
		}
		assertTrue(equalsWithError(sumP, 1));
	}

}
