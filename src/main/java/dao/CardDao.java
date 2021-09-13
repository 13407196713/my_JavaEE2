package dao;

import model.Card;
import model.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import po.CardTable;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CardDao {
	public int addCard(Card card);
	public int updateCard(Card card);
	public int deleteACard(int id);
	public int updatePwd(MyUser myuser);
	public CardTable selectACard(int id);
	public List<Map<String, Object>> selectAllCards(int uid);
	public List<Map<String, Object>> selectAllCardsByPage(@Param("startIndex") int startIndex, @Param("perPageSize") int perPageSize, @Param("uid") int uid);
}
