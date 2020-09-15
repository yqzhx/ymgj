package com.test.student;

import com.uustop.UUSTOPApplication;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.service.StudentClassService;
import com.uustop.project.system.student.service.StudentPersonnelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UUSTOPApplication.class)
public class StudentTest {
    @Autowired
    private StudentPersonnelService studentPersonnelService;
    @Autowired
    private StudentClassService studentClassService;

    @Test
    public void testPersonnel(){
     /*   StudentPersonnel studentPersonnel = new StudentPersonnel();
//        studentPersonnel.setStuUserId(1);
        studentPersonnel.setStuUserName("zhx");
        studentPersonnelService.insertPersonnel(studentPersonnel);

        List<StudentPersonnel> studentPersonnels = studentPersonnelService.selectAllPersonnel();
        studentPersonnels.forEach(stu -> System.out.println("stu = " + stu));*/
        String column = "stuUserName";
        String condition = "小";
        List<StudentPersonnel> studentPersonnels = studentPersonnelService.selectPersonnelByCondition(column, condition);
        studentPersonnels.forEach(stu -> System.out.println("stu = " + stu));
    }

    @Test
    public void testClass(){
        String condition = "二";
        List<StudentClass> studentClasses = studentClassService.selectStudentClassByCondition(condition);
        for (StudentClass studentClass : studentClasses) {
            System.out.println("studentClass = " + studentClass);
        }
    }
    @Test
    public void test(){
        String str = "0";
        String[] split = str.split(",");
        System.out.println(split.length==1);
    }
}
