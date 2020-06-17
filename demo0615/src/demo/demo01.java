package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class demo01 {
    public static void main(String[] args) {
        Vector<Student> stu = new Vector<>();
        ArrayList<Course> cour = new ArrayList<>();

        stu.add(new Student(1001,"张三"));
        stu.add(new Student(1002,"李四"));
        stu.add(new Student(1003,"王五"));
        stu.add(new Student(1004,"赵六"));
        stu.add(new Student(1005,"赵小宝"));
        stu.add(new Student(1006,"张小明"));

        cour.add(new Course(1, "Java程序设计", 3));
        cour.add(new Course(2, "Java程序设计", 4));
        cour.add(new Course(3, "Java程序设计", 3));
        cour.add(new Course(4, "Java程序设计", 4));

        Iterator<Student> sit = stu.iterator();
        while (sit.hasNext()){
            String name = sit.next().getName();
            System.out.println(name.length());
            if (name.length()>3){
                sit.remove();
            }
        }




        Iterator<Course> cit = cour.iterator();
        while (cit.hasNext()){
            System.out.println(cit.next());
        }

    }
}

class Student{
    private int sno;
    private String name;

    public Student(int sno, String name) {
        this.sno = sno;
        this.name = name;
    }

    public int getSno() {
        return sno;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sno=" + sno +
                ", name='" + name + '\'' +
                '}';
    }
}

class Course{
   private int cno;
   private String cname;
   private int credit;

    public Course(int cno, String cname, int credit) {
        this.cno = cno;
        this.cname = cname;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cno=" + cno +
                ", cname='" + cname + '\'' +
                ", credit=" + credit +
                '}';
    }
}