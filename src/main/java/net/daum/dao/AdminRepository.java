package net.daum.dao;

import net.daum.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminVO, String> {


}
