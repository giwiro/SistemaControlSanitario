package config;

/**
 * Created by Gi Wah Davalos on 1/07/2016.
 */
public class DBInfo {
    public static final String DB_HOST = "ec2-54-225-79-158.compute-1.amazonaws.com";
    public static final String DB_PORT = "5432";
    public static final String DB_USER = "ogheqdgdrstmvg";
    public static final String DB_PASSWORD = "J3enL7SGH2j40MLjg8f7-2UpRL";
    public static final String DB_NAME = "d43792a489l8do";

    /*public static final String DB_HOST = "localhost";
    public static final String DB_PORT = "5432";
    public static final String DB_USER = "giwiro";
    public static final String DB_PASSWORD = "XHEXRR";
    public static final String DB_NAME = "d43792a489l8do";*/

    public static String getURL() {
        final StringBuilder st = new StringBuilder();
        st.append("jdbc:postgresql://");
        /*st.append(DB_USER);
        st.append(":");
        st.append(DB_PASSWORD);
        st.append("@");*/
        st.append(DB_HOST);
        st.append(":");
        st.append(DB_PORT);
        st.append("/");
        st.append(DB_NAME);

        return st.toString();
    }

    public static String getSecureURL() {
        final StringBuilder st = new StringBuilder();
        st.append("jdbc:postgresql://");
        st.append(DB_HOST);
        st.append(":");
        st.append(DB_PORT);
        st.append("/");
        st.append(DB_NAME);
        st.append("?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");

        return st.toString();
    }


}
