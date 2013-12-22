package juniors.server.ext.emulator;

import java.io.Serializable;

import juniors.server.core.data.markets.Outcome;

/**
 * 
 * @description Часть сообщения 'Events Data'
 * Содержит всю необходимую информацию
 * о исходе. Данный класс нужен для <code>MarketMessage</code>
 * 
 * @author alex
 * 
 * */
public class OutcomeMessage implements Serializable {

	private static final long serialVersionUID = 321323L;

	private double coefficient;
	private int id;

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	
	public void initOutcome(Outcome o) {
		if(o == null)
			return;
		this.coefficient = o.getCoefficient();
		this.setId(o.getOutcomeId());
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}
	
}
