package com.example.blogspringbootjdbctemplate.service;

import com.example.blogspringbootjdbctemplate.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @author 付疆疆
 * @date 2020/11/25 8:09
 */
public interface UserService {
    /**
     * 获取用户总量
     * @return
     */
    String getAllUsers();

    /**
     * 获取全部学生
     * @return
     */
    List<Map<String, Object>> findAll();

    /**
     * 根据id获取学生
     * @param id
     * @return
     */
    Student getById(int id);

    /**
     * 增加学生
     * @param student
     * @return
     */
    int addStu(Student student);

    /**
     * 根据id删除学生
     * @param id
     * @return
     */
    int deleteStu(int id);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    int updateStu(Student student);

    /**
     * 判断是否存在该学生
     * @param id
     * @return
     */
    int isHasStu(int id);
}
