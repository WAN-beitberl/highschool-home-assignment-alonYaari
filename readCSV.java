import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Arrays;



public class readCSV {
    List<String [] > allStudents = null;
    List<String [] > friendsList = null;
    String url = "jdbc:mysql://localhost:3306/arsenal";
    String name = "root";
    String password = "AlonY2004";

    public void parse(){
        try (CSVReader firstTableReader = new CSVReader(new FileReader("C:\\Users\\Admin\\Desktop\\שיעורים\\שיעורים של טל בר\\highschool_sql_assignment\\highschool_sql_assignment\\highschool.csv"))) {

            allStudents = firstTableReader.readAll();

            for (int i=1;i<allStudents.size();i++){
                String [] student = allStudents.get(i);
                canBeParsed(student, "highschool ");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try (CSVReader secondTableReader = new CSVReader(new FileReader("C:\\Users\\Admin\\Desktop\\שיעורים\\שיעורים של טל בר\\highschool_sql_assignment\\highschool_sql_assignment\\highschool_friendships.csv"))){
            friendsList = secondTableReader.readAll();

            for (int i=1;i <= 1000;i++){
                String [] friend = friendsList.get(i);
                canBeParsed(friend, "highschool_friends ");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canBeParsed(String [] student, String tableName){
        if (tableName == "highschool ") {
            if (student[8].equalsIgnoreCase("FALSE")) {
                student[9] = "";
            } else {
                if (student[9].equalsIgnoreCase(""))
                    student[9] = "Not selected";
            }

            for (int i = 1; i <= 5; i++) {
                student[i] = "\"" + student[i] + "\"";

            }
            student[9] = "'" + student[9] + "'";
        }
        else{
            if(student[1].isEmpty() || student[2].isEmpty())
                return false;
        }



        String value = Arrays.toString(student).replace('[' , '(').replace(']',')');
        String query = "INSERT INTO " + tableName + "VALUES" + value + ";";

        PreparedStatement statement = null;
        Connection con = null;
        System.out.println(query);
        try {
            con = DriverManager.getConnection(url, name, password);
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
        }
        catch (SQLException e) {
            System.out.println("exception : " + e.getMessage() + " error code: " + e.getErrorCode());
            return false; // something went wrong
        }

        finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }
}

