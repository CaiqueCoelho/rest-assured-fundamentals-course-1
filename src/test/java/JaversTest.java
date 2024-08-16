import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import pojo.Address;
import pojo.Student;

import java.util.List;

public class JaversTest {

    public static void main(String[] args) {

        Address address1 = new Address();
        address1.setId(1);
        address1.setStreet("Street1");

        Address address2 = new Address();
        address2.setId(1);
        address2.setStreet("Street2");

        Student student1 = new Student();
        student1.setName("Caique");
        student1.setId(1);
        student1.setAddresses(List.of(address2, address1));

        Student student2 = new Student();
        student2.setName("Coelho");
        student2.setId(1);
        student2.setAddresses(List.of(address1, address2));

        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.AS_SET).build();
        Diff diff = javers.compare(student1, student2);
        System.out.println(diff);
        System.out.println(diff.getChanges());
    }
}
