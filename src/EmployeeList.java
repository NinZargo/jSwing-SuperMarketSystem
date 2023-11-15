import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class EmployeeList {
    ArrayList<Employee> employees;
    String filename;

    public EmployeeList(){
        employees = new ArrayList<Employee>();
        filename = "employees.txt";
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public Employee findEmployee(String username) {
        return this.employees.stream().filter(employee -> employee.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void loadFromFile(){
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            employees = (ArrayList<Employee>) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
