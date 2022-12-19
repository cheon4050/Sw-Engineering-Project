package maker.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clothes {
    private int clothesIdx;
    private String clothesImageUrl;
    private String date;
    private ArrayList<String> season;
    private ArrayList<String> kind;
    private ArrayList<Integer> washingMethod;
    private char size;
    private boolean bookmark;

    public void setSeason(String strSeason){
        if(strSeason.equals("[]"))
            this.season = new ArrayList<String>();
        else
            this.season = new ArrayList<String>(Arrays.asList(strSeason.substring(1,strSeason.length()-1).split(", ")));
    }

    public void setKind(String strKind){
        if(strKind.equals("[]"))
            this.kind = new ArrayList<String>();
        else
            this.kind = new ArrayList<String>(Arrays.asList(strKind.substring(1,strKind.length()-1).split(", ")));
    }

    public void setWashingMethod(String str){
        if (str.equals("[]"))
                this.washingMethod = new ArrayList<Integer>();
        else {
            int[] arr = Arrays.stream(str.substring(1, str.length() - 1).split(", ")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            this.washingMethod = (ArrayList<Integer>) Arrays.stream(arr).boxed().collect(Collectors.toList());
        }
    }
    public void setSize(String str){
        this.size = str.charAt(0);
    }
}
