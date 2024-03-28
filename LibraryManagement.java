import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
class Student {
    public static final String URL = "jdbc:mysql://localhost:3306/Pratham";
    public static final String USER = "Pratham";
    public static final String PASSWORD = "Pratham@16";
    
    public int getFine(Connection connection, String id) throws SQLException
    {
    	Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT student_fine FROM Student WHERE student_id = '" + id + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
            	int a = resultSet.getInt("student_fine");
            	return a;
            }
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
    
    public void updateFine(Connection connection, String id, int bookId) throws SQLException {
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        statement = connection.createStatement();
        String query = "SELECT book1_return, book2_return, book3_return FROM Issued WHERE id = '" + id + "'";
        resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String a1 = resultSet.getString("book1_return");
            String a2 = resultSet.getString("book2_return");
            String a3 = resultSet.getString("book3_return");

            int a = issuedbooknumber(connection, id, bookId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            String Todaysdate = dateFormat.format(today);

            if (a == 1 && a1 != null) {
                Date providedDate = dateFormat.parse(a1);
                int comparison = providedDate.compareTo(today);
                long timeDiff = Math.abs(providedDate.getTime() - today.getTime());
                long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
                int fine = getFine(connection, id);
                fine += daysDiff;
                String futureDate = getFifteenDate();
                if (comparison < 0) {
                statement = connection.createStatement();
                    String updateQuery1 = "UPDATE Issued SET book1_return = '" + futureDate + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(updateQuery1);
                    String updateQuery4 = "UPDATE Student SET student_fine =" + fine + " WHERE student_id = '" + id + "'";
                    statement.executeUpdate(updateQuery4);
                }
            } else if (a == 2 && a2 != null) {
                Date providedDate = dateFormat.parse(a2);
                int comparison = providedDate.compareTo(today);
                long timeDiff = Math.abs(providedDate.getTime() - today.getTime());
                long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
                int fine = getFine(connection, id);
                fine += daysDiff;
                String futureDate = getFifteenDate();
                if (comparison < 0) {
                statement = connection.createStatement();
                    String updateQuery2 = "UPDATE Issued SET book2_return = '" + futureDate + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(updateQuery2);
                    String updateQuery5 = "UPDATE Student SET student_fine =" + fine + " WHERE student_id = '" + id + "'";
                    statement.executeUpdate(updateQuery5);
                }
            } else if (a == 3 && a3 != null) {
                Date providedDate = dateFormat.parse(a3);
                int comparison = providedDate.compareTo(today);
                long timeDiff = Math.abs(providedDate.getTime() - today.getTime());
                long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
                int fine = getFine(connection, id);
                fine += daysDiff;
                String futureDate = getFifteenDate();
                if (comparison < 0) {
                statement = connection.createStatement();
                    String updateQuery3 = "UPDATE Issued SET book3_return = '" + futureDate + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(updateQuery3);
                    String updateQuery6 = "UPDATE Student SET student_fine =" + fine + " WHERE student_id = '" + id + "'";
                    statement.executeUpdate(updateQuery6);
                }
            }
        }
    } catch (SQLException | ParseException e) {
        e.printStackTrace();
    } finally {
        // Close resources
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


    
    

    public int issuedbooknumber(Connection connection, String id, int bookid) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT book1_id, book2_id, book3_id FROM Issued WHERE id = '" + id + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int a = resultSet.getInt("book1_id");
                int b = resultSet.getInt("book2_id");
                int c = resultSet.getInt("book3_id");
                if (a == bookid) {
                    return 1;
                }
                if (b == bookid) {
                    return 2;
                }
                if (c == bookid) {
                    return 3;
                }
            } else {
                System.out.println("Book with ID '" + id + "' not Issued by this Student");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public int quantityOfBookRemaining(Connection connection, int id) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT quantity FROM Books WHERE book_id = '" + id + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            } else {
                System.out.println("Book with ID '" + id + "' not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public String getTodaysdate() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedToday = dateFormat.format(today);
        return formattedToday;
    }

    public String getFifteenDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 15);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedFutureDate = dateFormat.format(calendar.getTime());
        return formattedFutureDate;
    }
    

    public boolean isBookIssued(Connection connection, String id, int bookid) throws SQLException {
        if (bookid != 0) {
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.createStatement();
                String query = "SELECT book1_id, book2_id, book3_id FROM Issued WHERE id = '" + id + "'";
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    int a = resultSet.getInt("book1_id");
                    int b = resultSet.getInt("book2_id");
                    int c = resultSet.getInt("book3_id");
                    return (bookid == a || bookid == b || bookid == c);
                } else {
                    System.out.println("Student with ID '" + id + "' not Issued this book.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
    public int numberOfBooksIssued(Connection connection, String id) throws SQLException {
        int c = 0;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT books_issued FROM Student WHERE student_id='" + id + "'")) {
                if (resultSet.next()) {
                    c = resultSet.getInt("books_issued");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public boolean isValidStudentId(Connection connection, String id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT student_id FROM Student WHERE student_id = '" + id + "'")) {
                if (resultSet.next()) {
                    String c = resultSet.getString("student_id");
                    if (c.equals(id)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void printBooks(Connection connection) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT book_id, book_name, book_author, quantity, book_subject FROM Books");

            System.out.println("Books available in the library:");
            System.out.printf("%-8s | %-50s | %-20s | %-8s | %-20s\n", "Book ID", "Book Name", "Book Author", "Quantity", "Book Subject");
            while (resultSet.next()) {
                String bookId = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String bookAuthor = resultSet.getString("book_author");
                int quantity = resultSet.getInt("quantity");
                String bookSubject = resultSet.getString("book_subject");

                System.out.printf("%-8s | %-50s | %-20s | %-8d | %-20s\n", bookId, bookName, bookAuthor, quantity, bookSubject);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }

    public void studentinfo(Connection connection, String id) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM Student WHERE student_id = '" + id + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String name = resultSet.getString("name");
                String addr = resultSet.getString("addr");
                String phone = resultSet.getString("phone_no");
                int issued = resultSet.getInt("books_issued");
                int age = resultSet.getInt("student_age");

                System.out.println("Student Information:");
                System.out.println("Student ID: " + studentId);
                System.out.println("Name: " + name);
                System.out.println("Address: " + addr);
                System.out.println("Phone Number: " + phone);
                System.out.println("Books Issued: " + issued);
                System.out.println("Age: " + age);
            } else {
                System.out.println("Student with ID '" + id + "' not found.");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
}

class Staff extends Student
{
	protected void returnBook(Connection connection, String id, int bookId) throws SQLException
	{
		int a;
        a = numberOfBooksIssued(connection, id);
        if (a != 0) {
            Statement statement = null;
                int k = issuedbooknumber(connection, id, bookId);
                try {
                    statement = connection.createStatement();
                    if (k == 1) {
                    	updateFine(connection,id,bookId);
                        String updateQuery1 = "UPDATE Issued SET book1_date = NULL, book1_id = 0,book1_return = NULL WHERE id = '" + id + "' AND book1_id = " + bookId;
                        statement.executeUpdate(updateQuery1);
                    }
                    if (k == 2) {
                    updateFine(connection,id,bookId);
                        String updateQuery2 = "UPDATE Issued SET book2_date = NULL, book2_id = 0,book2_return = NULL WHERE id = '" + id + "' AND book2_id = " + bookId;
                        statement.executeUpdate(updateQuery2);
                    }
                    if (k == 3) {
                    updateFine(connection,id,bookId);
                        String updateQuery3 = "UPDATE Issued SET book3_date = NULL, book3_id = 0,book3_return = NULL WHERE id = '" + id + "' AND book3_id = " + bookId;
                        statement.executeUpdate(updateQuery3);
                    }
					if (k!=0){
                    String incrementQuantityQuery = "UPDATE Books SET quantity = quantity + 1 WHERE book_id = " + bookId;
                    statement.executeUpdate(incrementQuantityQuery);

                    String decrementBooksIssuedQuery = "UPDATE Student SET books_issued = books_issued - 1 WHERE student_id = '" + id + "'";
                    statement.executeUpdate(decrementBooksIssuedQuery);

                    System.out.println("Book returned successfully!");
                    }
                    if(k <= 0||k>3)
                    {
                    	System.out.println("This book is Not Issued by student");
                    }
                } finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            } else {
                System.out.println("No book Issued by student");
            }
	}
	
	protected void issueBook(Connection connection, String id, int bookId) throws SQLException {
            Statement statement = null;
            int k = quantityOfBookRemaining(connection, bookId);
            if (k != 0) {
                int a;
                a = numberOfBooksIssued(connection, id);
                String date = getTodaysdate();
                String returndate = getFifteenDate();
                if(a<=3){
                try {
                    statement = connection.createStatement();

                    if (a == 0) {
                        String updateQuery1 = "UPDATE Issued SET book1_date = '" + date + "', book1_id = " + bookId + " , book1_return = '" + returndate + "' WHERE id = '" + id + "' AND book1_id = 0";
                        statement.executeUpdate(updateQuery1);
                    } else if (a == 1) {
                        String updateQuery2 = "UPDATE Issued SET book2_date = '" + date + "', book2_id = " + bookId + ",  book2_return = '" + returndate + "' WHERE id = '" + id + "' AND book2_id = 0";
                        statement.executeUpdate(updateQuery2);
                    } else if (a == 2) {
                        String updateQuery3 = "UPDATE Issued SET book3_date = '" + date + "', book3_id = " + bookId + ",  book3_return = '" + returndate + "' WHERE id = '" + id + "' AND book3_id = 0";
                        statement.executeUpdate(updateQuery3);
                    } else {
                        System.out.println("You already exceeded the issuing limit");
                    }
                    if (a < 3) {
                        String incrementQuantityQuery = "UPDATE Books SET quantity = quantity - 1 WHERE book_id = " + bookId;
                        statement.executeUpdate(incrementQuantityQuery);

                        String decrementBooksIssuedQuery = "UPDATE Student SET books_issued = books_issued + 1 WHERE student_id = '" + id + "'";
                        statement.executeUpdate(decrementBooksIssuedQuery);

                        System.out.println("Book Issued successfully!");
                    }
                } finally {

                    if (statement != null) {
                        statement.close();
                    }
                }

            } else {
            	System.out.println("You reached the book issuing limit.");
            }
            } else {
                System.out.println("The book you requested is not available at this time.");
            }
    }
    
    
    protected void renewBook(Connection connection, String id, int bookId) throws SQLException {
               updateFine(connection,id,bookId);
    	}
}


class Librarian {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Student.URL, Student.USER, Student.PASSWORD);
            if (connection != null) {
                Student student = new Student();
                Staff s = new Staff();
                s.issueBook(connection,"2022BIT045",3);
                s.returnBook(connection,"2022BIT045",10);
                s.returnBook(connection,"2022BIT045",3);
                
            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed! Check output console");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


