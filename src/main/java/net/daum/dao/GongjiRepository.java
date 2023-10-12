package net.daum.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.daum.vo.GongjiVO;

public interface GongjiRepository extends JpaRepository<GongjiVO, Integer> {

	/*JpaRepository 인터페이스의 부모 인터페이스인 PagingAndSortingRepository 에서 페이징과 정렬이라는 기능을 제공한다.
	 * PagingAndSortingRepository의 부모 인터페이스가 CrudRepository이다.
	 */
     
	/* JPA서브쿼리의 한계)
	 *  1. FROM 절(인라인 뷰)의 서브 쿼리는 현재 JPQL에서는 불가능하다.
	 *  2. 대신 조인으로 풀수 있으면 해결 가능하다.
	 *  3. 조인으로 해결 안될 경우 네이티브 쿼리를 사용하거나, 쿼리를 분리해서 따로 날려서 쿼리를 다시
	 *  조립하는 방식이 있다. 그래도 제일 좋은 방법은 조인으로 해결하는 것이다. nativeQuery(네이티브 쿼리)란 데이터베이스에 종속적인 SQL문을 그대로 사용하는 것을 말한다.즉 원래 쿼리
	 *  문을 쓰겠다는 것이다. 그러면 데이터베이스에 독립적인 이라는 것은 어느 정도 포기해야 한다. 남용하지 말고 적절하게 사용한다. 
	 *  결국 서브 쿼리문인 인라인 뷰는 jpa을 사용하는 것 보다 mybatis를 사용하는 것이 더 낫다.
	 */
	
}
