package maker.server.clothes.Repository.v2;

import maker.server.entity.Clothes;
import maker.server.entity.Users;

import java.util.ArrayList;
import java.util.List;

public interface ClothesDynamicQueryRepository {
    List<Clothes> findAllByUsersIdAndOptions(Users users, ArrayList<String> season, ArrayList<String> kind, boolean bookmark);
}
