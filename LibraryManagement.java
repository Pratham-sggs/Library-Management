import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Scanner;
import java.util.InputMismatchException;


class Student {
    public static final String URL = "jdbc:mysql://localhost:3306/Pratham";
    public static final String USER = "Pratham";
    public static final String PASSWORD = "Pratham@16";
    
    
    
    public boolean isValidStudentPassword(Connection connection,String studentId,String studentPassword) throws SQLException {
    	Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT student_password FROM Student WHERE student_id = '" + studentId + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String nameFromDb = resultSet.getString("student_password");
                if (nameFromDb.equals(studentPassword)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }

    public String getSerialNumber(Connection connection, String Id) {

        int Quantity = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT students_issued FROM Department WHERE department = '" + Id + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Quantity = resultSet.getInt("students_issued");
            }
            if (Quantity < 999) {
                Quantity = Quantity + 1;
                String updateQuery2 = "UPDATE Department SET students_issued =" + Quantity + " WHERE department = '" + Id + "'";
                statement.executeUpdate(updateQuery2);
                String i = Integer.toString(Quantity);
                return i;
            }
            if (Quantity >= 999) {
                Quantity = 0;
                Quantity = Quantity + 1;
                String updateQuery2 = "UPDATE Department SET students_issued =" + Quantity + " WHERE department = '" + Id + "'";
                statement.executeUpdate(updateQuery2);
                String i = Integer.toString(Quantity);
                return i;
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

        return "0";

    }

    public String getRandomStudentId(Connection connection) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Information Technology");
        System.out.println("2.Computer Science and Engineering");
        System.out.println("3.Electronics and Telecommunication Engineering");
        System.out.println("4.Instrumentation Engineering");
        System.out.println("5.Electrical Engineering");
        System.out.println("6.Chemical Engineering");
        System.out.println("7.Civil Engineering");
        System.out.println("8.Textile Technology");
        System.out.println("9.Production Engineering");
        System.out.println("10.Mechanical Engineering");
        System.out.println("Enter Your Department Number:");
        int Choice = scanner.nextInt();
        int Department = 0;
        String departmentId = "";

        do {
            switch (Choice) {
                case 1:
                    departmentId = "BIT";
                    break;
                case 2:
                    departmentId = "BCS";
                    break;
                case 3:
                    departmentId = "BEC";
                    break;
                case 4:
                    departmentId = "BIN";
                    break;
                case 5:
                    departmentId = "BEL";
                    break;
                case 6:
                    departmentId = "BCH";
                    break;
                case 7:
                    departmentId = "BCE";
                    break;
                case 8:
                    departmentId = "BTT";
                    break;
                case 9:
                    departmentId = "BPR";
                    break;
                case 10:
                    departmentId = "BME";
                    break;
                default:
                    System.out.println("Enter Valid Choice! ");
                    break;
            }
        } while (departmentId.isEmpty());

        String date = getTodaysdate();
        String year = date.substring(0, 4);

        String SerialNumber = getSerialNumber(connection, departmentId);
        int SerialNo = Integer.parseInt(SerialNumber);
        if (SerialNo < 10 && SerialNo != 0) {
            return year + departmentId + "00" + SerialNumber;
        }
        if (SerialNo <= 99 && SerialNo >= 10) {
            return year + departmentId + "0" + SerialNumber;
        }
        if (SerialNo <= 999 && SerialNo >= 100) {
            return year + departmentId + SerialNumber;
        }
        return "";
    }

