public class Main {
    public static void main(String[] args) {

        Student stud = new Student("Nicu");
        Project proj = new Project("Banking App", Project.projectType.PRACTICAL);

        System.out.println(stud + "\n" + proj);
    }
}

class Student{
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Project{
    private String name;
    public enum projectType{
        THEORETICAL,
        PRACTICAL;
    }
    private projectType type;

    public Project(String name, projectType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public projectType getType() {
        return type;
    }

    public void setType(projectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

