package tshirtFilter;
import java.util.*;

public class ProductFilter { //for filtering products

	public static List<Prod> filterProducts(List<Prod> productList, String color, String size, String gender) {
		List<Prod> filteredProducts = new ArrayList<Prod>();
		for (Prod product : productList) {
			if (product.getColor().equalsIgnoreCase(color) && product.getSize().equalsIgnoreCase(size) &&
					product.getGender().equalsIgnoreCase(gender) && product.getAvailable().equalsIgnoreCase("y")) {
				filteredProducts.add(product);
			}
		}
		return filteredProducts;
	}

	public static List<Prod> sortProducts(List<Prod> products, String outputPreference) { 	//for sorting filtered products
		List<Prod> sortedProducts = new ArrayList<Prod>(products);
		if (outputPreference.equalsIgnoreCase("Price")) {
			Collections.sort(sortedProducts, new Prod.PriceComparator());
		} else if (outputPreference.equalsIgnoreCase("Rating")) {
			Collections.sort(sortedProducts, new Prod.RatingComparator());
		} else if (outputPreference.equalsIgnoreCase("Both")) {
			Collections.sort(sortedProducts, new Prod.PriceRatingComparator());
		}
		return sortedProducts;
	}
}