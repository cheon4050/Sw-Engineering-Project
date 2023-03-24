package maker.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maker.server.entity.converter.BooleanToYNConverter;
import maker.server.entity.converter.IntegerArrayConverter;
import maker.server.entity.converter.StringArrayConverter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="USERS_ID")
    private Users users;

    private String clothesImageUrl;

    private LocalDateTime createDate;

//    @Convert(converter = StringArrayConverter.class)
//    private List<String> season = new ArrayList<>();
//
//    @Convert(converter = StringArrayConverter.class)
//    private List<String> kind = new ArrayList<>();

    @Convert(converter = IntegerArrayConverter.class)
    private List<Integer> washingMethod = new ArrayList<>();

    private String season;

    private String kind;
//
//    private String washingMethod;

    private char size;

//    @Convert(converter = BooleanToYNConverter.class)
    private boolean bookmark;


//    @Builder
//    public Clothes(Users users, String clothesImageUrl, List<String> season, List<String> kind, List<Integer> washingMethod, char size, boolean bookmark) {
//        this.users = users;
//        this.clothesImageUrl = clothesImageUrl;
//        this.season = season;
//        this.kind = kind;
//        this.washingMethod = washingMethod;
//        this.size = size;
//        this.bookmark = bookmark;
//    }

    @Builder
    public Clothes(Users users, String clothesImageUrl, String season, String kind, List<Integer> washingMethod, char size, boolean bookmark) {
        this.users = users;
        this.clothesImageUrl = clothesImageUrl;
        this.season = season;
        this.kind = kind;
        this.washingMethod = washingMethod;
        this.size = size;
        this.bookmark = bookmark;
    }



    public void updateBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

//    public void updateClothes(String clothesImageUrl, List<String> season, List<String> kind, List<Integer> washingMethod, char size, boolean bookmark) {
//        this.clothesImageUrl = clothesImageUrl;
//        this.season = season;
//        this.kind = kind;
//        this.washingMethod = washingMethod;
//        this.size = size;
//        this.bookmark = bookmark;
//    }

    public void updateClothes(String clothesImageUrl, String season, String kind, List<Integer> washingMethod, char size, boolean bookmark) {
        this.clothesImageUrl = clothesImageUrl;
        this.season = season;
        this.kind = kind;
        this.washingMethod = washingMethod;
        this.size = size;
        this.bookmark = bookmark;
    }
    @PrePersist
    public void createAt() {
        this.createDate = LocalDateTime.now();
    }

}
