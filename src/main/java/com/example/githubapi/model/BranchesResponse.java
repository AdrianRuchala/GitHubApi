package com.example.githubapi.model;

public class BranchesResponse {
    private String name;
    private String lastCommitSha;

    public BranchesResponse(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }
}
