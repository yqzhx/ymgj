package com.test.student;

import com.uustop.UUSTOPApplication;
import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.student.mapper.StudentClassMapper;
import com.uustop.project.system.student.mapper.StudentPersonnelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UUSTOPApplication.class)
public class StudentMapperTest {
    @Autowired
    private StudentClassMapper studentClassMapper;
    @Autowired
    private StudentPersonnelMapper studentPersonnelMapper;
    @Test
    public void mapper(){
        /*StudentPersonnel student = new StudentPersonnel();
        student.setStuUserId(1);
        student.setStuUserName("zhx");*//*
        String column = "stuUserName";
        String condition = "%小%";
        Example example = new Example(StudentPersonnel.class);
        example.createCriteria().andEqualTo(column, condition);
        List<StudentPersonnel> studentPersonnels1 = studentPersonnelMapper.selectByExample(example);
        for (StudentPersonnel studentPersonnel : studentPersonnels1) {
            System.out.println("studentPersonnel = " + studentPersonnel);
        }
        *//*List<StudentClass> studentClasses = studentClassMapper.selectAll();
        studentClasses.forEach((studentClass -> System.out.println("studentClass = " + studentClass)));*//*
//        studentPersonnelMapper.insertSelective(student);
        List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectAll();
        studentPersonnels.forEach(studentPersonnel -> System.out.println("studentPersonnel = " + studentPersonnel));*/
        StudentPersonnel s = new StudentPersonnel();
        List<StudentPersonnel> studentPersonnels = studentPersonnelMapper.selectStudentPersonnelList(s);
        for (StudentPersonnel studentPersonnel : studentPersonnels) {
            System.out.println("studentPersonnel = " + studentPersonnel);
        }
    }
}
