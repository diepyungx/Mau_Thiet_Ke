package MauThietKe;

import java.util.ArrayList;
import java.util.List;
interface Person {
    String getDetails();
    boolean isMarried();
    List<Person> getChildren();
}

/**
 * Individual.
 */
class Individual implements Person {
    private String name;
    private String birthDate;
    private String gender;
    private boolean married;
    private List<Person> children;

    public Individual(String name, String birthDate, String gender, boolean married) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.married = married;
        this.children = new ArrayList<>();
    }

    @Override
    public String getDetails() {
        return "Name: " + name + ", Birth Date: " + birthDate + ", Gender: " + gender + ", Married: " + married;
    }

    @Override
    public boolean isMarried() {
        return married;
    }

    @Override
    public List<Person> getChildren() {
        return children;
    }

    public void addChild(Person child) {
        children.add(child);
    }
}

/**
 * family.
 */
class Family implements Person {
    private List<Person> memberList = new ArrayList<>();

    public void addMember(Person person) {
        memberList.add(person);
    }

    public List<Person> getMembers() {
        return memberList;
    }

    @Override
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        for (Person member : memberList) {
            details.append(member.getDetails()).append("\n");
        }
        return details.toString();
    }

    @Override
    public boolean isMarried() {
        return memberList.size() > 1;
    }

    @Override
    public List<Person> getChildren() {
        List<Person> children = new ArrayList<>();
        for (Person member : memberList) {
            children.addAll(member.getChildren());
        }
        return children;
    }
}

public class Genealogy {
    public static void main(String[] args) {
        Individual james = new Individual("James", "01/01/1970", "Male", true);
        Individual hana = new Individual("Hana", "02/02/1975", "Female", true);
        Individual ryan = new Individual("Ryan", "03/03/1995", "Male", false);
        Individual kai = new Individual("Kai", "04/04/1998", "Male", true);
        Individual jennifer = new Individual("Jennifer", "05/05/2000", "Female", true);

        /**
         * create family.
         */
        Family jamesFamily = new Family();
        jamesFamily.addMember(james);
        jamesFamily.addMember(hana);

        Family ryanFamily = new Family();
        ryanFamily.addMember(ryan);

        Family kaiFamily = new Family();
        kaiFamily.addMember(kai);
        kaiFamily.addMember(jennifer);

        /**
         * Building the genealogy tree.
         */
        jamesFamily.addMember(ryanFamily);
        ryanFamily.addMember(kaiFamily);

        findUnmarried(jamesFamily);
        findTwoChildCouples(jamesFamily);
        findLatestGeneration(jamesFamily, 1);
    }

    private static void findUnmarried(Person person) {
        if (person instanceof Individual) {
            Individual individual = (Individual) person;
            if (!individual.isMarried()) {
                System.out.println("Unmarried person: " + "\n" + individual.getDetails());
            }
        } else if (person instanceof Family) {
            for (Person member : ((Family) person).getMembers()) {
                findUnmarried(member);
            }
        }
    }

    private static void findTwoChildCouples(Person person) {
        if (person instanceof Family) {
            Family family = (Family) person;
            List<Person> members = family.getMembers();
            if (members.size() == 2 && members.get(0) instanceof Individual && members.get(1) instanceof Individual) {
                System.out.println("Two-child couple: " + "\n" + members.get(0).getDetails() + "\n" + members.get(1).getDetails());
            }
        }
        if (person instanceof Family) {
            for (Person member : ((Family) person).getMembers()) {
                findTwoChildCouples(member);
            }
        }
    }

    private static void findLatestGeneration(Person person, int generation) {
        if (person instanceof Family) {
            Family family = (Family) person;
            List<Person> members = family.getMembers();
            if (!members.isEmpty()) {
                System.out.println("Latest generation (Generation " + "\n" + generation + "):");
                for (Person member : members) {
                    System.out.println(member.getDetails());
                }
                findLatestGeneration(members.get(0), generation + 1);
            }
        }
    }
}
