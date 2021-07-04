package dev.hyunwoo.main.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dev.hyunwoo.main.model.Memberships;

@Repository
public class MembershipsRepository {

    @Autowired
    JdbcTemplate template;

    public List<Memberships> fetch(String userId) throws Exception {
        String select = "SELECT * FROM memberships WHERE userId=? AND membershipStatus='Y'";
        List<Memberships> memberships;
        try {
            memberships = template.query(select,
                    (result, idx) -> new Memberships(result.getInt("seq"), result.getString("membershipId"),
                            result.getString("userId"), result.getString("membershipName"),
                            result.getString("startDate"), result.getString("membershipStatus"),
                            result.getInt("point")),
                    userId);
        } catch (Exception e) {
            throw new Exception("Fetch > SELECT > " + e);
        }
        return memberships;
    }

    public List<Memberships> register(String userId, String membershipId, String membershipName, int point)
            throws Exception {
        String select = "SELECT count(*) AS CNT FROM memberships WHERE userId=? AND membershipId=? AND membershipStatus='Y'";
        try {
            int count = template.queryForObject(select, (result, idx) -> result.getInt("CNT"), userId,
                    membershipId);
            if (count >= 1) {
                // 중복 있을 경우
                throw new Exception ("Register > Membership already exists");
            }
        } catch (Exception e) {
            throw new Exception("Register > SELECT > " + e);
        }

        String insert = "INSERT INTO memberships(userId, membershipId, membershipName, startDate, membershipStatus, point) VALUES(?,?,?,?,?,?)";
        try {
            template.update(insert, userId, membershipId, membershipName, LocalDateTime.now(), 'Y', point);
        } catch (Exception e) {
            throw new Exception("Register > INSERT > " + e);
        }

        select = "SELECT * FROM memberships WHERE userId=?";
        List<Memberships> memberships;
        try {
            memberships = template.query(select,
                    (result, idx) -> new Memberships(result.getInt("seq"), result.getString("membershipId"),
                            result.getString("userId"), result.getString("membershipName"),
                            result.getString("startDate"), result.getString("membershipStatus"),
                            result.getInt("point")),
                    userId);
        } catch (Exception e) {
            throw new Exception("REGISTER > SELECT > " + e);
        }
        return memberships;
    }

    public int disable(String userId, String membershipId) throws Exception {
        String update = "UPDATE memberships SET membershipStatus='N' WHERE userId=? AND membershipId=? AND membershipStatus='Y'";
        int result = 0;
        try {
            result = template.update(update, userId, membershipId);
        } catch (Exception e) {
            throw new Exception("Disable > UPDATE > " + e);
        }
        return result;
    }

    public Memberships search(String userId, String membershipId) throws Exception {
        String select = "SELECT * FROM memberships WHERE userID=? AND membershipId=? AND membershipStatus='Y'";
        Memberships membership;
        try {
            membership = template.queryForObject(select,
                    (result, idx) -> new Memberships(result.getInt("seq"), result.getString("membershipId"),
                            result.getString("userId"), result.getString("membershipName"),
                            result.getString("startDate"), result.getString("membershipStatus"),
                            result.getInt("point")),
                    userId, membershipId);
        } catch (Exception e) {
            throw new Exception("Search > SELECT > " + e);
        }
        return membership;
    }

    public int accumulate(String userId, String membershipId, int amount) throws Exception {
        String select = "SELECT point FROM memberships WHERE userId=? AND membershipId=? AND membershipStatus='Y'";
        int point = 0;
        try {
            point = template.queryForObject(select, (result, idx) -> result.getInt("point"), userId,
                    membershipId);
        } catch (Exception e) {
            throw new Exception("Accumulate > SELECT > " + e);
        }

        int newPoint = point + ((int) (amount * 0.01));
        String update = "UPDATE memberships SET point=? WHERE userId=? AND membershipId=? AND membershipStatus='Y'";
        int result = 0;
        try {
            result =  template.update(update, newPoint, userId, membershipId);
        } catch (Exception e) {
            throw new Exception("Accumulate > UPDATE > " + e);
        }
        return result;
    }
}