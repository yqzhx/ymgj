package com.uustop.project.system.student.mapper;

import com.uustop.project.system.student.domain.StudentPersonnel;
import com.uustop.project.system.user.domain.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudentPersonnelMapper extends Mapper<StudentPersonnel> {
    /**
     * 根据条件分页查询用户对象
     *
     * @param studentPersonnel 用户信息
     * @return 用户信息集合信息
     */
    public List<StudentPersonnel> selectStudentPersonnelList(StudentPersonnel studentPersonnel);
    /**
     * 根据loginName获得userName
     *
     * @return 结果
     */
    List<StudentPersonnel> selectUserNameByLoginName();

    public StudentPersonnel selectMfrsAccountByLoginName(String userName);

    int selectCountByClinicId(Integer clinicId);
}
