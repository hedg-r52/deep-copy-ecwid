package utils;

import models.Address;
import models.Man;
import models.Person;
import models.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CopyUtilsTest {

    @Test
    void whenDeepCopyPersonShouldCreatePersonCopy() {
        Person person = new Person("Andrei", 20);

        Person copyPerson = CopyUtils.deepCopy(person);

        assertEquals(person.getName(), copyPerson.getName());
        assertEquals(person.getAge(), copyPerson.getAge());
        assertNotEquals(person, copyPerson);

    }

    @Test
    void whenDeepCopyManShouldCreateNewMan() {
        Man man = new Man("Igor", 30, List.of("Book 1", "Book 2"));

        Man copyMan = CopyUtils.deepCopy(man);

        assertEquals(man.getName(), copyMan.getName());
        assertEquals(man.getAge(), copyMan.getAge());
        assertEquals(man.getFavoriteBooks().get(0), copyMan.getFavoriteBooks().get(0));
        assertEquals(man.getFavoriteBooks().get(1), copyMan.getFavoriteBooks().get(1));
        assertNotEquals(man.getFavoriteBooks(), copyMan.getFavoriteBooks());
        assertNotEquals(man, copyMan);
    }

    @Test
    void whenDeepCopyStudentShouldCreateNewStudent() {
        Student student = new Student("Ivan Ivanov", new Address("Address 1"));

        Student copyStudent = CopyUtils.deepCopy(student);

        assertEquals(student.getName(), copyStudent.getName());
        assertNotEquals(student, copyStudent);
        assertEquals(student.getAddress().getStreet(), copyStudent.getAddress().getStreet());
        assertNotEquals(student.getAddress(), copyStudent.getAddress());
    }
}