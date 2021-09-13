package dao;

import model.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import po.MyUserTable;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
	public List<MyUserTable> selectByUname(MyUser myUser);
	public int register(MyUser myUser);
	public List<MyUserTable> login(MyUser myUser);
}
