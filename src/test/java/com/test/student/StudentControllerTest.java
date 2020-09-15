package com.test.student;

import com.uustop.UUSTOPApplication;
import com.uustop.project.system.student.domain.StudentClass;
import com.uustop.project.system.student.mapper.StudentClassMapper;
import com.uustop.project.system.student.service.StudentClassService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UUSTOPApplication.class)
public class StudentControllerTest {
    @Autowired
    private StudentClassService studentClassService;
    @Autowired
    private StudentClassMapper studentClassMapper;
    @Test
    public void testController(){
        /*StudentClass studentClass = studentClassMapper.selectByPrimaryKey(100);
        String[] split = studentClass.getStuAncestors().split(",");
        for (String s : split) {
            System.out.println("s = " + s);
        }
        System.out.println(split.length);*/
//        List<StudentClass> studentClasses = studentClassService.selectClassIdByStuDeptId(100);
       /* List<StudentClass> studentClasses = studentClassService.selectAllTree();
        studentClasses.forEach(studentClass -> System.out.println("studentClass = " + studentClass));*/
    }
}
