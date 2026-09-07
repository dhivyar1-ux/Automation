package utils;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
	private DataStore() {
        // prevent object creation
    }
	
	private static Map<String, Object> data = new HashMap<>();

    public static void put(String key, Object value) {
        data.put(key, value);
    }

    public static Object get(String key) {
        return data.get(key);
    }

    public static void clear() {
        data.clear();
    }
    
	public static String loggedInUsername;
	public static String loggedInPassword;
	
	public static String s_name;
    public static String s_contactNumber;
    public static String s_location;
    public static String s_comments;
    public static String s_courseLevel;
    public static String s_category;
    public static String s_collegeName;
    public static String s_collegeStartYear;
    public static String s_collegeEndYear;
    public static String s_department;
    public static String s_address;
    public static String s_parentOrGuardianName;
    public static String s_parentOrGuardianContact;
    public static String s_currentCity;
    
    public static String a_name;
    public static String a_skills;
    public static String a_comments;
    public static String a_targets;
    public static String a_incentives;
    public static String a_type;
    
    public static String f_name;
    public static String f_skills;
    public static String f_comments;
    
    public static String sa_username;
    public static String sa_name;
    public static String sa_department;
    public static String sa_comments;
    
    public static String cc_name;
    public static String cc_login;
    public static String cc_password;
    public static String cc_email;
    public static String cc_comments;
    public static String cc_skills;
    
    public static String ug_groupname;
    public static String ug_description;
    public static String ug_studentslist;
    
    public static String c_type;
    public static String c_name;
    public static String c_duration;
    public static String c_fees;
    public static String c_placementAssistance;
    public static String c_planApproved;
    public static String c_coins;
    public static String c_status;
    public static String c_comments;
    public static String c_courseLevel;
    public static String c_category;
    public static String c_validity;
    public static String c_uploadImageFile;
    public static String c_mcqTest;        
	public static String c_programmingTest;
	public static String c_labConfig;
	public static String c_feedbackTemplate;
	public static String c_courseDescription;
    
    public static String gamesListName;
    public static String gamesListType;
    public static String gamesListImage; 
    public static String gamesListDescription; 
    public static String gamesListLevel; 
    public static String gamesListCategory; 
    public static String gamesListPlanApproved; 
    public static String gamesListInteractionTemplate;
    public static String gamesListInteractionTemplateLevel;
    public static String gamesListInteractionTemplateCategory;
    public static String gamesListPracticeTask;

}
