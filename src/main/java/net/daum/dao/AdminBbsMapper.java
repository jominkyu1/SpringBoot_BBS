package net.daum.dao;

import net.daum.vo.BbsVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminBbsMapper {

    void abbs_in(BbsVO b);
}
