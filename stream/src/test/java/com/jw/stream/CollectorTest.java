package com.jw.stream;


import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectorTest {

    List<Student> students = new ArrayList<>();

    @BeforeEach
    public void init() {
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setName(i + "");
            if (i % 2 == 0) {
                student.setGender("male");
            } else {
                student.setGender("female");
            }
            student.setGrade(i % 4);
            students.add(student);
        }
    }

    @Test
    public void test() {
        Set<Integer> ages = students.stream().map(Student::getAge).collect(Collectors.toSet());
        System.out.println(ages);

        // 统计
        IntSummaryStatistics ageStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.out.println(ageStatistics);

        // 分区（学生按男女分区）
        Map<Boolean, List<Student>> genderPartition = students.stream().collect(
                Collectors.partitioningBy(s -> s.getGender().equals("male"))
        );
        System.out.println(genderPartition);

        // 分组（按照班级分组）
        Map<Integer, List<Student>> group = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        System.out.println(group);

        // 统计班级个数
        Map<Integer, Long> gradeCount = students.stream().collect(
                Collectors.groupingBy(
                        Student::getGrade, Collectors.counting()
                )
        );
        System.out.println(gradeCount);
    }
}
