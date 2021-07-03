package dev.hyunwoo.main.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1/membership")
public class MainController {

    @Autowired
    MembershipsRepository membershipsRepository;

    /**
     * 1. 멤버십 전체조회하기 API ▪ 모든 멤버십을 조회합니다. ▪ 사용자 식별값을 입력값으로 받습니다. ▪ 나의 멤버십 조회 응답은 다음
     * 내용을 포함합니다. ▪ 멤버십 ID, 멤버십 이름, 포인트, 멤버십상태(활성, 비활성), 가입 일시
     */
    @GetMapping("/")
    public ResponseEntity<Object> fetch(@RequestHeader(name = "X-USER-ID", required = false) final String userId) {
        final HashMap<String, Object> response = new HashMap<String, Object>();

        if (userId != null) {
            final List<Memberships> memberships = membershipsRepository.fetch(userId);

            if (memberships != null) {
                response.put("suceess", true);
                response.put("response", memberships);
                response.put("error", null);
                return ResponseEntity.ok(response);
            } else {
                final HashMap<String, Object> error = new HashMap<String, Object>();
                error.put("message", "Internal Server Error");
                error.put("status", 500);

                response.put("suceess", false);
                response.put("response", null);
                response.put("error", error);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            final HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("message", "X-USER-ID must be provided");
            error.put("status", 400);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 2. 멤버십 등록하기 API ▪ 사용자 식별값, 멤버십 ID, 멤버십 이름, 포인트를 입력값으로 받습니다. ▪ 나의 멤버십 응답은 다음
     * 내용을 포함합니다. ▪ 멤버십 ID, 멤버십 이름
     */
    @PostMapping("/")
    public ResponseEntity<Object> register(@RequestHeader(name = "X-USER-ID", required = false) final String userId,
            @RequestBody final Memberships membership) {
        final HashMap<String, Object> response = new HashMap<String, Object>();

        if (userId != null) {
            final List<Memberships> memberships = membershipsRepository.register(userId, membership.getMembershipId(),
                    membership.getMembershipName(), membership.getPoint());

            if (memberships != null) {
                response.put("suceess", true);
                response.put("response", memberships);
                response.put("error", null);

                return ResponseEntity.ok(response);
            } else {
                final HashMap<String, Object> error = new HashMap<String, Object>();
                error.put("message", "Internal Server Error");
                error.put("status", 500);

                response.put("suceess", false);
                response.put("response", null);
                response.put("error", error);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            final HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("message", "X-USER-ID must be provided");
            error.put("status", 400);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 3. 멤버십 삭제(비활성화)하기 API ▪ 멤버십을 비활성화 상태로 변경합니다. ▪ 사용자 식별값, 멤버십 ID를 입력값으로 받습니다.
     */
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<Object> disable(@RequestHeader(name = "X-USER-ID", required = false) final String userId,
            @PathVariable final String membershipId) {
        final HashMap<String, Object> response = new HashMap<String, Object>();

        if (userId != null) {
            final int result = membershipsRepository.disable(userId, membershipId);

            if (result != 0) {
                response.put("suceess", true);
                response.put("response", true);
                response.put("error", null);

                return ResponseEntity.ok(response);
            } else {
                final HashMap<String, Object> error = new HashMap<String, Object>();
                error.put("message", "Internal Server Error");
                error.put("status", 500);

                response.put("suceess", false);
                response.put("response", false);
                response.put("error", error);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            final HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("message", "X-USER-ID must be provided");
            error.put("status", 400);

            response.put("suceess", false);
            response.put("response", false);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 4. 멤버십 상세 조회하기 API ▪ 사용자 식별값, 멤버십 ID를 입력값으로 받습니다. ▪ 나의 멤버십 응답은 다음 내용을 포함합니다.
     * ▪ 멤버십 ID, 멤버십 이름, 사용자 식별값, 포인트, 가입일시,멤버십상태 (활성, 비활성)을 응답합니다.
     */
    @GetMapping("/{membershipId}")
    public ResponseEntity<Object> search(@RequestHeader(name = "X-USER-ID", required = false) final String userId,
            @PathVariable final String membershipId) {
        final HashMap<String, Object> response = new HashMap<String, Object>();

        if (userId != null) {
            final Memberships membership = membershipsRepository.search(userId, membershipId);

            if (membership != null) {
                response.put("suceess", true);
                response.put("response", membership);
                response.put("error", null);

                return ResponseEntity.ok(response);
            } else {
                final HashMap<String, Object> error = new HashMap<String, Object>();
                error.put("message", "Internal Server Error");
                error.put("status", 500);

                response.put("suceess", false);
                response.put("response", null);
                response.put("error", error);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            final HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("message", "X-USER-ID must be provided");
            error.put("status", 400);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 5. 포인트 적립하기 API ▪ 사용자 식별값, 멤버십 ID, 사용 금액을 입력값으로 받습니다. ▪ 포인트 적립은 결제 금액의 1%가
     * 적립됩니다.
     */
    @PutMapping("/point")
    public ResponseEntity<Object> accumulate(@RequestHeader("X-USER-ID") final String userId,
            @RequestBody final Memberships membership) {
        final HashMap<String, Object> response = new HashMap<String, Object>();

        if (userId != null) {
            final int result = membershipsRepository.accumulate(userId, membership.getMembershipId(),
                    membership.getAmount());
            if (result != 0) {
                response.put("suceess", true);
                response.put("response", true);
                response.put("error", null);

                return ResponseEntity.ok(response);
            } else {
                final HashMap<String, Object> error = new HashMap<String, Object>();
                error.put("message", "Internal Server Error");
                error.put("status", 500);

                response.put("suceess", false);
                response.put("response", false);
                response.put("error", error);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            final HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("message", "X-USER-ID must be provided");
            error.put("status", 400);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}