package net.daum.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.daum.vo.GongjiVO;

public interface AdminGongjiRepository extends JpaRepository<GongjiVO, Integer> {

}
