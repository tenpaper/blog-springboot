package com.example.blogspringbootjdbctemplate.service.impl;

import com.example.blogspringbootjdbctemplate.entity.Student;
import com.example.blogspringbootjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 付疆疆
 * @date 2020/11/25 8:10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * getJdbcTemplate().queryForObject(SQL_SELECT_INSCHARACTER, params, String.class);
     * queryForObject 方法需要传入三个参数，第一个参数是该方法需要执行的SQL语句。
     * 数组params为执行动态SQL时需要传入的参数，参数顺序与SQL中的参数顺序相同。
     * 最后一个参数是返回值的类型（只能是基本数据类型的封装类，如Integer、String）
     * 如果想使用自定义的类型的返回值：new BeanPropertyRowMapper(StoreDto.class)
     * （当POJO对象和数据看表字段完全对应或驼峰式与下划线对应时BeanPropertyRowMapper类会根据构造函数中的class来自动填充数据。）
     * 需要注意的是：queryForObject方法默认返回值不为空，如果可以肯定结果集不为空可以不做处理，否则需要用
     * try……catch代码块进行异常的捕获与处理。
     * @return
     */
    @Override
    public String getAllUsers() {
        return jdbcTemplate.queryForObject("select count(1) from STUDENT", String.class);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        String sql = "select * from sTUDENT";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public Student getById(int id) {
        String sql = "select * from student where id = ? ";
        List<Student> stu = jdbcTemplate.query(sql,new Object[]{id}, new StudentRowMapper());
        Student student = null;
        if(!stu.isEmpty()){
            student = stu.get(0);
        }
        return student;
    }

    /**
     * 插入用户-防止sql注入-可以返回该条记录的主键
     * @param student
     * @return
     */
    @Override
    public int addStu(Student student) {
        String sql = "insert into student(id,name,sex,age) values(null,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resRow = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
                ps.setString(1,student.getName());
                ps.setInt(2,student.getSex());
                ps.setInt(3,student.getAge());
                return ps;
            }
        },keyHolder);
        System.out.println("操作记录数："+resRow+" 主键："+keyHolder.getKey());
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    @Override
    public int deleteStu(int id) {
        String sql = "delete from student where id = ?";
        return jdbcTemplate.update(sql,id);
    }

    @Override
    public int updateStu(Student student) {
        String sql = "update student set name=?,sex=?,age=? where id=?";
        int res = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,student.getName());
                preparedStatement.setInt(2,student.getSex());
                preparedStatement.setInt(3,student.getAge());
                preparedStatement.setInt(4,student.getId());
            }
        });
        return res;
    }

    @Override
    public int isHasStu(int id) {
        String sql = "select * from student where id=?";
        List<Student> student = jdbcTemplate.query(sql, new Object[]{id}, new StudentRowMapper());
        if (student!=null && student.size()>0){
            return 1;
        } else {
            return 0;
        }
    }
    }

class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException{
        Student stu = new Student();
        stu.setId(resultSet.getInt("id"));
        stu.setAge(resultSet.getInt("age"));
        stu.setSex(resultSet.getInt("sex"));
        stu.setName(resultSet.getString("name"));
        return stu;
    }
}
