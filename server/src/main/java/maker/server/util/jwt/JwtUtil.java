package maker.server.util.jwt;

public interface JwtUtil {
    public String createJWT(int userIdx, long ttlMills);
    public Integer parseJwt(String jwt);
}
