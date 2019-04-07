package cn.com.taiji.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 为{@link User}领域类提供包括分页和排序等基本的管理能力
 * @author zhuohao
 */
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findUserByAccount(@Param("account") String account);

    boolean exists(@Param("account") String account);
}