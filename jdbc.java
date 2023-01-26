import java.sql.*;
import java.util.ArrayList;

public class jdbc {
    static String url = "jdbc:mysql://localhost:3306/arsenal";
    static String name = "root";
    static String password = "AlonY2004";

    public static float avgGrades(int option) {
        String res = "";
        String query = "";
        switch (option){
            case 1: // School average
                query = "select sum(grade_avg)/count(grade_avg) from highschool;";
                break;
            case 2: // Male's  average
                query = "select sum(grade_avg)/count(grade_avg) from highschool Where gender = \"male\";";

                break;
            case 3: // Female's  average
                query = "select sum(grade_avg)/count(grade_avg) from highschool Where gender = \"female\";";
                break;
            default:
                break;
        }
        //System.out.println("query: " + query);

        PreparedStatement statement = null;
        Connection con = null;
        ResultSet resultSet;
        try {
            con = DriverManager.getConnection(url, name, password);
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            resultSet = statement.getResultSet();
            resultSet.next();
            res = resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Float.parseFloat(res);
    }


    public static float avgHeight(){
        String res = "";
        String query = "select avg(cm_height) from highschool";
        String where = " WHERE cm_height > 199 AND car_color = \"Purple\" ;";
        //System.out.println("query: " + query + "" + where);

        PreparedStatement statement = null;
        Connection con = null;
        ResultSet resultSet;
        try {
            con = DriverManager.getConnection(url, name, password);
            statement = con.prepareStatement(query + where, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            resultSet = statement.getResultSet();
            resultSet.next();
            res = resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Float.parseFloat(res);
    }

    public static ArrayList<Integer> findFriends(int id, int withSecondCircle){
        ArrayList<Integer> friendsArr = new ArrayList<>();

        String query = "select friend_id, other_friend_id from highschool_friends";
        String where = " WHERE " + id + " = friend_id OR other_friend_id = " + id + ";";
        //System.out.println("query: " + query + "" + where);

        PreparedStatement statement = null;
        Connection con = null;
        Connection con2 = null;
        try {
            con = DriverManager.getConnection(url, name, password); // Connecting to DB.
            statement = con.prepareStatement(query + where, Statement.RETURN_GENERATED_KEYS); // Build the query.
            statement.execute();

            String res;
            ResultSet rs = statement.getResultSet(); // Gets the result.
            while (rs.next()){
                res = rs.getString(1);

                if (res != null && !res.equals(Integer.toString(id)) && !friendsArr.contains(Integer.parseInt(res.toString())))
                    friendsArr.add(Integer.parseInt(res));


                res = rs.getString(2);
                if (res != null && !res.equals(Integer.toString(id)) && !friendsArr.contains(Integer.parseInt(res.toString())))
                    friendsArr.add(Integer.parseInt(res));
            }
            if (withSecondCircle == 1){// Check if two count the second circle of friends as well. According to the parameters.
                int firstRoundSize = friendsArr.size(); // In purpose that the size wont grow up while adding more students.
                for (int currFriend=0; currFriend < firstRoundSize;currFriend++){ // In order to check the second round of friends.
                    where = " WHERE " + friendsArr.get(currFriend) + " = friend_id OR other_friend_id = " + friendsArr.get(currFriend) + ";";
                    //System.out.println("query: " + query + "" + where);
                    con2 = DriverManager.getConnection(url, name, password); // Connecting to DB.
                    PreparedStatement statement2 = con2.prepareStatement(query + where, Statement.RETURN_GENERATED_KEYS); // Querying again
                    statement2.execute();


                    // Same logic
                    String secondRes;
                    ResultSet secondRS = statement2.getResultSet();
                    while (secondRS.next()){
                        secondRes = secondRS.getString(1);

                        if (secondRes != null && !secondRes.equals((Integer.toString(currFriend))) &&
                                !secondRes.equals(Integer.toString(id)) &&
                                !friendsArr.contains(Integer.parseInt(secondRes.toString())))
                            friendsArr.add(Integer.parseInt(secondRes));


                        secondRes = secondRS.getString(2);
                        if (secondRes != null && !secondRes.equals((Integer.toString(currFriend))) &&
                                !secondRes.equals(Integer.toString(id)) &&
                                !friendsArr.contains(Integer.parseInt(secondRes.toString())))
                            friendsArr.add(Integer.parseInt(secondRes));
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("exception : " + e.getMessage() + " error code: " + e.getErrorCode());
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

        return  friendsArr;
    }

    public static void studentTypes(){
        float populars = 0;
        float regulars = 0;
        float individuals = 0;
        for (int id=1;id<=1000;id++){
            if (findFriends(id,0).size() > 2)
                populars++;
            else if (findFriends(id,0).size() == 2)
                regulars++;
            else
                individuals++;
        }

        System.out.println("The percentage of the popular students is: " + populars/10 + "%");
        System.out.println("The percentage of the regular students is: " + regulars/10 + "%");
        System.out.println("The percentage of the individual students is: " + individuals/10 + "%");
    }

    public static float getAvg(int id){
        String res = "";
        String select = "select grade_avg from student";
        String where = " WHERE " + id + " = id;";
        //System.out.println("query: " + select + " " + where);

        PreparedStatement statement = null;
        Connection con = null;
        ResultSet resultSet;
        try {
            con = DriverManager.getConnection(url, name, password);
            statement = con.prepareStatement(select + where, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            resultSet = statement.getResultSet();
            resultSet.next();
            res = resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Float.parseFloat(res);
    }
}
