/*
 * Copyright 2017 Banco Bilbao Vizcaya Argentaria, S.A..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bbva.arq.devops.ae.mirrorgate.api;

import com.bbva.arq.devops.ae.mirrorgate.core.dto.CommitDTO;
import com.bbva.arq.devops.ae.mirrorgate.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


/**
 * Commit controller.
 */
@RestController
public class CommitController {

    private final CommitService commitService;

    @Autowired
    public CommitController(CommitService commitService) {
        this.commitService = commitService;

    }

    @RequestMapping(value = "/api/commits", method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCommits(@Valid @RequestBody Iterable<CommitDTO> request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commitService.saveCommits(request));
    }

    @RequestMapping(value = "/api/commits/lastcommit", method = GET,
        produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLastCommitOfBranch(
        @RequestParam(value = "repo", required = true) String repo,
        @RequestParam(value = "branch", required = true) String branch
        ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commitService.getLastCommitOfBranch(repo, branch));
    }

}