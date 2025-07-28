package com.example.githubapi.service;

import com.example.githubapi.model.BranchesResponse;
import com.example.githubapi.model.RepositoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {
    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryResponse> getNonForkRepositories(String username) {
        try {
            String repoUrl = "https://api.github.com/users/" + username + "/repos";
            GitHubRepo[] repostiories = restTemplate.getForObject(repoUrl, GitHubRepo[].class);

            if (repostiories == null) return List.of();

            return Arrays.stream(repostiories)
                    .filter(repo -> !repo.isFork())
                    .map(repo -> {
                        List<BranchesResponse> branchesResponses = fetchBranches(username, repo.getName());
                        return new RepositoryResponse(
                                repo.getName(),
                                repo.getOwner().getLogin(),
                                branchesResponses
                        );
                    })
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("GitHub user not found: " + username);
            }
            throw e;
        }
    }

    private List<BranchesResponse> fetchBranches(String username, String repoName) {
        String branchesUrl = "https://api.github.com/repos/" + username + "/" + repoName + "/branches";
        GitHubBranch[] branches = restTemplate.getForObject(branchesUrl, GitHubBranch[].class);

        if (branches == null) return List.of();

        return Arrays.stream(branches)
                .map(branch -> new BranchesResponse(branch.getName(), branch.getCommit().getSha()))
                .collect(Collectors.toList());
    }

    //INTERNAL DTOs

    private static class GitHubRepo {
        private String name;
        private boolean fork;
        private Owner owner;


        public String getName() {
            return name;
        }

        public boolean isFork() {
            return fork;
        }

        public Owner getOwner() {
            return owner;
        }

        public static class Owner {
            private String login;

            public String getLogin() {
                return login;
            }

        }

    }

    private static class GitHubBranch {
        private String name;
        private Commit commit;

        public String getName() {
            return name;
        }

        public Commit getCommit() {
            return commit;
        }

        public static class Commit {
            private String sha;

            public String getSha() {
                return sha;
            }
        }
    }
}
