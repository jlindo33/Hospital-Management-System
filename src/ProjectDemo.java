import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.Scanner;
/**
 *
 * @author Victoria Cadogan, Joe Lindo
 *
 */
public class ProjectDemo {
    Connection connection;
    String sql;
    String DB_PATH = ProjectDemo.class.getResource("Project1.sqlite").getFile();

    public ProjectDemo() throws ClassNotFoundException, SQLException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName( "org.sqlite.JDBC" );
        connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
    }

    public void run() throws SQLException {
        Scanner input = new Scanner(System.in);
        Boolean using = true;

        while(using == true){
            String pretty = "**********";
            System.out.print("\n" + pretty + " Hospital Management System " + pretty);
            System.out.print("\n\nView Patients:\nEnter the patient ID you want to view:\nAvailable options: 20, 24, 31, 36\n");
            System.out.print("\nView Doctors:\nEnter the doctor ID you want to view:\nAvailable options: 69, 70, 74, 80");
            System.out.print("\nEnter 1 to view the Medical Staff or 0 to exit the system.");
            System.out.print("\nYour input: ");
            int param = Integer.parseInt(input.next());
            System.out.print("\n");
        if (param == 20 || param == 24 || param == 31 || param == 36) {

                sql = "SELECT PatientID, FirstName AS 'First Name', LastName AS 'Last Name', Gender, Address, Telephone, " +
                        "DiagDetail AS 'Diagnose', Remark, Date AS 'Bill Date', Amount From Patient " +
                        "INNER JOIN PatientDiagnosis USING (PatientID) " +
                        "INNER JOIN Bill USING (PatientID) " +
                        "WHERE PatientID LIKE ?";

                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, param);
                ResultSet res = stmt.executeQuery();
                while (res.next()) {
                    System.out.println("Patient ID: " + res.getString("PatientID") +
                            "\nPatient Name: " + res.getString("First Name") + " " + res.getString("Last Name") +
                            "\nGender: " + res.getString("Gender") +
                            "\nAddress: " + res.getString("Address") +
                            "\nTelephone: " + res.getString("Telephone") +
                            "\nMedical Diagnosis : " + res.getString("Remark") + " " + res.getString("Diagnose")  +
                            "\nMedical Bill Date: " + res.getString("Bill Date") +
                            "\nBill Total Amount Due: $" + res.getString("Amount"));


                }
            } else if (param == 69 || param == 70 || param == 74 || param == 80) {
            sql = "SELECT Staff.DoctorID, Doctor.FirstName, Doctor.LastName, Doctor.Gender, Doctor.Address, " +
                    "Staff.Telephone, Doctor.Designation, Staff.Department " +
                    "FROM Staff INNER JOIN Doctor USING (DoctorID) " +
                    "WHERE DoctorID LIKE ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, param);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                System.out.println("Doctor ID: " + res.getString("DoctorID") +
                        "\nDoctor Name: " + res.getString("FirstName") + " " + res.getString("LastName") +
                        "\nGender: " + res.getString("Gender") +
                        "\nAddress: " + res.getString("Address") +
                        "\nTelephone: " + res.getString("Telephone") +
                        "\nDesignation : " + res.getString("Designation") +
                        "\nDepartment: " + res.getString("Department"));

            }
        }else if (param == 1) {
            sql = "SELECT StaffID, Name, Gender, Address, Telephone, Department FROM Staff";
            PreparedStatement stmt = connection.prepareStatement(sql);
            //stmt.setInt(1, param);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                System.out.println("\nStaff ID: " + res.getString("StaffID") +
                        "\nLast Name: " + res.getString("Name") +
                        "\nGender: " + res.getString("Gender") +
                        "\nAddress: " + res.getString("Address") +
                        "\nTelephone: " + res.getString("Telephone") +
                        "\nDepartment: " + res.getString("Department"));

            }
        } else if (param == 0) {
            System.out.print("Goodbye!");
            using = false;
        }
        else
            System.out.print("Please enter a valid input!");
        }
        }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ProjectDemo demo = new ProjectDemo();
        demo.run();
    }
}