    public String getStudentName(Connection connection, String id) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        String name = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT name FROM Student WHERE student_id = '" + id + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                name = resultSet.getString("name");
            } else {
                System.out.println("Student with ID '" + id + "' not found.");
                return name;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
        return name;
    }

    public boolean studentNameMatches(Connection connection, String name) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT name FROM Student WHERE name = '" + name + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String nameFromDb = resultSet.getString("name");
                if (nameFromDb.equals(name)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }

    public int getTotalQuantity(Connection connection, int bookId) {
        int Quantity = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT total_quantity FROM Books WHERE book_id = " + bookId;
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Quantity = resultSet.getInt("total_quantity");
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

        return Quantity;
    }

    public int getBookId(Connection connection, String bookName) {

        int bookId = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT book_id FROM Books WHERE book_name = '" + bookName + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                bookId = resultSet.getInt("book_id");
            }
            if (bookId == 0) {
                System.out.println("Book Name is incorrect");
                return bookId;
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

        return bookId;
    }

    public boolean bookAlreadyExist(Connection connection, String BookName) {

        int bookId = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT book_id FROM Books WHERE book_name = '" + BookName + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                bookId = resultSet.getInt("book_id");
            }
            if (bookId == 0) {
                return false;
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
        return true;
    }

    public int getFine(Connection connection, String id) throws SQLException {
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
                int fine = resultSet.getInt("student_fine");

                System.out.println("Student Information:");
                System.out.println("Student ID: " + studentId);
                System.out.println("Name: " + name);
                System.out.println("Address: " + addr);
                System.out.println("Phone Number: " + phone);
                System.out.println("Books Issued: " + issued);
                System.out.println("Age: " + age);
                System.out.println("Fine: " + fine);
            } else {
                System.out.println("Student with ID '" + id + "' not found.");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
}




class Staff extends Student {


