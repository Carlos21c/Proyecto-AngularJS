package es.unex.pi.model;

public class CartLine {
	private int amount;
	private Dish dish;
	
	public CartLine(Dish dish) {
		amount = 1;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}
	
	public void incAmount() {
		this.amount++;
	}
	
	public void decAmount() {
		this.amount--;
	}

}
