package juniors.server.ext.emulator;

import java.io.Serializable;
import juniors.server.core.data.markets.*;

/**
 * 
 * @description Часть сообщения 'Events Data'
 * Содержит всю необходимую для робота информацию
 * о маркете. Данный класс нужен для <code>EventMessage</code>
 * 
 * @author alex
 * 
 * */
public class MarketMessage implements Serializable{

	private static final long serialVersionUID = 321324L;

	private OutcomeMessage[] oucomes;

	public OutcomeMessage[] getOucomes() {
		return oucomes;
	}

	public void setOucomes(OutcomeMessage[] oucomes) {
		this.oucomes = oucomes;
	}
	
	public void initMarket(Market market) {
		Outcome[] outcoms = new Outcome[market.getOutcomeCollection().size()];
		outcoms = market.getOutcomeCollection().toArray(outcoms);
		this.oucomes = new OutcomeMessage[outcoms.length];
		for(int i = 0; i < outcoms.length; ++i) {
			this.oucomes[i].initOutcome(outcoms[i]);
		}
	}
}
