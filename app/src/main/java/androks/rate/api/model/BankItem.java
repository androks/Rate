package androks.rate.api.model;

/**
 * androks.rate.api.data
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class BankItem {
	public int bankId;
	private float buy;
	private float buyDiff;
	private float sale;
	private float saleDiff;

	public BankItem(int bankId, float buy, float buyDiff, float sale, float saleDiff) {
		this.bankId = bankId;
		this.buy = buy;
		this.buyDiff = buyDiff;
		this.sale = sale;
		this.saleDiff = saleDiff;
	}

	public int getBankId() {
		return bankId;
	}

	public float getBuy() {
		return buy;
	}

	public float getBuyDiff() {
		return buyDiff;
	}

	public float getSale() {
		return sale;
	}

	public float getSaleDiff() {
		return saleDiff;
	}
}
