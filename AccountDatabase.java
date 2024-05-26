package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class AccountDatabase {
    public int index = 0;
	private Map<String, String> userData;
	public List<String> usernameList = new ArrayList<>();
	public List<String> passwordList = new ArrayList<>();
    public List<String> nameList = new ArrayList<>();

    public AccountDatabase() {
        userData = new HashMap<>();
        usernameList.add(0, "Owner123");
        passwordList.add(0, "ownerpass");
        nameList.add(0, "Owner");
        usernameList.add(1, "Manager123");
        passwordList.add(1, "managerpass");
        nameList.add(1, "Manager");
        
        userData.put(usernameList.get(0), passwordList.get(0));
    }

    //to register a new user
    public void registerUser(List<String> usernameList, List<String> passwordList) {
    	for (int i = 0; i < usernameList.size(); i++) {
            userData.put(usernameList.get(i), passwordList.get(i));
        }
    	
    	
    	for (Entry<String, String> entry : userData.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }
    
    

    //to login a user
    public boolean loginUser(String username, String password) {
        for (int i = 0; i < usernameList.size(); i++) {
        	if (username.equals(usernameList.get(i)) && password.equals(passwordList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void indexer(String username, String password) {
        for (int i = 0; i < usernameList.size(); i++) {
        	if (username.equals(usernameList.get(i)) && password.equals(passwordList.get(i))) {
                index = i;
            }
        }
    }
    
    public ArrayList<String> getUsernames() {
        return (ArrayList<String>) usernameList;
    }

    public ArrayList<String> getPasswords() {
        return (ArrayList<String>) passwordList;
    }

    public ArrayList<String> getNames() {
        return (ArrayList<String>) nameList;
    }
    public void setUsernames(List<String> usernameList) {
        this.usernameList = usernameList;
    }
    public void setPasswordList(List<String> passwordList) {
        this.passwordList = passwordList;
    }
    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }
    

    
}
