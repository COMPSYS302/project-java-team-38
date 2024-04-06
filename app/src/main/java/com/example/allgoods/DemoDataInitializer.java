package com.example.allgoods;


import java.util.Date;
public class DemoDataInitializer {

    CarDatabaseManager dbManager;
    public void initializeDemoData() {
        // Sample users
        User DevenT1 = new User("1", "DevenT1", "Password145!", "DevenT1@example.com");
        User DevenT2 = new User("2", "DevenT2", "Password24!", "DevenT2@example.com");
        User DevenT3 = new User("3", "DevenT3", "Password34!", "DevenT3@example.com");
        User DevenT4 = new User("4", "DevenT4", "Password44!", "DevenT4@example.com");
        User DevenT5 = new User("5", "DevenT5", "Password54!", "DevenT5@example.com");
        User DevenT6 = new User("6", "DevenT6", "Password64!", "DevenT6@example.com");
        User DevenT7 = new User("7", "DevenT7", "Password74!", "DevenT7@example.com");
        User DevenT8 = new User("8", "DevenT8", "Password84!", "DevenT8@example.com");
        User DevenT9 = new User("9", "DevenT9", "Password94!", "DevenT9@example.com");
        User DevenT10 = new User("10", "DevenT10", "Password10!", "DevenT10@example.com");
        User DevenT11 = new User("11", "DevenT11", "Password11!", "DevenT11@example.com");
        User DevenT12 = new User("12", "DevenT12", "Password12!", "DevenT12@example.com");
        User DevenT13 = new User("13", "DevenT13", "Password13!", "DevenT13@example.com");
        User DevenT14 = new User("14", "DevenT14", "Password14!", "DevenT14@example.com");
        User DevenT15 = new User("15", "DevenT15", "Password15!", "DevenT15@example.com");
        User DevenT16 = new User("16", "DevenT16", "Password16!", "DevenT16@example.com");
        User DevenT17 = new User("17", "DevenT17", "Password17!", "DevenT17@example.com");
        User DevenT18 = new User("18", "DevenT18", "Password18!", "DevenT18@example.com");
        User DevenT19 = new User("19", "DevenT19", "Password19!", "DevenT19@example.com");

        Car car1 = new Car(DevenT1, "Toyota", "Camry", 2018, 25000);
        Car car2 = new Car(DevenT2, "Honda", "Civic", 2017, 30000);
        Car car3 = new Car(DevenT3, "Ford", "Fusion", 2019, 15000);
        Car car4 = new Car(DevenT4, "Nissan", "Altima", 2016, 28000);
        Car car5 = new Car(DevenT5, "Chevrolet", "Malibu", 2018, 22000);
        Car car6 = new Car(DevenT6, "Hyundai", "Sonata", 2017, 18000);
        Car car7 = new Car(DevenT7, "Kia", "Optima", 2018, 21000);
        Car car8 = new Car(DevenT8, "Subaru", "Impreza", 2019, 20000);
        Car car9 = new Car(DevenT9, "Mazda", "6", 2016, 17000);
        Car car10 = new Car(DevenT10, "Volkswagen", "Jetta", 2018, 16000);
        Car car11 = new Car(DevenT11, "BMW", "3 Series", 2017, 27000);
        Car car12 = new Car(DevenT12, "Mercedes", "C-Class", 2019, 30000);
        Car car13 = new Car(DevenT13, "Audi", "A4", 2018, 31000);
        Car car14 = new Car(DevenT14, "Lexus", "IS", 2017, 29000);
        Car car15 = new Car(DevenT15, "Acura", "TLX", 2019, 25000);
        Car car16 = new Car(DevenT16, "Infiniti", "Q50", 2018, 23943);
        Car car17 = new Car(DevenT17, "Cadillac", "ATS", 2017, 24000);
        Car car18 = new Car(DevenT18, "Jaguar", "XE", 2019, 32000);
        Car car19 = new Car(DevenT19, "Genesis", "G70", 2018, 33000);
                // Current date for demo
        Date currentDate = new Date();

        // Sample car listings
       /* CarListing listing1 = new CarListing(1, car1, 15000.00, currentDate);
        CarListing listing2 = new CarListing(2, car2, 14000.00, currentDate);
        CarListing listing3 = new CarListing(3, car3, 16000.00, currentDate);
        CarListing listing4 = new CarListing(4, car4, 13500.00, currentDate);
        CarListing listing5 = new CarListing(5, car5, 14800.00, currentDate);
        CarListing listing6 = new CarListing(6, car6, 16500.00, currentDate);
        CarListing listing7 = new CarListing(7, car7, 17500.00, currentDate);
        CarListing listing8 = new CarListing(8, car8, 18000.00, currentDate);
        CarListing listing9 = new CarListing(9, car9, 15500.00, currentDate);
        CarListing listing10 = new CarListing(10, car10, 17000.00, currentDate);
        CarListing listing11 = new CarListing(11, car11, 26500.00, currentDate);
        CarListing listing12 = new CarListing(12, car12, 29000.00, currentDate);
        CarListing listing13 = new CarListing(13, car13, 30500.00, currentDate);
        CarListing listing14 = new CarListing(14, car14, 27500.00, currentDate);
        CarListing listing15 = new CarListing(15, car15, 25500.00, currentDate);
        CarListing listing16 = new CarListing(16, car16, 24500.00, currentDate);
        CarListing listing17 = new CarListing(17, car17, 23500.00, currentDate);
        CarListing listing18 = new CarListing(18, car18, 31500.00, currentDate);
        CarListing listing19 = new CarListing(19, car19, 32500.00, currentDate);


        // Assuming CarDatabaseManager has a method to add listings
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT1, listing1);
        dbManager.addListing(DevenT2, listing2);
        dbManager.addListing(DevenT3, listing3);
        dbManager.addListing(DevenT4, listing4);
        dbManager.addListing(DevenT5, listing5);
        dbManager.addListing(DevenT6, listing6);
        dbManager.addListing(DevenT7, listing7);
        dbManager.addListing(DevenT8, listing8);
        dbManager.addListing(DevenT9, listing9);
        dbManager.addListing(DevenT10, listing10);
        dbManager.addListing(DevenT11, listing11);
        dbManager.addListing(DevenT12, listing12);
        dbManager.addListing(DevenT13, listing13);
        dbManager.addListing(DevenT14, listing14);
        dbManager.addListing(DevenT15, listing15);
        dbManager.addListing(DevenT16, listing16);
        dbManager.addListing(DevenT17, listing17);
        dbManager.addListing(DevenT18, listing18);
        dbManager.addListing(DevenT19, listing19);*/


    }
}
