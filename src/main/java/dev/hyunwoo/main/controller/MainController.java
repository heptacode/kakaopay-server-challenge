package dev.hyunwoo.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hyunwoo.main.model.Memberships;
import dev.hyunwoo.main.repository.MembershipsRepository;
import dev.hyunwoo.main.utils.ResponseSender;

@RestController
@RequestMapping("/api/v1/membership")
public class MainController {

    @Autowired
    MembershipsRepository membershipsRepository;
    ResponseSender responseSender;

    /**
     * 1. 멤버십 전체조회하기 API ▪ 모든 멤버십을 조회합니다. ▪ 사용자 식별값을 입력값으로 받습니다. ▪ 나의 멤버십 조회 응답은 다음
     * 내용을 포함합니다. ▪ 멤버십 ID, 멤버십 이름, 포인트, 멤버십상태(활성, 비활성), 가입 일시
     */
    @GetMapping("")
    public ResponseEntity<Object> fetch(@RequestHeader(name = "X-USER-ID", required = false) String userId) {
        if (userId != null) {
            List<Memberships> memberships;
            try {
                memberships = membershipsRepository.fetch(userId);
            } catch (Exception e) {
                return responseSender.send(500, null);
            }

            return responseSender.send(200, memberships);
        } else {
            return responseSender.send(400, "X-USER-ID must be provided");
        }
    }

    /**
     * 2. 멤버십 등록하기 API ▪ 사용자 식별값, 멤버십 ID, 멤버십 이름, 포인트를 입력값으로 받습니다. ▪ 나의 멤버십 응답은 다음
     * 내용을 포함합니다. ▪ 멤버십 ID, 멤버십 이름
     */
    @PostMapping("")
    public ResponseEntity<Object> register(@RequestHeader(name = "X-USER-ID", required = false) String userId,
            @RequestBody Memberships membership) {
        if (userId != null) {
            List<Memberships> memberships;
            try {
                memberships = membershipsRepository.register(userId, membership.getMembershipId(),
                        membership.getMembershipName(), membership.getPoint());
            } catch (Exception e) {
                return responseSender.send(500, null);
            }

            return responseSender.send(200, memberships);
        } else {
            return responseSender.send(400, "X-USER-ID must be provided");
        }
    }

    /**
     * 3. 멤버십 삭제(비활성화)하기 API ▪ 멤버십을 비활성화 상태로 변경합니다. ▪ 사용자 식별값, 멤버십 ID를 입력값으로 받습니다.
     */
    @DeleteMapping("{membershipId}")
    public ResponseEntity<Object> disable(@RequestHeader(name = "X-USER-ID", required = false) String userId,
            @PathVariable String membershipId) {
        if (userId != null) {
            int result;
            try {
                result = membershipsRepository.disable(userId, membershipId);
            } catch (Exception e) {
                return responseSender.send(500, null);
            }

            if(result != 0){
                return responseSender.send(200, true);
            } else {
                return responseSender.send(404, null);
            }
        } else {
            return responseSender.send(400, "X-USER-ID must be provided");
        }
    }

    /**
     * 4. 멤버십 상세 조회하기 API ▪ 사용자 식별값, 멤버십 ID를 입력값으로 받습니다. ▪ 나의 멤버십 응답은 다음 내용을 포함합니다.
     * ▪ 멤버십 ID, 멤버십 이름, 사용자 식별값, 포인트, 가입일시, 멤버십상태 (활성, 비활성)을 응답합니다.
     */
    @GetMapping("{membershipId}")
    public ResponseEntity<Object> search(@RequestHeader(name = "X-USER-ID", required = false) String userId,
            @PathVariable String membershipId) {
        if (userId != null) {
            Memberships membership;
            try {
                membership = membershipsRepository.search(userId, membershipId);
            } catch (Exception e) {
                return responseSender.send(500, null);
            }

            if(membership != null) {
                return responseSender.send(200, membership);
            } else {
                return responseSender.send(404, null);
            }
        } else {
            return responseSender.send(400, "X-USER-ID must be provided");
        }
    }

    /**
     * 5. 포인트 적립하기 API ▪ 사용자 식별값, 멤버십 ID, 사용 금액을 입력값으로 받습니다. ▪ 포인트 적립은 결제 금액의 1%가
     * 적립됩니다.
     */
    @PutMapping("point")
    public ResponseEntity<Object> accumulate(@RequestHeader(name = "X-USER-ID", required = false) String userId,
            @RequestBody Memberships membership) {
        if (userId != null) {
            int result;
            try {
                result = membershipsRepository.accumulate(userId, membership.getMembershipId(), membership.getAmount());
            } catch (Exception e) {
                return responseSender.send(500, null);
            }

            if(result != 0) {
                return responseSender.send(200, true);
            } else {
                return responseSender.send(404, null);
            }
        } else {
            return responseSender.send(400, "X-USER-ID must be provided");
        }
    }
}