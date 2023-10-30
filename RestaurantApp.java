import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// NAMA : AJENG FITRIHANDINI
// NIM : 044094149

public class RestaurantApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Map<String, Double> menu = createMenu();
        List<String> order = new ArrayList<>();

        // Input pemesanan
        System.out.println("Menu Restoran:");
        displayMenu(menu);

        while (true) {
            System.out.print("Masukkan nama menu (atau ketik 'selesai' untuk menyelesaikan pemesanan): ");
            String menuItem = input.nextLine();

            if (menuItem.equalsIgnoreCase("selesai")) {
                break;
            }

            if (menu.containsKey(menuItem)) {
                System.out.print("Jumlah yang dipesan: ");
                int quantity = input.nextInt();
                input.nextLine();  // Membuang karakter '\n'

                if (quantity > 0) {
                    order.add(menuItem + " = " + quantity);
                }
            } else {
                System.out.println("Menu tidak valid.");
            }
        }

        // Menghitung total biaya
        double totalCost = calculateTotalCost(order, menu);
        double tax = totalCost * 0.10;
        double serviceCharge = 20000;
        double discount = calculateDiscount(totalCost, menu);

        // Mencetak struk pesanan
        System.out.println("\nStruk Pesanan:");
        for (String item : order) {
            System.out.println(item);
        }

        System.out.println("\nTotal Biaya: Rp. " + totalCost);
        System.out.println("Pajak (10%): Rp. " + tax);
        System.out.println("Biaya Pelayanan: Rp. " + serviceCharge);

        if (discount > 0) {
            System.out.println("Diskon: Rp. " + discount);
        }

        double finalCost = totalCost + tax + serviceCharge - discount;
        System.out.println("Total Biaya Akhir: Rp. " + finalCost);
    }

    private static Map<String, Double> createMenu() {
        Map<String, Double> menu = new HashMap<>();
        menu.put("Nasi Padang", 25000.0);
        menu.put("Mie Goreng", 20000.0);
        menu.put("Es Teh", 5000.0);
        menu.put("Es Jeruk", 6000.0);
 

        return menu;
    }

    private static void displayMenu(Map<String, Double> menu) {
        for (String item : menu.keySet()) {
            System.out.println(item + " - Rp. " + menu.get(item));
        }
    }

    private static double calculateTotalCost(List<String> order, Map<String, Double> menu) {
        double totalCost = 0;
        for (String item : order) {
            String[] parts = item.split(" = ");
            String menuItem = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            totalCost += menu.get(menuItem) * quantity;
        }
        return totalCost;
    }

    private static double calculateDiscount(double totalCost, Map<String, Double> menu) {
        double discount = 0;

        if (totalCost > 100000) {
            discount = totalCost * 0.10;
        }

        // Periksa apakah total biaya melebihi Rp 50000 dan ada menu minuman (misalnya, Es Teh)
        boolean hasBeverage = false;
        for (String item : menu.keySet()) {
            if (item.toLowerCase().contains("minuman")) {
                hasBeverage = true;
                break;
            }
        }

        if (totalCost > 50000 && hasBeverage) {
            // Penawaran beli satu gratis satu untuk salah satu kategori minuman
            discount += menu.get("Es Teh");
        }

        return discount;
    }
}
