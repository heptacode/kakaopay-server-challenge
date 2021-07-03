package dev.hyunwoo.main.model;

public class Memberships {
    private Integer seq;
    private String membershipId;
    private String userId;
    private String membershipName;
    private String startDate;
    private String membershipStatus;
    private Integer point;

    private Integer amount;


    public Memberships() {
    }

    public Memberships(String membershipId, String userId, String membershipName, String startDate,
            String membershipStatus, Integer point) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.membershipName = membershipName;
        this.startDate = startDate;
        this.membershipStatus = membershipStatus;
        this.point = point;
    }

    public Memberships(Integer seq, String membershipId, String userId, String membershipName, String startDate,
            String membershipStatus, Integer point) {
        this.seq = seq;
        this.membershipId = membershipId;
        this.userId = userId;
        this.membershipName = membershipName;
        this.startDate = startDate;
        this.membershipStatus = membershipStatus;
        this.point = point;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    

    public Integer getAmount() {
        return amount;
    }
}