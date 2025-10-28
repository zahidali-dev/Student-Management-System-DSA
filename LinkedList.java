
public class LinkedList {

    // Node inner class
    private class Node {
        Student student;
        Node next;

        Node(Student student) {
            this.student = student;
            this.next = null;
        }
    }

    private Node head;

    public LinkedList() {
        head = null;
    }

    // Add Student (at end) with duplicate ID check
    public void Add(Student student) {
        if (Search(student.getId()) != null) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " already exists.");
        }

        Node newNode = new Node(student);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Search Student by ID
    public Student Search(int id) {
        Node current = head;
        while (current != null) {
            if (current.student.getId() == id) {
                return current.student;
            }
            current = current.next;
        }
        return null;
    }

    // Update Student
    public boolean Update(int id, String name, String grade, String attendance) {
        Node current = head;
        while (current != null) {
            if (current.student.getId() == id) {
                current.student.setName(name);
                current.student.setGrade(grade);
                current.student.setAttendance(attendance);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Delete Student by ID
    public boolean Delete(int id) {
        if (head == null) return false;

        if (head.student.getId() == id) {
            head = head.next;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.student.getId() == id) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    // Get all students as array
    public Student[] GetAll() {
        int count = 0;
        Node current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        Student[] arr = new Student[count];
        current = head;
        int i = 0;

        while (current != null) {
            arr[i++] = current.student;
            current = current.next;
        }

        return arr;
    }

    // Check empty
    public boolean isEmpty() {
        return head == null;
    }
}

 