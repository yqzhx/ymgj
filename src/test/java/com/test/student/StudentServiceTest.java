package com.test.student;

import com.uustop.UUSTOPApplication;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.service.StudentClassService;
import com.uustop.project.system.student.service.StudentPersonnelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UUSTOPApplication.class)
public class StudentServiceTest {

    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private StudentPersonnelService studentPersonnelService;

    @Test
    public void testService(){
        List<StudentClass> studentClasses = studentClassService.selectStudentClassList(new StudentClass());
        for (StudentClass studentClass : studentClasses) {
            System.out.println("studentClass = " + studentClass);
        }
        List<Map<String, Object>> maps = studentClassService.selectStudentClassTree();
        for (Map<String, Object> map : maps) {
            System.out.println("map = " + map);
        }
        StudentClass studentClass = new StudentClass();
        studentClass.setStuDeptName("初一");
        studentClass.setStuParentId(100L);
        studentClass.setStuDelFlag("0");
        String s = studentClassService.checkSchoolClassNameUnique(studentClass);
        System.out.println("s = " + s);
        boolean b = studentClassService.checkStudentClassExistUser(100L);
        System.out.println("b = " + b);
    }
}
