package maker.server.clothes;

import maker.server.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesJpaRepository extends JpaRepository<Clothes, Integer>, ClothesDynamicQueryRepository{
    @Query("Select clothesImageUrl from Clothes where season = :season order by rand()")
    List<String> findBySeason(@Param("season")String Season);

}
