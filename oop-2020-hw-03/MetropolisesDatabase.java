import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetropolisesDatabase {
    private Statement statement;
    private static final int COLUMN_COUNT = 3;

    public MetropolisesDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //might need to call new instance
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/localhost", "root", "araqvs");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getMetropolises(String whereClause){
        List<String[]> result = new ArrayList<>();
        try {
            boolean successful = statement.execute("select * from metropolises " + whereClause);
            if (successful) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    String[] currRow = new String[COLUMN_COUNT];
                    for (int i = 0; i < COLUMN_COUNT - 1; i++) {
                        currRow[i] = resultSet.getString(i + 1);
                    }
                    currRow[2] = String.valueOf(resultSet.getLong(3));
                    result.add(currRow);
                }
            } else{
                throw new RuntimeException("wasn't successful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public void addMetropolis(String[] row){
        try {
            String statementSql = "insert into metropolises values (\"" +
                    row[0] + "\",\"" +
                    row[1] + "\"," +
                    row[2] + ");";
            int res = statement.executeUpdate(statementSql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
