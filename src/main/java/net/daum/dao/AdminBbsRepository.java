package net.daum.dao;

import net.daum.vo.BbsVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminBbsRepository extends JpaRepository<BbsVO, Integer> {

}
