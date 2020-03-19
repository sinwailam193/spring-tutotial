import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Imperative approach
        List<Person> people = getPeople();
        List<Person> females = new ArrayList<>();
        for (Person person : people) {
            if (person.getGender().equals(Gender.FEMALE)) {
                females.add(person);
            }
        }
        females.forEach(System.out::println);

        // Declarative approach
        List<Person> females2 = people.stream().filter(person -> person.getGender().equals(Gender.FEMALE))
                .collect(Collectors.toList());
        females2.forEach(System.out::println);

    }

    private static List<Person> getPeople() {
        return List.of(new Person("Aaron", 26, Gender.MALE), new Person("Bryan", 26, Gender.MALE),
                new Person("Stacey", 26, Gender.FEMALE), new Person("Maria", 26, Gender.FEMALE));
    }
}