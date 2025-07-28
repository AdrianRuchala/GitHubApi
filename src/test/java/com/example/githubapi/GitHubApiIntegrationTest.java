package com.example.githubapi;

import com.example.githubapi.model.BranchesResponse;
import com.example.githubapi.model.RepositoryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubApiIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnNonForkRepositoriesWithBranches() {
        String username = "AdrianRuchala";
        ResponseEntity<RepositoryResponse[]> response = restTemplate.getForEntity(
                "/api/github/" + username + "/repositories",
                RepositoryResponse[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RepositoryResponse[] repos = response.getBody();
        assertThat(repos).isNotNull();
        Assertions.assertNotNull(repos);
        assertThat(repos.length).isGreaterThan(0);

        for (RepositoryResponse repo : repos) {
            assertThat(repo.getRepositoryName()).isNotBlank();
            assertThat(repo.getOwnerLogin()).isEqualToIgnoringCase(username);

            List<BranchesResponse> branchesResponses = repo.getBranchesResponses();
            assertThat(branchesResponses).isNotNull();
            assertThat(branchesResponses.size()).isGreaterThan(0);

            for (BranchesResponse branch : branchesResponses) {
                assertThat(branch.getName()).isNotBlank();
                assertThat(branch.getLastCommitSha()).isNotBlank();
            }
        }
    }
}
