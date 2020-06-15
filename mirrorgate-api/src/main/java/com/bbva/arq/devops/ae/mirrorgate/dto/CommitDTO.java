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

package com.bbva.arq.devops.ae.mirrorgate.dto;

import java.util.List;
import java.util.Map;

public class CommitDTO {

    private String hash;
    private String repository;
    private Integer timestamp;
    private String message;
    private String authorName;
    private String authorEmail;
    private String committerName;
    private String committerEmail;
    private List<String> parentsIds;
    private Map<String, Integer> branches;

    public String getHash() {
        return hash;
    }

    public CommitDTO setHash(final String hash) {
        this.hash = hash;
        return this;
    }

    public String getRepository() {
        return repository;
    }

    public CommitDTO setRepository(final String repository) {
        this.repository = repository;
        return this;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public CommitDTO setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommitDTO setMessage(final String message) {
        this.message = message;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public CommitDTO setAuthorName(final String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public CommitDTO setAuthorEmail(final String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public String getCommitterName() {
        return committerName;
    }

    public CommitDTO setCommitterName(final String committerName) {
        this.committerName = committerName;
        return this;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public CommitDTO setCommitterEmail(final String committerEmail) {
        this.committerEmail = committerEmail;
        return this;
    }

    public List<String> getParentsIds() {
        return parentsIds;
    }

    public CommitDTO setParentsIds(final List<String> parentsIds) {
        this.parentsIds = parentsIds;
        return this;
    }

    public Map<String, Integer> getBranches() {
        return branches;
    }

    public CommitDTO setBranches(final Map<String, Integer> branches) {
        this.branches = branches;
        return this;
    }
}