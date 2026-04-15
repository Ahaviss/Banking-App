package save;
import java.io.*;
import java.util.ArrayList;
import database.Account;
import database.Admin;
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
            if (!file.exists()) file.createNewFile();
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
            if (!file.exists()) file.createNewFile();
            ous.writeObject(admins);
        }
        catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
