package maker.server.Clothes;

import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Dto.Clothes.ClothesPostDto;
import maker.server.Dto.Clothes.ClothesPutDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.style;
import maker.server.Entity.weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
    public void save(String userToken, ClothesPostDto clothes) {
        jdbcTemplate.execute("INSERT INTO clothes(userIdx,date,bookmark,size,clothesImageUrl,kind,season,washingMethod) VALUES ("+
                userToken + ",'"+
                clothes.getDate()+ "',"+
                clothes.isBookmark()+ ",'"+
                clothes.getSize()+ "','" +
                clothes.getClothesImageUrl() + "','"+
                clothes.getKind() + "','"+
                clothes.getSeason() + "','"+
                clothes.getWashingMethod() + "')");
    }

    public ArrayList<Clothes> findByCategory(String userToken, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        final String[] query = {"Select * From Clothes where userIdx = " + userToken + " and "};
        if (bookmark)
            query[0] = query[0] + "bookmark = true ";
        else
            query[0] = query[0] + "bookmark = false ";
        if (season != null)
            query[0] = query[0] + "and(season like '%"+String.join("%' OR season '%", season) + "%') ";
        if(kind != null)
            query[0] = query[0] + "and(kind like '%"+String.join("%' OR kind like '%", kind) + "%') ";
        ArrayList<Clothes> clothesList = (ArrayList<Clothes>) jdbcTemplate.query(
                query[0],
                clothesRowMapper
        );
        System.out.println("clothesList = " + clothesList);
        return clothesList;
    }

    public void delete(String userToken, int clothesIdx) {
        jdbcTemplate.execute("Delete From clothes where clothesIdx = " + clothesIdx);
    }

    public void update(String userToken, int clothesIdx, ClothesPutDto clothes) {
        jdbcTemplate.execute("Update clothes set date = '" +clothes.getDate()+
                "', bookmark = "+ clothes.isBookmark() +
                ", size = '"+ clothes.getSize() +
                "', clothesImageUrl = '" + clothes.getClothesImageUrl() +
                "', kind = '" + clothes.getKind() +
                "', season = '" + clothes.getSize() +
                "', washingMethod = '"+ clothes.getWashingMethod()+
                "' where clothesIdx = "+clothesIdx);
    }

    public String findClothesImageUrlByClothesIdx(int clothesIdx){
        return jdbcTemplate.queryForObject(
                "select clothesImageUrl from clothes where clothesIdx = " + clothesIdx,
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int count) throws SQLException{
                        return rs.getString(1);
                    }
                }
        );

    }
    public Clothes findByClothesIdx(String userToken, int clothesIdx) {
        return jdbcTemplate.queryForObject(
                "select * from clothes where clothesIdx = "+clothesIdx,
                clothesRowMapper
        );
    }

    public void bookmarkByClothesIdx(String userToken, int clothesIdx, boolean bookmark) {
        jdbcTemplate.execute("Update clothes set bookmark = "+bookmark + " where clothesIdx = " + clothesIdx);
    }

//    public ArrayList<style> recommend(String userToken, weather weather) {
//        return
//    }
}
