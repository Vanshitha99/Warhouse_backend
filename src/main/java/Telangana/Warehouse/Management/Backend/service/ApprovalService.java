package Telangana.Warehouse.Management.Backend.service;

import Telangana.Warehouse.Management.Backend.entity.Approval;
import Telangana.Warehouse.Management.Backend.repository.ApprovalRepository;
import Telangana.Warehouse.Management.Backend.repository.GateEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final GateEntryRepository gateEntryRepository;

    public ApprovalService(
            ApprovalRepository approvalRepository,
            GateEntryRepository gateEntryRepository) {
        this.approvalRepository = approvalRepository;
        this.gateEntryRepository = gateEntryRepository;
    }

    @Transactional(readOnly = true)
    public List<Approval> getAllApprovals() {
        return approvalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Approval> getApprovalsByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status is required");
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!List.of("PENDING", "APPROVED", "REJECTED")
                .contains(normalizedStatus)) {
            throw new IllegalArgumentException(
                    "Status must be PENDING, APPROVED, or REJECTED");
        }

        return approvalRepository.findByStatusIgnoreCase(normalizedStatus);
    }

    @Transactional
    public Approval createApproval(
            Long gateEntryId,
            String transactionType) {

        if (gateEntryId == null || gateEntryId <= 0) {
            throw new IllegalArgumentException(
                    "Valid gate entry ID is required");
        }

        if (!gateEntryRepository.existsById(gateEntryId)) {
            throw new IllegalArgumentException(
                    "Gate entry not found: " + gateEntryId);
        }

        if (transactionType == null || transactionType.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction type is required");
        }

        if (approvalRepository.existsByGateEntryId(gateEntryId)) {
            throw new IllegalArgumentException(
                    "An approval record already exists for this gate entry");
        }

        Approval approval = new Approval();
        approval.setGateEntryId(gateEntryId);
        approval.setTransactionType(transactionType.trim());
        approval.setStatus("PENDING");

        return approvalRepository.save(approval);
    }

    @Transactional
    public Approval reviewApproval(
            Long id,
            String decision,
            String remarks,
            String reviewedBy) {

        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Approval not found: " + id));

        if (!"PENDING".equalsIgnoreCase(approval.getStatus())) {
            throw new IllegalArgumentException(
                    "Only pending approvals can be reviewed");
        }

        if (decision == null || decision.isBlank()) {
            throw new IllegalArgumentException("Decision is required");
        }

        String normalizedDecision = decision.trim().toUpperCase();

        if (!List.of("APPROVED", "REJECTED")
                .contains(normalizedDecision)) {
            throw new IllegalArgumentException(
                    "Decision must be APPROVED or REJECTED");
        }

        if (reviewedBy == null || reviewedBy.isBlank()) {
            throw new IllegalArgumentException(
                    "Reviewer is required");
        }

        approval.setStatus(normalizedDecision);
        approval.setRemarks(remarks);
        approval.setReviewedBy(reviewedBy.trim());
        approval.setReviewedAt(LocalDateTime.now());

        return approvalRepository.save(approval);
    }
}

