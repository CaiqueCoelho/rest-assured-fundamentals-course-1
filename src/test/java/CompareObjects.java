import pojo.Employee;

import java.util.HashSet;

public class CompareObjects {

    public static void main(String[] args) {
        Employee employee = new Employee(1, "Caique", "Male");
        Employee employee2 = new Employee(2, "Caique", "Male");

        System.out.println(employee.equals(employee2));

        HashSet set = new HashSet();
        set.add(employee);
        set.add(employee2);
        System.out.println(set);
    }
}
