import java.util.*;

/**
 * The main entry point for the Student Project Allocation program.
 * It creates students, projects, and a teacher, then assigns projects using a greedy algorithm.
 */
public class Main {
    /**
     * The main method that initializes and runs the project allocation process.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        long startTime = System.nanoTime();

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        // Creating projects;
        Project P1 = new Project("P1", projectType.PRACTICAL);
        Project P2 = new Project("P2", projectType.THEORETICAL);
        Project P3 = new Project("P3", projectType.PRACTICAL);
        Project P4 = new Project("P4", projectType.THEORETICAL);

        // List of projects for the teacher object;
        List<Project>  projects = new ArrayList<>();
        projects.add(P1);
        projects.add(P2);
        projects.add(P3);
        projects.add(P4);

        // Defining students preferences for projects;
        Project[] S1_Preference = {P1, P2};
        Project[] S2_Preference = {P1, P3};
        Project[] S3_Preference = {P3, P4};
        Project[] S4_Preference = {P1, P4};

        // Creating new students with the preferences;
        Student S1 = new Student("S1", "xx.yy.zzzz", 1001, S1_Preference);
        Student S2 = new Student("S2", "xx.yy.zzzz", 1002, S2_Preference);
        Student S3 = new Student("S3", "xx.yy.zzzz", 1003, S3_Preference);
        Student S4 = new Student("S4", "xx.yy.zzzz", 1004, S4_Preference);

        // Creating a teacher;
        Teacher Cosmin = new Teacher("Cosmin", "xx.yy.zzzz", projects);

        // Creating a problem (students, teachers, projects);
        Problem Homework = new Problem(4,1,4);

        // Adding students to the problem;
        Homework.addStudent(S1);
        Homework.addStudent(S2);
        Homework.addStudent(S3);
        Homework.addStudent(S4);

        // Adding the teacher to the problem;
        Homework.addTeacher(Cosmin);

        // Adding the projects to the problem;
        Homework.addProject(P1);
        Homework.addProject(P2);
        Homework.addProject(P3);
        Homework.addProject(P4);

        // Adding students and teachers;
        Homework.addPerson(S1);
        Homework.addPerson(S2);
        Homework.addPerson(Cosmin);
        Homework.addPerson(S3);
        Homework.addPerson(S4);

        // Printing the people that have been added previosly;
        Homework.printPeople();

        // Creating a solution project for the problem "Homework"
        Solution Solver = new Solution(Homework);

        // Calling the greedy algorithm;
        Solver.solve();

        // Printing the solution;
        Solver.printSolution();

        Solver.solveHall();

        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) +  " nanoseconds");
        System.out.println("Execution time: " + (endTime - startTime)/1000000 + " miliseconds");

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory: " + (usedMemoryAfter - usedMemoryBefore) + " bytes");
        System.out.println("Used memory: " + (usedMemoryAfter - usedMemoryBefore)/1000000 + " megabytes");
    }
}

// Parent class for teachers and students;
class Person{
    private String name;
    private String dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Override equals to compare a person based on their name and DoB;
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getName(), person.getName()) && Objects.equals(getDateOfBirth(), person.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateOfBirth());
    }
}
// Student class that inherits from the person class;
class Student extends Person{
    private int regNumber;
    private Project[] prefferedProjects; // An array of prefered projects;

    public Student(String name, String dateOfBirth,int regNumber, Project[] prefferedProjects) {
        this.regNumber = regNumber;
        this.prefferedProjects = prefferedProjects;
        setName(name);
        setDateOfBirth(dateOfBirth);
    }

    public int getRegNumber() {
        return regNumber;
    }

    public Project[] getPrefferedProjects() {
        return prefferedProjects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Name: " + getName() +
                ", Birthday: " + getDateOfBirth() +
                ", Student ID: " + getRegNumber() +
                ", Projects: " + Arrays.toString(prefferedProjects) +
                '}';
    }

    // Override equals() to compare students based on their registration number;
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        return regNumber == student.regNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(regNumber);
    }
}

// Teacher class that inherits from the person class;
class Teacher extends Person{
    private List<Project> projects; // List of projects a teacher can give;

    public Teacher(String name, String dateOfBirth,List<Project> projects) {
        setName(name);
        setDateOfBirth(dateOfBirth);
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Name: " + getName() +
                ", Birthday: " + getDateOfBirth() +
                ", List Of Projects: " + projects +
                '}';
    }
}

// Enum for project type;
enum projectType{
    THEORETICAL,
    PRACTICAL;
}

// Class representing a project;
class Project{
    private String name;
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
                "Name:'" + name + '\'' +
                ", Type:" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Project project)) return false;
        return Objects.equals(getName(), project.getName()) && getType() == project.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }
}

// Problem class that stores students, teachers and projects;
class Problem {
    private Student[] student;
    private Teacher[] teacher;
    private Project[] project;
    private Person[] person;

    private int studentNumber = 0;
    private int teacherNumber = 0;
    private int projectNumber = 0;
    private int personNumber = 0;

    public Problem(int maxStuds, int maxTeachers, int maxProjects){
        student = new Student[maxStuds];
        teacher = new Teacher[maxTeachers];
        project = new Project[maxProjects];
        person = new Person[maxStuds + maxTeachers];
    }

    // Adds a student and prevents duplicates;
    public void addStudent(Student students){
        for(Student s: student){
            if(s!=null){
                if(s.equals(students)){
                    System.out.println("Student already exists!");
                    return;
                }
            }
        }

        if(studentNumber < student.length){
            student[studentNumber] = students;
            studentNumber++;
        }
        else {
            System.out.println("Error: Student number out of range!");
        }
    }

    // Adds a teacher and prevents duplicates;
    public void addTeacher(Teacher teachers){
        for(Teacher t: teacher){
            if(t!=null){
                if(t.equals(teachers)){
                    System.out.println("Teacher already exists!");
                    return;
                }
            }
        }

        if(teacherNumber < teacher.length){
            teacher[teacherNumber] = teachers;
            teacherNumber++;
        }
        else {
            System.out.println("Error: Teacher number out of range!");
        }
    }

    //Adds a project and prevents duplicates;
    public void addProject(Project projects){
        for(Project p: project){
            if(p!=null){
                if(p.equals(projects)){
                    System.out.println("Project already exists!");
                    return;
                }
            }
        }

        if(projectNumber < project.length){
            project[projectNumber] = projects;
            projectNumber++;
        }
        else {
            System.out.println("Error: Project number out of range!");
        }
    }

    // Adds a person and prevents duplicates;
    public void addPerson(Person persons){
        for(Person p: person){
            if(p!=null){
                if(p.equals(persons)){
                    System.out.println("Person already exists!");
                    return;
                }
            }
        }

        if( personNumber < person.length){
            person[personNumber] = persons;
            personNumber++;
        }
        else {
            System.out.println("Error: Student number out of range!");
        }
    }

    // Prints the students;
    public void printStudents(){
        for(Student  s: student){
            if(s != null){
                System.out.println(s.toString());
            }
        }
    }

    // Prints the teachers;
    public void printTeachers(){
        for(Teacher t: teacher){
            if(t != null){
                System.out.println(t.toString());
            }
        }
    }

    // Prints the projects;
    public void printProjects(){
        for(Project p: project){
            if(p != null){
                System.out.println(p.toString());
            }
        }
    }

    // Prints the persons;
    public void printPeople(){
        for(Person p: person){
            if(p != null){
                System.out.println(p.toString());
            }
        }
    }

    // Getter for projects;
    public Project[] getProjects() {
        return project;
    }

    // Getter for students;
    public Student[] getStudents() {
        return student;
    }
}

// Solution class the implements a greedy algorithm;
class Solution {
    private Problem problems;
    private Project[] studentAllocation;

    public Solution(Problem problems) {
        this.problems = problems;
        this.studentAllocation = new Project[problems.getStudents().length];
    }

    public void solve() {
        boolean[] assignedProjects = new boolean[problems.getProjects().length];

        Student[] students = problems.getStudents();
        Project[] projects = problems.getProjects();

        for(int i = 0; i < students.length; i++){
            Project[] preferences = students[i].getPrefferedProjects();

            for(Project prefferedProject: preferences){
                int projectIndex = getProjectIndex(prefferedProject, projects);

                if((projectIndex != -1) && (!assignedProjects[projectIndex])){
                    studentAllocation[projectIndex] = prefferedProject;
                    assignedProjects[projectIndex] = true;
                    break;
                }
            }
        }
    }

    public void solveHall(){
        if(checkHallTheorem() == true){
            System.out.println("Can be solved: Allocation Possible With Hall's Theorem!");
        } else {
            System.out.println("Cannot be solved: Allocation Impossible With Hall's Theorem!");
        }
    }

    // |N(S)| >= |S|
    /**
     * We get the students vector and check how big each subset is
     * Then we go through the preferences of each student subset and count how many different projects there are
     * @return false if the Hall's condition isn't met, true otherwise
     */
    private boolean checkHallTheorem(){
        Student[] students = problems.getStudents();
        int totalStudents = students.length;
        Project[] projects = problems.getProjects();
        int totalProjects = projects.length;

        // Creating the subsets of students |S|
        for(int i = 0; i < (1 << totalStudents); i++){
            int studentsCount = 0;
            boolean[] assignedProjects = new boolean[totalProjects];
            int differentProjects = 0;

            for(int j = 0; j < totalProjects; j++){
                if((i & (1 << j)) != 0){ //Verify if a student is part of the subset;
                    studentsCount++;
                }

                for(Project p: students[j].getPrefferedProjects()){ // Going through every student preferences
                    int projectIndex = getProjectIndex(p, projects);
                    if((projectIndex != -1)&&(assignedProjects[projectIndex] == false)){
                        assignedProjects[projectIndex] = true;
                        differentProjects++;
                    }
                }
            }
            if(differentProjects < studentsCount){ // |N(S)| >= |S|
                return false;
            }
        }
        return true;
    }

    // Obtains the index of a project;
    private int getProjectIndex(Project project, Project[] projects){
        for(int i = 0; i < projects.length; i++){
            if(projects[i] != null && projects[i].equals(project)){
                return i;
            }
        }
        return -1;
    }

    // Prints the final solution;
    public void printSolution(){
        Student[] students = problems.getStudents();
        for(int i = 0; i < students.length; i++){
            System.out.println(students[i].getName() + " -> " + (studentAllocation[i] != null ? studentAllocation[i].getName() : "No Project"));
        }
    }
}