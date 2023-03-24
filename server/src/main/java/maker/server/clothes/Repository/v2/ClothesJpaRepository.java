package maker.server.clothes.Repository.v2;

import maker.server.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * V2 ClothesRepository
 * Jpa 사용 ClothesRepository
 * */
@Repository
public interface ClothesJpaRepository extends JpaRepository<Clothes, Integer>, ClothesDynamicQueryRepository{
    @Query("Select clothesImageUrl from Clothes where season = :season order by rand()")
    List<String> findBySeason(@Param("season")String Season);

}
