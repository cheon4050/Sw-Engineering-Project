package maker.server.Clothes;

import maker.server.Dto.Clothes.ClothesPostDto;
import maker.server.Dto.Clothes.ClothesPutDto;
import maker.server.Entity.Clothes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Repository
public class ClothesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Clothes> clothesRowMapper =  (rs,count) ->{
            Clothes clothes = new Clothes();
            clothes.setClothesIdx(rs.getInt("clothesIdx"));
            clothes.setClothesImageUrl(rs.getString("clothesImageUrl"));
            clothes.setDate(rs.getString("date"));
            clothes.setSeason(rs.getString("season"));
            clothes.setKind(rs.getString("kind"));
            clothes.setWashingMethod(rs.getString("washingMethod"));
            clothes.setSize(rs.getString("size"));
            clothes.setBookmark(rs.getBoolean("bookmark"));
            return clothes;

    };
    private final RowMapper<String> stringMapper =  (rs, count) -> {
        return rs.getString(1);
    };
    public void save(Integer userIdx, ClothesPostDto clothes) {
        jdbcTemplate.execute("INSERT INTO clothes(userIdx,date,bookmark,size,clothesImageUrl,kind,season,washingMethod) VALUES ("+
                userIdx + ",'"+
                clothes.getDate()+ "',"+
                clothes.isBookmark()+ ",'"+
                clothes.getSize()+ "','" +
                clothes.getClothesImageUrl() + "','"+
                clothes.getKind() + "','"+
                clothes.getSeason() + "','"+
                clothes.getWashingMethod() + "')");
    }

    public ArrayList<Clothes> findByCategory(Integer userIdx, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        final String[] query = {"Select * From Clothes where userIdx = " + userIdx};
        if (bookmark)
            query[0] = query[0] + " and bookmark = true";
        if (season != null)
            query[0] = query[0] + " and(season like '%"+String.join("%' OR season like'%", season) + "%') ";
        if(kind != null)
            query[0] = query[0] + " and(kind like '%"+String.join("%' OR kind like '%", kind) + "%') ";
        ArrayList<Clothes> clothesList = (ArrayList<Clothes>) jdbcTemplate.query(
                query[0],
                clothesRowMapper
        );
        return clothesList;
    }

    public void delete(int clothesIdx) {
        jdbcTemplate.execute("Delete From clothes where clothesIdx = " + clothesIdx);
    }

    public void update( int clothesIdx, ClothesPutDto clothes) {
        jdbcTemplate.execute("Update clothes set date = '" +clothes.getDate()+
                "', size = '"+ clothes.getSize() +
                "', clothesImageUrl = '" + clothes.getClothesImageUrl() +
                "', kind = '" + clothes.getKind() +
                "', season = '" + clothes.getSeason() +
                "', washingMethod = '"+ clothes.getWashingMethod()+
                "' where clothesIdx = "+clothesIdx);
    }

    public Clothes findByClothesIdx(int clothesIdx) {
        return jdbcTemplate.queryForObject(
                "select * from clothes where clothesIdx = "+clothesIdx,
                clothesRowMapper
        );
    }

    public void bookmarkByClothesIdx(int clothesIdx, boolean bookmark) {
        jdbcTemplate.execute("Update clothes set bookmark = "+bookmark + " where clothesIdx = " + clothesIdx);
    }

    public ArrayList<String> recommend(Integer userIdx, String season) {
        ArrayList<String> urlList = (ArrayList<String>) jdbcTemplate.query("select clothesImageUrl from clothes where userIdx = " + userIdx
                + " and season like '%" + season + "%' order by rand() limit 6" , stringMapper);
        return urlList;
    }
}
