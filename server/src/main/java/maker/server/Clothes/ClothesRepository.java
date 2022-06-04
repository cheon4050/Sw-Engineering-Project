package maker.server.Clothes;

import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Dto.Clothes.ClothesPostDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.style;
import maker.server.Entity.weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Repository
public class ClothesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Clothes> clothesRowMapper = (rs, count) ->{
        Clothes clothes = new Clothes(
                rs.getInt("clothesIdx"),
                rs.getString("clothesImageUrl"),
                rs.getDate("date"),
                rs.getObject("season", ArrayList.class),
                rs.getObject("kind", ArrayList.class),
                rs.getObject("washingMethod", ArrayList.class),
                rs.getObject("size", Character.class),
                rs.getBoolean("bookmark")
        );
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

//    public ArrayList<Clothes> findByCategory(String userToken, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
//        ArrayList<Clothes> clothesList= jdbcTemplate.queryForObject(
//                "Select * From Clothes where userIdx = " +userToken,
//                clothesRowMapper,
//                1000L
//
//        );
//        return "";
//    }

//    public void delete(String userToken, int clothesIdx) {
//    }
//
//    public void update(String userToken, int clothesIdx, ClothesDto clothes) {
//    }
//
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