	protected void repayFine(Connection connection, String Id) {
    Scanner scanner = new Scanner(System.in);
    try {
        System.out.print("Enter Amount: ");
        int repayAmount = scanner.nextInt();
        
        int totalAmount = getFine(connection, Id);
        int newAmount = totalAmount - repayAmount;
        
        if (newAmount < 0) {
            System.out.println("Amount to repay exceeds total fine.");
            return;
        }
        
        String updateQuery = "UPDATE Student SET student_fine = " + newAmount + " WHERE student_id = '" + Id + "'";
        Statement statement = connection.createStatement();
        
        int rowsAffected = statement.executeUpdate(updateQuery);

        if (rowsAffected > 0) {
            System.out.println("Fine updated.");
        } else {
            System.out.println("Failed to update fine.");
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid amount.");
    } catch (SQLException e) {
        System.out.println("Error while updating fine: " + e.getMessage());
    } finally {
        scanner.close();
    }
}



    protected void addStudent(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Name: ");
            String studentName = scanner.nextLine();

            if (studentNameMatches(connection, studentName)) {
                System.out.println("Student with Name: " + studentName + " already exists.");
                return;
            }

            System.out.print("Enter Address: ");
            String studentAddress = scanner.nextLine();

            System.out.print("Enter Age: ");
            int studentAge = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String studentPhone = scanner.nextLine();

            int a = 0;
            String studentPassword = "";
            while (a == 0) {
                System.out.print("Set Password: ");
                studentPassword = scanner.nextLine();
                System.out.print("Enter Password again: ");
                String studentPassword1 = scanner.nextLine();
                if (studentPassword.equals(studentPassword1)) {
                    a = 1;
                } else {
                    a = 0;
                    System.out.println("Both passwords should match");
                }
            }

            String studentId = getRandomStudentId(connection);

            Statement statement = connection.createStatement();
            String query = "INSERT INTO Student (student_id, name, addr, phone_no, books_issued, student_age, student_fine, student_password) VALUES ('" + studentId + "', '" + studentName + "', '" + studentAddress + "', '" + studentPhone + "', '0', '" + studentAge + "', '0', '" + studentPassword + "')";
            String query1 = "INSERT INTO Issued (id, book1_id, book2_id, book3_id) VALUES ('" + studentId + "','0','0','0')";
            statement.executeUpdate(query);
            int rowsAffected = statement.executeUpdate(query1);
            if (rowsAffected > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding student: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    protected void returnBook(Connection connection, String id, int bookId) throws SQLException {
        int a;
        a = numberOfBooksIssued(connection, id);
        if (a != 0) {
            Statement statement = null;
            int k = issuedbooknumber(connection, id, bookId);
            try {
                statement = connection.createStatement();
                if (k == 1) {
                    updateFine(connection, id, bookId);
                    String updateQuery1 = "UPDATE Issued SET book1_date = NULL, book1_id = 0,book1_return = NULL WHERE id = '" + id + "' AND book1_id = " + bookId;
                    statement.executeUpdate(updateQuery1);
                }
                if (k == 2) {
                    updateFine(connection, id, bookId);
                    String updateQuery2 = "UPDATE Issued SET book2_date = NULL, book2_id = 0,book2_return = NULL WHERE id = '" + id + "' AND book2_id = " + bookId;
                    statement.executeUpdate(updateQuery2);
                }
                if (k == 3) {
                    updateFine(connection, id, bookId);
                    String updateQuery3 = "UPDATE Issued SET book3_date = NULL, book3_id = 0,book3_return = NULL WHERE id = '" + id + "' AND book3_id = " + bookId;
                    statement.executeUpdate(updateQuery3);
                }
                if (k != 0) {
                    String incrementQuantityQuery = "UPDATE Books SET quantity = quantity + 1 WHERE book_id = " + bookId;
                    statement.executeUpdate(incrementQuantityQuery);

                    String decrementBooksIssuedQuery = "UPDATE Student SET books_issued = books_issued - 1 WHERE student_id = '" + id + "'";
                    statement.executeUpdate(decrementBooksIssuedQuery);

                    System.out.println("Book returned successfully!");
                }
                if (k <= 0 || k > 3) {
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
            if (a <= 3) {
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
        updateFine(connection, id, bookId);
    }
    
    
    protected void removeStudent(Connection connection) throws SQLException {
    	Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Name: ");
            String studentName = scanner.nextLine();

            if (!studentNameMatches(connection, studentName)) {
                System.out.println("Student with Name: " + studentName + " doesnot exists.");
                return;
            }

            
			System.out.print("Enter Student Id: ");
            String studentId = scanner.nextLine();

            Statement statement = connection.createStatement();
            String query = "DELETE FROM Student WHERE name = '" + studentName + "' AND student_id = '" + studentId + "'";
            String query1 = "DELETE FROM Issued WHERE name = '" + studentName + "' AND student_id = '" + studentId + "'";
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
            int rowsAffected = statement.executeUpdate(query1);
            if (rowsAffected > 0) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Failed to remove student.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding student: " + e.getMessage());
        } finally {
            scanner.close();
        }
    	
    	
    }
    
    protected void printStaff(Connection connection) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT staff_id, name, addr, phone_no, staff_pass, last_login, salary FROM Staff");

            System.out.println("Staff Information:");
			System.out.printf("%-8s | %-50s | %-20s | %-8s | %-20s | %-8s\n", "Staff ID", "Name", "Address", "phone_no", "Password", "Salary");
			while (resultSet.next()) {
    			String StaffId = resultSet.getString("staff_id");
    			String Name = resultSet.getString("name");
    			String Address = resultSet.getString("addr");
    			String Phone = resultSet.getString("phone_no");
    			String Password = resultSet.getString("staff_pass");
    			int salary = resultSet.getInt("salary");

    			System.out.printf("%-8s | %-50s | %-20s | %-8s | %-20s | %-8d\n", StaffId, Name, Address, Phone, Password, salary);
			}
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
    
    protected boolean isValidStaffPassword(Connection connection,String staffId,String staffPassword) throws SQLException  {
    	Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT staff_pass FROM Staff WHERE staff_id = '" + staffId + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String nameFromDb = resultSet.getString("staff_pass");
                if (nameFromDb.equals(staffPassword)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
    
    public boolean isValidStaffId(Connection connection, String id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT staff_id FROM Staff WHERE staff_id = '" + id + "'")) {
                if (resultSet.next()) {
                    String c = resultSet.getString("staff_id");
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

}




class Librarian extends Staff {
	
	private String getStaffId(Connection connection, String name) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        
        String staffId = "";

        try {
            statement = connection.createStatement();
            String query = "SELECT staff_id FROM Staff WHERE name = '" + name + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                staffId = resultSet.getString("staff_id");
                return staffId;
            } else {
                return staffId;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
    
	
	
	private void removeStaff(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Name: ");
            String staffName = scanner.nextLine();

            if (!staffNameMatches(connection, staffName)) {
                System.out.println("Staff with Name: " + staffName + " Does Not exists.");
                return;
            }
			
			System.out.print("Enter Staff Id: ");
            String staffId = scanner.nextLine();
			
			String staffId1 = getStaffId(connection,staffName);
			
			if(staffId.equals(staffId1)){
			
            	Statement statement = connection.createStatement();
            	String query = "DELETE FROM Staff WHERE staff_id = '" + staffId + "' AND name = '" + staffName + "'";

            	int rowsAffected = statement.executeUpdate(query);
            	if (rowsAffected > 0) {
            	    System.out.println("Staff Removed successfully.");
            	} else {
            	    System.out.println("Failed to Remove staff.");
            	}
        	}
        	else{
        		System.out.println("Staff Id Incorrect");
        	}
        } catch (SQLException e) {
            System.out.println("Error while adding student: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
	
	
    private boolean staffNameMatches(Connection connection, String name) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT name FROM Staff WHERE name = '" + name + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String nameFromDb = resultSet.getString("name");
                if (nameFromDb.equals(name)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }

    private String getStaffIssueNumber(Connection connection) {
        int Quantity = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT Number FROM LibraryStaffIssued ";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Quantity = resultSet.getInt("Number");
            }
            if (Quantity < 999) {
                Quantity = Quantity + 1;
                String updateQuery2 = "UPDATE LibraryStaffIssued SET Number =" + Quantity;
                statement.executeUpdate(updateQuery2);
                String i = Integer.toString(Quantity);
                return i;
            }
            if (Quantity >= 999) {
                Quantity = 0;
                Quantity = Quantity + 1;
                String updateQuery2 = "UPDATE LibraryStaffIssued SET Number =" + Quantity;
                statement.executeUpdate(updateQuery2);
                String i = Integer.toString(Quantity);
                return i;
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
        return "0";
    }

    private String getRandomStaffId(Connection connection) {
        String departmentId = "LIB";
        String date = getTodaysdate();
        String year = date.substring(0, 4);

        String SerialNumber = getStaffIssueNumber(connection);
        int SerialNo = Integer.parseInt(SerialNumber);
        if (SerialNo < 10 && SerialNo != 0) {
            return "S" + year + departmentId + "00" + SerialNumber;
        }
        if (SerialNo <= 99 && SerialNo >= 10) {
            return "S" + year + departmentId + "0" + SerialNumber;
        }
        if (SerialNo <= 999 && SerialNo >= 100) {
            return "S" + year + departmentId + SerialNumber;
        }
        return "";
    }

    private void addStaff(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Name: ");
            String staffName = scanner.nextLine();

            if (staffNameMatches(connection, staffName)) {
                System.out.println("Staff with Name: " + staffName + " already exists.");
                return;
            }

            System.out.print("Enter Address: ");
            String staffAddress = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String staffPhone = scanner.nextLine();

            System.out.print("Enter Salary: ");
            int staffSalary = scanner.nextInt();
            scanner.nextLine();

            int a = 0;
            String staffPassword = "";
            while (a == 0) {
                System.out.print("Set Password: ");
                staffPassword = scanner.nextLine();
                System.out.print("Enter Password again: ");
                String staffPassword1 = scanner.nextLine();
                if (staffPassword.equals(staffPassword1)) {
                    a = 1;
                } else {
                    a = 0;
                    System.out.println("Both passwords should match");
                }
            }

            String staffId = getRandomStaffId(connection);

            String stafflogin = getTodaysdate();

            Statement statement = connection.createStatement();
            String query = "INSERT INTO Staff (staff_id, name, addr, phone_no, staff_pass, Salary, last_login) VALUES ('" + staffId + "', '" + staffName + "', '" + staffAddress + "', '" + staffPhone + "','" + staffPassword + "', '" + staffSalary + "','" + stafflogin + "')";

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Staff added successfully.");
            } else {
                System.out.println("Failed to add staff.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding student: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private void addBook(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a Book Name: ");
        String bookName = scanner.nextLine();
        // Check if book already exists
        if (bookAlreadyExist(connection, bookName)) {
            System.out.println("Book already exists.");
            return;
        }
        System.out.print("Enter Author Name: ");
        String bookAuthor = scanner.nextLine();
        System.out.print("Enter Book Subject: ");
        String bookSubject = scanner.nextLine();
        System.out.print("Enter Book Quantity: ");
        int bookQuantity = scanner.nextInt();

        // Validate book quantity
        if (bookQuantity <= 0) {
            System.out.println("Book quantity cannot be zero or negative.");
            return;
        }

        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Books (book_name, book_author, book_subject, quantity, total_quantity) VALUES ('" + bookName + "', '" + bookAuthor + "', '" + bookSubject + "', " + bookQuantity + ", " + bookQuantity + ")";
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add book.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding book: " + e.getMessage());
        } finally {
            scanner.close(); // Close the scanner to release resources
        }
    }

    private void removeBook(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter a Book Name: ");
            if (!scanner.hasNextLine()) {
                System.out.println("No input provided.");
                return;
            }
            String bookName = scanner.nextLine();

            if (!bookAlreadyExist(connection, bookName)) {
                System.out.println("Book does not exist in this library.");
                return;
            }
            int quantityOfBookRemaining = quantityOfBookRemaining(connection, getBookId(connection, bookName));
            int totalQuantity = getTotalQuantity(connection, getBookId(connection, bookName));
            if (quantityOfBookRemaining != totalQuantity) {
                System.out.println("This Book is Issued by student so cannot be removed.");
                return;
            }
            System.out.print("Enter Author Name: ");
            String bookAuthor = scanner.nextLine();
            System.out.print("Enter Book Subject: ");
            String bookSubject = scanner.nextLine();

            Statement statement = connection.createStatement();
            String query = "DELETE FROM Books WHERE book_name = '" + bookName + "' AND book_author = '" + bookAuthor + "' AND book_subject = '" + bookSubject + "'";
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Book with name " + bookName + " deleted successfully.");
            } else {
                System.out.println("No book deleted Incorrect Author name or Subject.");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting row: " + e.getMessage());
        } finally {
            scanner.close(); // Close the scanner to release resources
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static String getLibrarianId(Connection connection) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT LibrarianId FROM Librarian";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String a = resultSet.getString("LibrarianId");
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
        return "";
    }

    private static String getLibrarianPass(Connection connection) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT LibrarianPass FROM Librarian";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String a = resultSet.getString("LibrarianPass");
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
        return "";
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Student.URL, Student.USER, Student.PASSWORD);
            if (connection != null) {
                String User = "";
                int bookId =0;
                String UserPass = "";
                
                int loginAttempts = 0;
                Scanner scanner = new Scanner(System.in);
                int Choice = 0;
                String LibrarianUsername = getLibrarianId(connection);
                String LibrarianPassword = getLibrarianPass(connection);
                System.out.println("1.Librarian Login");
                System.out.println("2.Staff Login");
                System.out.println("3.Student Login");
                System.out.println("0.Close The Program");
                System.out.print("Enter Your Choice :");
                Choice = scanner.nextInt();
                scanner.nextLine();
                do {

                    switch (Choice) {
                        case 1:
                        int c = 0;
                        String studentId = "";
                            do {
                                
                                System.out.print("Enter Librarian Username :");
                                User = scanner.nextLine();
                                System.out.print("Enter Librarian Password :");
                                UserPass = scanner.nextLine();
                                clearScreen();
                                loginAttempts = loginAttempts + 1;
                                if ((!User.equals(LibrarianUsername) || !UserPass.equals(LibrarianPassword)) && loginAttempts == 3) {
                                    System.out.println("You Failed to Login 3 times so you have to restart application!");
                                    System.out.println("GoodBye!!");
                                    Choice = 0;
                                    break;
                                }
                                if (!User.equals(LibrarianUsername) || !UserPass.equals(LibrarianPassword)) {
                                    int a = 3 - loginAttempts;
                                    System.out.println("Only " + a + " Attempts left!");
                                }
                            } while (!User.equals(LibrarianUsername) || !UserPass.equals(LibrarianPassword));
                            if (User.equals(LibrarianUsername) && UserPass.equals(LibrarianPassword)) {
                               	clearScreen();
                               	System.out.println("Login successful!");
                               	System.out.println("Hello, Librarian!");
                               	Librarian l = new Librarian();
                                do 
                                {
                                	
                                	System.out.println("What You Would Like to do:");
                                	System.out.println("1.Add Staff");
                                	System.out.println("2.Remove Staff");
                                	System.out.println("3.Add Book");
                                	System.out.println("4.Remove Book");
                                	System.out.println("5.Add Student");
                                	System.out.println("6.Remove Student");
                                	System.out.println("7.Available Books In Library");
                                	System.out.println("8.Get Student Information");
                                	System.out.println("9.Get Staff Information");
                                	System.out.println("10.Issue a Book");
                                	System.out.println("11.Return a Book");
                                	System.out.println("12.Renew a Book");
                                	System.out.println("13.Get Student Fine");
                                	System.out.println("14.Pay Student Fine");
                              	    System.out.println("15.To Exit");
                             	  	System.out.print("Enter Your Choice:");
									c = scanner.nextInt();
									scanner.nextLine();
            						switch (c) {
                						case 1:
                						    l.addStaff(connection);
                						    break;
                						case 2:
                						    l.removeStaff(connection);
                						    break;
                						case 3:
                						    l.addBook(connection);
                						    break;
                						case 4:
                    						l.removeBook(connection);
                    						break;
                						case 5:
                						    l.addStudent(connection);
                    						break;
                						case 6:
                						    l.removeStudent(connection);
                						    break;
                						case 7:
                						    l.printBooks(connection);
                						    break;
                						case 8:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!l.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    l.studentinfo(connection,studentId);
                						    break;
                						case 9:
                						    l.printStaff(connection);
                						    break;
                						case 10:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!l.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    System.out.print("Enter book Id :");
                                			bookId = scanner.nextInt();
                                			if(!l.isBookIssued(connection,studentId,bookId)){
                                				System.out.println("This Book is not issued by student");
                						    	break;
                						    }
                    						l.issueBook(connection,studentId,bookId);
                    						break;
                						case 11:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!l.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    System.out.print("Enter book Id :");
                                			bookId = scanner.nextInt();
                                			if(!l.isBookIssued(connection,studentId,bookId)){
                                				System.out.println("This Book is not issued by student");
                						    	break;
                						    }
                						    l.returnBook(connection,studentId,bookId);
                    						break;
                						case 12:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!l.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    System.out.print("Enter book Id :");
                                			bookId = scanner.nextInt();
                                			if(!l.isBookIssued(connection,studentId,bookId)){
                                				System.out.println("This Book is not issued by student");
                						    	break;
                						    }
                						    l.renewBook(connection,studentId,bookId);
                						    break;
                						case 13:
                    						System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!l.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    int a = l.getFine(connection,studentId);
                						    System.out.println("Student Fine = "+a);
                    						break;
                						case 14:
                						    System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!l.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    l.repayFine(connection,studentId);
                						    
                    						break;
                						case 15:
                						    Choice = 0;
                						    System.out.println("GoodBye Librarian!");
                						    break;
                						default:
                    						System.out.println("Enter Valid Choice! ");
                   							break;
            						}
        						} while (c != 15);
        						
                            }
                            Choice = 0;
                            break;
                        case 2:
                        	String staffPassword = "";
                        	String staffId = "";
                        	int cp = 0;
                            Staff staff = new Staff();
                        	System.out.print("Enter Staff Id :");
                            staffId = scanner.nextLine();
                            if(!staff.isValidStaffId(connection,staffId)){
                            	System.out.println("Staff id is Invalid");
                				break;
                			}
                			System.out.print("Enter Staff Password :");
                            staffPassword = scanner.nextLine();
                			if(!staff.isValidStaffPassword(connection,staffId,staffPassword)){
                            	System.out.println("Staff Password is Invalid");
                				break;
                			}
                        	clearScreen();
                        	do 
                                {
                                	
                                	System.out.println("What You Would Like to do:");
                                	
                                	System.out.println("1.Add Student");
                                	System.out.println("2.Remove Student");
                                	System.out.println("3.Available Books In Library");
                                	System.out.println("4.Get Student Information");
                                	System.out.println("5.Issue a Book");
                                	System.out.println("6.Return a Book");
                                	System.out.println("7.Renew a Book");
                                	System.out.println("8.Get Student Fine");
                                	System.out.println("9.Pay Student Fine");
                              	    	System.out.println("10.To Exit");
                             	  	System.out.print("Enter Your Choice:");
									cp = scanner.nextInt();
									scanner.nextLine();
                        	switch (cp)
                        	{
                        				case 1:
                						    staff.addStudent(connection);
                    						break;
                						case 2:
                						    staff.removeStudent(connection);
                						    break;
                						case 3:
                						    staff.printBooks(connection);
                						    break;
                						case 4:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!staff.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    staff.studentinfo(connection,studentId);
                						    break;
                						case 5:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!staff.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    System.out.print("Enter book Id :");
                                			bookId = scanner.nextInt();
                    						staff.issueBook(connection,studentId,bookId);
                    						break;
                						case 6:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!staff.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    System.out.print("Enter book Id :");
                                			bookId = scanner.nextInt();
                                			if(!staff.isBookIssued(connection,studentId,bookId)){
                                				System.out.println("This Book is not issued by student");
                						    	break;
                						    }
                						    staff.returnBook(connection,studentId,bookId);
                    						break;
                						case 7:
                							System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!staff.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    System.out.print("Enter book Id :");
                                			bookId = scanner.nextInt();
                                			if(!staff.isBookIssued(connection,studentId,bookId)){
                                				System.out.println("This Book is not issued by student");
                						    	break;
                						    }
                						    staff.renewBook(connection,studentId,bookId);
                						    break;
                						case 8:
                						scanner.nextLine();
                    						System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!staff.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    staff.getFine(connection,studentId);
                    						break;
                						case 9:
                						    System.out.print("Enter Student Id :");
                                			studentId = scanner.nextLine();
                                			if(!staff.isValidStudentId(connection,studentId)){
                                				System.out.println("Student id is Invalid");
                						    	break;
                						    }
                						    staff.repayFine(connection,studentId);
                    						break;
                						case 10:
                						    Choice = 0;
                						    System.out.println("GoodBye Librarian!");
                						    break;
                						default:
                    						System.out.println("Enter Valid Choice! ");
                   							break;
            						}
            						} while (cp != 10);
                        	Choice =0;
                        	break;
                        case 3:
                        	String studentPassword = "";
                            Student student = new Student();
                        	String studentI = "";
                        	int p = 0;
                        	System.out.print("Enter Student Id :");
                            studentI = scanner.nextLine();
                            if(!student.isValidStudentId(connection,studentI)){
                            	System.out.println("Student id is Invalid");
                				break;
                			}
                			System.out.print("Enter Student Password :");
                            studentPassword = scanner.nextLine();
                			if(!student.isValidStudentPassword(connection,studentI,studentPassword)){
                            	System.out.println("Student Password is Invalid");
                				break;
                			}
                			
                			do 
                            {
                            	
                                System.out.println("1.Available Books In Library");
                                System.out.println("2.Get Student Information");
                                System.out.println("0.To Exit");
                             	System.out.print("Enter Your Choice:");
								p = scanner.nextInt();
								scanner.nextLine();
            					switch (p) {
                					case 1:
                						student.printBooks(connection);
                        				break;
                        			case 2:
                						student.studentinfo(connection,studentI);
                        				break;
                        			case 0:
                						Choice = 0;
                						System.out.println("GoodBye Student!");
                						break;
                        			default:
                        				System.out.println("Enter Valid Choice! ");
                        				break;
                    			}
                    		} while(p != 0);
                    		Choice =0;
                    		break;
              	}
            } while (Choice != 0);
        }
             else {
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
// ..............Prathmesh Ushkewar.......................
