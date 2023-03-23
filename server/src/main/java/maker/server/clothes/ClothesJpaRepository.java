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
public interface ClothesJpaRepository extends JpaRepository<Clothes, Integer> {

//    @Query
//    List<Clothes> findByUsersIdAndOptions(Integer usersId, String season, String kind, boolean bookmark);

//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("Update Clothes c SET c.bookmark = :bookmark where c.id = :clothesId")
//    int updateBookmark(@Param("clothesId")Integer clothesIdx, @Param("bookmark") boolean bookmark);

//    Optional<Clothes> findByUsersId(Integer userId);

    @Query("Select clothesImageUrl from Clothes where season = :season order by rand()")
    List<String> findBySeason(@Param("season")String Season);



}
