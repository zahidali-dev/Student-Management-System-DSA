public class Student {
    private int id;
    private String name, grade, attendance;

    public Student(int id, String name, String grade, String attendance) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.attendance = attendance;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGrade() { return grade; }
    public String getAttendance() { return attendance; }
    public void setName(String n) { this.name = n; }
    public void setGrade(String g) { this.grade = g; }
    public void setAttendance(String a) { this.attendance = a; }

    // toString for display
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade + ", Attendance: " + attendance;
    }
}
