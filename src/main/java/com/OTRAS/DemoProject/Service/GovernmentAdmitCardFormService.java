package com.OTRAS.DemoProject.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.OTRAS.DemoProject.Entity.GovernmentAdmitCardForm;
import com.OTRAS.DemoProject.Repository.GovernmentAdmitCardFormRepository;
import com.OTRAS.DemoProject.Util.FileUploadUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GovernmentAdmitCardFormService {

    private final GovernmentAdmitCardFormRepository repo;
    private final FileUploadUtility fileUploadUtility;

    public GovernmentAdmitCardForm create(
            String jobCategory,
            String authorizedSignature,
            MultipartFile signatureFile,
            MultipartFile allLocationsFile
    ) {
        GovernmentAdmitCardForm form = new GovernmentAdmitCardForm();
        form.setJobCategory(jobCategory);
        form.setAuthorizedSignature(authorizedSignature);

        if (signatureFile != null && !signatureFile.isEmpty()) {
            form.setUploadSignature(fileUploadUtility.uploadFile(signatureFile, "signature"));
        }

        if (allLocationsFile != null && !allLocationsFile.isEmpty()) {
            form.setUploadAllLocations(fileUploadUtility.uploadFile(allLocationsFile, "locations"));
        }

        return repo.save(form);
    }

    public GovernmentAdmitCardForm update(
            Long id,
            String jobCategory,
            String authorizedSignature,
            MultipartFile signatureFile,
            MultipartFile allLocationsFile
    ) {
        GovernmentAdmitCardForm form = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record Not Found"));

        form.setJobCategory(jobCategory);
        form.setAuthorizedSignature(authorizedSignature);

        if (signatureFile != null && !signatureFile.isEmpty()) {
            form.setUploadSignature(fileUploadUtility.replaceFile(signatureFile, "signature", form.getUploadSignature()));
        }

        if (allLocationsFile != null && !allLocationsFile.isEmpty()) {
            form.setUploadAllLocations(fileUploadUtility.replaceFile(allLocationsFile, "locations", form.getUploadAllLocations()));
        }

        return repo.save(form);
    }

    public List<GovernmentAdmitCardForm> getAll() {
        return repo.findAll();
    }

    public GovernmentAdmitCardForm getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Record Not Found"));
    }

    public GovernmentAdmitCardForm getByJobCategory(String jobCategory) {
        return repo.findByJobCategory(jobCategory)
                .orElseThrow(() -> new RuntimeException("No admit card form found for job category: " + jobCategory));
    }

    public void delete(Long id) {
        GovernmentAdmitCardForm form = getById(id);

        fileUploadUtility.deleteFile(form.getUploadSignature());
        fileUploadUtility.deleteFile(form.getUploadAllLocations());

        repo.delete(form);
    }
}