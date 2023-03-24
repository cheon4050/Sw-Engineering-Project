package maker.server.clothes.Repository.v2;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maker.server.entity.Clothes;
import maker.server.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static maker.server.entity.QClothes.*;

@Slf4j
@Repository
@AllArgsConstructor
public class ClothesDynamicQueryRepositoryImpl implements ClothesDynamicQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Clothes> findAllByUsersIdAndOptions(Users users, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        BooleanBuilder seasonBuilder = new BooleanBuilder();
        BooleanBuilder kindBuilder = new BooleanBuilder();
        for (String s : season){
            seasonBuilder.or(clothes.season.contains(s));
        }
        for (String k : kind) {
            kindBuilder.or(clothes.kind.contains(k));
        }
        if (bookmark){
            kindBuilder.and(clothes.bookmark.eq(true));
        }
        kindBuilder.and(clothes.users.eq(users));
        List<Clothes> clothesList = jpaQueryFactory.selectFrom(clothes)
                .where(seasonBuilder, kindBuilder)
                .fetch();

        return clothesList;
    }

}
