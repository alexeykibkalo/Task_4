package myprojects.automation.assignment4.utils;

public class Properties {
    private static final String DEFAULT_BASE_URL = "http://prestashop-automation.qatestlab.com.ua/";
    private static final String DEFAULT_BASE_ADMIN_URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
    private static final String DEFAULT_LOGIN = "webinar.test@gmail.com";
    private static final String DEFAULT_PASSWORD = "Xcg7299bnSmMuRLp9ITw";

    public static String getBaseUrl() {
        return System.getProperty(EnvironmentVariables.BASE_URL.toString(), DEFAULT_BASE_URL);
    }

    public static String getBaseAdminUrl() {
        return System.getProperty(EnvironmentVariables.BASE_ADMIN_URL.toString(), DEFAULT_BASE_ADMIN_URL);
    }

    public static String getLogin(){
        return System.getProperty(EnvironmentVariables.LOGIN.toString(), DEFAULT_LOGIN);
    }

    public static String getPassword(){
        return System.getProperty(EnvironmentVariables.PASSWORD.toString(), DEFAULT_PASSWORD);
    }
}

enum EnvironmentVariables {
    BASE_URL("env.url"),
    BASE_ADMIN_URL("env.admin.url"),
    LOGIN("login"),
    PASSWORD("password");

    private String value;
    EnvironmentVariables(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}