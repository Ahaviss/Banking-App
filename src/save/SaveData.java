package save;
import java.io.*;
import java.util.ArrayList;
import database.Account;
import database.Admin;
import utilities.ProjectUtils;

public class SaveData {
    //Loads account data
    @SuppressWarnings("unchecked")
    public static ArrayList<Account> loadAccountData () {
        File file = new File("accountMetadata.ser");
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Account>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //Loads admin data
    @SuppressWarnings("unchecked")
    public static ArrayList<Admin> loadAdminData () {
        File file = new File("adminMetadata.ser");
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Admin>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //Saves account data
    public static void saveAccountData (ArrayList<Account> accounts) {
        File file = new File("accountMetadata.ser");
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file))) {
            ous.writeObject(accounts);
        }
        catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    //Saves admin data
    public static void saveAdminData (ArrayList<Admin> admins) {
        File file = new File("adminMetadata.ser");
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file))) {
            ous.writeObject(admins);
        }
        catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    public static boolean killswitch () {
        while (true) {
            try {
                System.out.println("Are you sure you want to turn on the killswitch?");
                System.out.println("This will delete all data and terminate the program.");
                String option = ProjectUtils.getValidString("Y/N");
                if (option.equalsIgnoreCase("y")) {
                    File delete1 = new File("accountMetadata.ser");
                    File delete2 = new File("adminMetadata.ser");
                    if (delete1.exists()) {
                        boolean del = delete1.delete();
                        if (!del) System.out.println("Delete failed for account data.");
                    }
                    if (delete2.exists()) {
                        boolean del = delete2.delete();
                        if (!del) System.out.println("Delete failed for admin data.");
                    }
                    return true;
                }
                else if (option.equalsIgnoreCase("n")) return false;
                else {
                    System.out.println("Invalid input. Please try again.");
                }
            }
            catch (Exception e) {
                System.out.println("Error implementing killswitch: " + e.getMessage());
            }
        }
    }
}
