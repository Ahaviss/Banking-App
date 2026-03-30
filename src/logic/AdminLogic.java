package logic;
//Local imports
import database.*;
import utilities.ProjectUtils;
//Java imports
import java.util.Random;
import java.util.ArrayList;
public class AdminLogic {
    //Loops through the admins to find a specific admin
    public static int loopThroughAdmins (ArrayList<Admin> admins, int adminId) {
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getAdminId() == adminId) {
                //Returns the index
                return i;
            }
        }
        //If not found
        return -1;
    }
    public static ArrayList<Admin> deleteAdmins (ArrayList<Admin> admins) {
        //Checks if the admins list is empty
        if (!ProjectUtils.checkArrayList(admins)) {
            System.out.println("No admins available. Please create an admin.");
            return null;
        }
        while (true) {
            try {
                //Asks the number of admins to delete
                int amountOfPeople = ProjectUtils.getValidInt(String.format("Enter the amount of admins you want to delete (%d total admins): ", admins.size()));
                //Validates input
                if (amountOfPeople > admins.size()) {
                    System.out.println("Invalid amount of admins.");
                    continue;
                } else if (amountOfPeople == 0) {
                    System.out.println("No admins deleted.");
                    return admins;
                }
                for (int i = 0; i < amountOfPeople; i++) {
                    while (true) {
                        //Asks for the ID of the admin to delete
                        int adminId = ProjectUtils.getValidInt("Enter the ID of the admin you want to delete: ");
                        int adminIndex = loopThroughAdmins(admins, adminId);
                        //Checks if the admin is found
                        if (adminIndex == -1) {
                            System.out.println("Admin not found.");
                            continue;
                        }
                        //Removes the admin
                        admins.remove(adminIndex);
                        System.out.println("Admin deleted successfully!");
                        break;
                    }
                }
                //Returns the updated admins list
                return admins;
            }
            //Catch invalid input
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
    public static Admin editPassword (Admin admin) {
        //Gets a valid password, sets it to the admin and returns it
        String newPassword = ProjectUtils.getValidPassword("Enter the new password: ");
        admin.setAdminPassword(newPassword);
        return admin;
    }
    public static Admin editAdminName (Admin admin) {
        //Gets a valid name, sets it to the admin and returns it
        String newName = ProjectUtils.getValidString("Enter the new admin name: ");
        admin.setAdminName(newName);
        return admin;
    }
    public static ArrayList<Admin> addAdmins (ArrayList<Admin> admins) {
        //RNG for admin ID
        Random random = new Random();
        while (true) {
            //Asks the number of admins to add
            int amountOfAdmins = ProjectUtils.getValidInt("Enter the amount of admins you want to add: ");
            //Validates input
            if (amountOfAdmins == 0) {
                System.out.println("No admins added.");
                return admins;
            }
            for (int i = 0; i < amountOfAdmins; i++) {
                //Gets the admin's name and password
                String adminName = ProjectUtils.getValidString("Enter admin name:");
                String adminPassword = ProjectUtils.getValidPassword("Enter admin password:");
                //Generates a random admin ID
                int adminId = random.nextInt(9999999 - 1000000 + 1) + 1000000;
                //Checks if the ID is already taken
                while (true) {
                    if (loopThroughAdmins(admins, adminId) == -1) {
                        break;
                    } else {
                        //Increments the ID and repeats the check
                        adminId++;
                        if (adminId > 9999999) {
                            adminId = 1000000;
                        }
                    }
                }
                //Prints the admin ID
                System.out.println("Admin ID: " + adminId);
                //Adds the admin to the admins list
                admins.add(new Admin(adminId, adminName, adminPassword));
            }
            //Returns the updated admins list
            return admins;
        }
    }
}
