package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import warehouse.model.ProductData;
import warehouse.model.Warehouse;
import warehouse.repository.WarehouseRepository;

import java.util.ArrayList;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private WarehouseRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		String[] categories = {"Getränke", "Gemüse", "Elektronik", "Milchprodukte", "Obst", "Gepaeck"};
		String[] cities = {"Wien", "Korneuburg", "Salzburg", "Graz", "Innsbruck"};
		int productCounter = 1;

		for (int w = 0; w < 5; w++) {
			String wID = String.valueOf(w + 1);
			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseID(wID);
			warehouse.setWarehouseName("Lager " + cities[w]);
			warehouse.setWarehouseCity(cities[w]);
			warehouse.setWarehouseCountry("Austria");
			warehouse.setProductData(new ArrayList<>());

			for (int p = 0; p < 60; p++) {
				String pID = "PROD-" + productCounter;
				String pName = "Produkt " + productCounter;
				String category = categories[p % 6];
				double quantity = Math.round((Math.random() * 1000) * 100.0) / 100.0;

				ProductData product = new ProductData(wID, pID, pName, category, quantity);
				warehouse.getProductData().add(product);
				productCounter++;
			}
			repository.save(warehouse);
		}
		System.out.println("5 lager mit 300 produkten erstellt");
	}
}