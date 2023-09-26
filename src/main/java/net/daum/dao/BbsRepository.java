package net.daum.dao;

import net.daum.vo.BbsVO;
import org.springframework.data.jpa.repository.JpaRepository;

/** 1.BbsRepository는 사용자 자료실을 JPA로 실행하기 위한 Repository이다.
 *  2.JpaRepository 인터페이스의 부모 인터페이스 중에서 PagingAndSortingRepository에서
 *  페이징과 정렬이라는 기능을 제공한다.
 *  PagingAndSortingRepository의 부모 인터페이스가 CrudRepository이다.
 */
public interface BbsRepository extends JpaRepository<BbsVO, Integer> {

}
