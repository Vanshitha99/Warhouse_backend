
package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.Approval;
import Telangana.Warehouse.Management.Backend.service.ApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/approvals")
@CrossOrigin(origins = "http://localhost:5173")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping
    public ResponseEntity<List<Approval>> getApprovals(
            @RequestParam(required = false) String status) {

        if (status == null || status.isBlank()) {
            return ResponseEntity.ok(approvalService.getAllApprovals());
        }

        return ResponseEntity.ok(
                approvalService.getApprovalsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Approval> createApproval(
            @RequestParam Long gateEntryId,
            @RequestParam(defaultValue = "Gate Entry") String transactionType) {

        Approval approval = approvalService.createApproval(
                gateEntryId, transactionType);

        return ResponseEntity.status(HttpStatus.CREATED).body(approval);
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<Approval> reviewApproval(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Approval approval = approvalService.reviewApproval(
                id,
                request.get("decision"),
                request.get("remarks"),
                request.get("reviewedBy"));

        return ResponseEntity.ok(approval);
    }
}

