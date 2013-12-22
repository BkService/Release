package juniors.server.core.data.coefficientcorrecter;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;

public class CoefficientCorrecter {
	public static boolean needToCorrect(Market market, Outcome outcome) {
		double curLoseOutcome = outcome.getSumBets() * outcome.getCoefficient();
		double curLoseMarket = curLoseOutcome - market.getSumBets();
		if (curLoseMarket > 5000.0) {
			if (outcome.getPaySumIfWin() > market.getSumBets()) {
				return true;
			} 
		}
		return false;
	}
	
	public static void normalize(Market market) {
		double sumP = 0;
		for (Outcome out : market.getOutcomeCollection()) {
			sumP += (out.getCoefficient() == 0 ? 0 : 1 / out.getCoefficient());
		}
		for (Outcome out : market.getOutcomeCollection()) {
			out.setCoefficient(out.getCoefficient() * sumP);
		}
	}
	
	
	
	private static void setMaxBets(Market m) {
		for (Outcome out : m.getOutcomeCollection()) {
			if (out.getPaySumIfWin() < m.getSumBets()) {
				out.setMaxBet( (float) Math.max(out.getMaxBet(), m.getSumBets() / ((out.getCoefficient() == 0 ? out.getCoefficient() : 1) * 10)));
			} else {
				if (out.getSumBets() * out.getCoefficient() > m.getSumBets()) {
					out.setInitialMaxBet();
				} 
			}
		}
	}
	
	
	private static double getSmoothingParametr(double outcomeSumBets) {
		return Math.min(0.3, (Math.log(outcomeSumBets) / 40.0));
	}
	
	public static void changeCoefficients(Market m) {
		double sumBets = m.getSumBets();
		for (Outcome out : m.getOutcomeCollection()) {
			double curCoefficent  = out.getCoefficient();
			double estimatedCoefficent = (m.getSumBets() == 0 ? 100 : sumBets / m.getSumBets());
			double c = getSmoothingParametr(m.getSumBets());
			double newCoefficient = c * estimatedCoefficent + (1.0 - c) * curCoefficent;
			out.setCoefficient(newCoefficient);
		}
		normalize(m);
		setMaxBets(m);
	}
	
	public static void correct(Market market, Outcome outcome) {
		if (needToCorrect(market, outcome)) {
			changeCoefficients(market);
		}
	}
}
