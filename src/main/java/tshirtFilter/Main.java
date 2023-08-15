package tshirtFilter;
import java.util.*;

import repository.*;
public class Main {
	public static void main(String[]args) {

		List<String[]> csvData = new ArrayList<>();
		CSVReaderThread csvReaderThread = new CSVReaderThread(csvData); 
		csvReaderThread.start();
		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);

		while (true) {
			
			List <Prod> productList= new ArrayList<>();
			String color ="",size="",gender="", outputPreference="";

			do {
				System.out.println("enter color:");
				color= sc.nextLine();
			}
			while (!color.matches("[a-zA-Z]+"));
			do {
				System.out.println("enter size(S/M/L/XL/XXL):");
				size = sc.nextLine();
			}
			while(!size.matches("[smlxlSMLXL]{1,2}|xxl|XXL"));
			do {
				System.out.println("enter gender(M/F):");
				gender = sc.nextLine();
			}
			while(!gender.matches("[mMfF]{1}"));
			
			System.out.println("enter Preference(Price/Rating/Both):");
			outputPreference = sc.nextLine();
			
			List<String[]> data;
			synchronized (csvData) {
				data = new ArrayList<>(csvData);
			}

			for (String[] strArr :data) {
				String id=strArr[0];
				String name=strArr[1];
				String color1=strArr[2];
				String gender1=strArr[3];
				String size1=strArr[4];
				double price=Double.parseDouble(strArr[5]);
				double rating=Double.parseDouble(strArr[6]);
				String available=strArr[7];
				Prod prod= new Prod(id,name,color1,gender1,size1,rating,price,available);
				productList.add(prod);
			}
			
			List<Prod> filteredProducts = ProductFilter.filterProducts(productList, color, size, gender);
			List<Prod> sortedProducts = ProductFilter.sortProducts(filteredProducts, outputPreference);

			if(!sortedProducts.isEmpty()) {
				for (Prod product : sortedProducts) {
					System.out.println(product.toString());
				}
				System.out.println("Continue Searching more tshirts?(Y/N)");
				String option=sc.nextLine();
				if(option.equalsIgnoreCase("n")) {
					System.out.println("*******END*****");
					System.exit(0);
				}
			}		

			else {
				System.out.println("****Tshirt not available.Please try again****");
				System.out.println("Continue Searching more tshirts?(Y/N)");
				String option=sc.nextLine();
				if(option.equalsIgnoreCase("n")) {
					System.out.println("*******END*****");
					System.exit(0);
				}
			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
