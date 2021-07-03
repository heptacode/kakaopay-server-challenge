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

    public List<Memberships> fetch(String userId) {
        try {
            String select = "SELECT * FROM memberships WHERE userId=? AND membershipStatus='Y'";
            List<Memberships> memberships = template.query(select, (result, rowNum) -> new Memberships(result.getInt("seq"), result.getString("membershipId"), result.getString("userId"), result.getString("membershipName"), result.getString("startDate"), result.getString("membershipStatus"), result.getInt(
                    "point")), userId);
            return memberships;
        } catch (Exception e) {
            System.out.println("Fetch > " + e);
        }
        return null;
    }

    public List<Memberships> register(String userId, String membershipId, String membershipName, Integer point) {
        try {
            String insert = "INSERT INTO memberships(userId, membershipId, membershipName, startDate, membershipStatus, point) VALUES(?,?,?,?,?,?)";
            template.update(insert, userId, membershipId, membershipName, LocalDateTime.now(), 'Y', point);

            String select = "SELECT * FROM memberships WHERE userId=?";
            List<Memberships> memberships = template.query(select, (result, rowNum) -> new Memberships(result.getInt("seq"), result.getString("membershipId"), result.getString("userId"), result.getString("membershipName"), result.getString("startDate"), result.getString("membershipStatus"), result.getInt(
                    "point")), userId);
            return memberships;
        } catch (Exception e) {
            System.out.println("Register > " + e);
        }
        return null;
    }

    public int disable(String userId, String membershipId) {
        try {
            String update = "UPDATE memberships SET membershipStatus='N' WHERE userId=? AND membershipId=? AND membershipStatus='Y'";
            return template.update(update, userId, membershipId);
        } catch (Exception e) {
            System.out.println("Disable > " + e);
        }
        return 0;
    }

    public Memberships search(String userId, String membershipId) {
        try {
            String select = "SELECT * FROM memberships WHERE userID=? AND membershipId=? AND membershipStatus='Y'";
            Memberships membership = template.queryForObject(select, (result, rowNum) -> new Memberships(result.getInt("seq"), result.getString("membershipId"), result.getString("userId"), result.getString("membershipName"), result.getString("startDate"), result.getString("membershipStatus"), result.getInt(
                "point")), userId, membershipId);
            return membership;
        } catch (Exception e) {
            System.out.println("Search > " + e);
        }
        return null;
    }

    public int accumulate(String userId, String membershipId, Integer amount) {
        try {
            String select = "SELECT point FROM memberships WHERE userId=? AND membershipId=? AND membershipStatus='Y'";
            Integer point = template.queryForObject(select, (result, rowNum) -> (Integer) result.getInt("point"), userId, membershipId);

            Integer newPoint = (int) (point + amount * 0.01);

            String update = "UPDATE memberships SET point=? WHERE userId=? AND membershipId=?";
            return template.update(update, newPoint, userId, membershipId);
        } catch (Exception e) {
            System.out.println("Accumulate > " + e);
        }
        return 0;
    }
}