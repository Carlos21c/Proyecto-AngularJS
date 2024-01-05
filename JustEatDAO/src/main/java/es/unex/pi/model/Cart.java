package es.unex.pi.model;

import java.util.HashMap;

public class Cart {
	private float price;
	private HashMap<Long, CartLine> lines;
	
	public Cart() {
		super();
		lines = new HashMap<Long, CartLine>();
		this.price = 0;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public HashMap<Long, CartLine> getLines() {
		return lines;
	}

	public void setLines(HashMap<Long, CartLine> lines) {
		this.lines = lines;
	}

	public void addToCart(Dish dish) {
		if(lines.containsKey(dish.getId())) {
			CartLine line = lines.get(dish.getId());
			line.incAmount();
			line.setDish(dish);
			lines.put(dish.getId(), line);
		}
		else {
			CartLine line = new CartLine(dish);
			line.setDish(dish);
			lines.put(dish.getId(), line);
		}
		price = price+dish.getPrice();
	}
	
	public void deleteFromCart(long id) {
		if(lines.containsKey(id)) {
			CartLine line = lines.get(id);
			if(line.getAmount()>1) {
				line.decAmount();
			}
			else
				lines.remove(id);
			
			Dish dish = line.getDish();
			price = price-dish.getPrice();
		}
		
	}
}
