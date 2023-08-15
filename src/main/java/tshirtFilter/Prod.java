package tshirtFilter;
import java.util.Comparator;

public class Prod {
	private String id;
	private String name;
	private String color;
	private String gender;
	private String size;
	private double price;
	private double rating;
	private String available;

	public Prod(String id, String name, String color, String gender, String size, double rating, double price, String available) {
		this.id=id;
		this.name = name;
		this.color=color;
		this.gender = gender;
		this.size = size;
		this.rating = rating;
		this.price = price;
		this.available=available;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getGender() {
		return gender;
	}

	public String getSize() {
		return size;
	}

	public double getPrice() {
		return price;
	}

	public double getRating() {
		return rating;
	}

	public String getAvailable() {
		return available;
	}
	
	public String toString() {
		return "Name:"+name +"("+ id+"), "+ color+", "+"Size:" + size + ", Rating: " + rating + ", Price: " + price;
	}
	
	static class PriceComparator implements Comparator<Prod> {
		public int compare(Prod p1, Prod p2) {
			return Double.compare(p1.getPrice(),p2.getPrice());
		}
	}	

	static class RatingComparator implements Comparator<Prod> {
		public int compare(Prod p1, Prod p2) {
			return Double.compare(p2.getRating(), p1.getRating());
		}
	}

	static class PriceRatingComparator implements Comparator<Prod> {
		public int compare(Prod p1, Prod p2) {
			int priceComparison = Double.compare(p1.getPrice(), p2.getPrice());
			if (priceComparison == 0) {
				return Double.compare(p2.getRating(), p1.getRating());
			}
			return priceComparison;
		}
	}
}
