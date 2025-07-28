package com.example.githubapi.model;

import java.util.List;

public class RepositoryResponse {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchesResponse> branchesResponses;

    public RepositoryResponse(String repositoryName, String ownerLogin, List<BranchesResponse> branchesResponses) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branchesResponses = branchesResponses;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchesResponse> getBranchesResponses() {
        return branchesResponses;
    }
}
