package maker.server.Clothes;

import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Dto.Clothes.ClothesPostDto;
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
        ArrayList<Clothes> clothesList= (ArrayList<Clothes>) jdbcTemplate.query(
                query[0],
                new RowMapper<Clothes>() {
                    public Clothes mapRow(ResultSet rs, int rowNum) throws SQLException{
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
                    }
                }
        );
        System.out.println("clothesList = " + clothesList);
        return clothesList;
    }

    public void delete(String userToken, int clothesIdx) {
        jdbcTemplate.execute("Delete From clothes where clothesIdx = " + clothesIdx);
    }

//    public void update(String userToken, int clothesIdx, ClothesDto clothes) {
//    }

//    public Clothes findByClothesIdx(String userToken, int clothesIdx) {
//        return
//    }
//
//    public void bookmarkByClothesIdx(String userToken, int clothesIdx) {
//    }
//
//    public ArrayList<style> recommend(String userToken, weather weather) {
//        return
//    }
}
