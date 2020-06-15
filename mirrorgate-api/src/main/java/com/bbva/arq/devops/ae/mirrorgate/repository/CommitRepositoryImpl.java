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

package com.bbva.arq.devops.ae.mirrorgate.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import com.bbva.arq.devops.ae.mirrorgate.utils.MirrorGateUtils.DoubleValue;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Ceil;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Subtract;
import org.springframework.data.mongodb.core.query.Criteria;

public class CommitRepositoryImpl implements CommitRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Double getSecondsToMaster(final List<String> repositories, final long timestamp) {
        final Aggregation agg = newAggregation(
            match(getCriteriaExpressionsForRepositories(repositories)
                .and("timestamp").gte(timestamp)
            ),
            group()
                .avg(
                    Ceil.ceilValueOf(
                        Subtract.valueOf("$branches.refs/remotes/origin/master").subtract("timestamp")
                    )
                ).as("value"),
            project("value")
                .andExclude("_id")
        );

        final AggregationResults<DoubleValue> secondsToMaster = mongoTemplate
            .aggregate(agg, "commits", DoubleValue.class);
        final DoubleValue seconds = secondsToMaster.getUniqueMappedResult();

        return seconds != null ? seconds.value : null;
    }

    @Override
    public Double getCommitsPerDay(final List<String> repositories, final long timestamp, final int daysBefore) {

        final Aggregation agg = newAggregation(
            match(getCriteriaExpressionsForRepositories(repositories)
                .and("timestamp").gte(timestamp)
            ),
            group()
                .count().as("value"),
            project("value")
                .andExclude("_id").and("value").divide(daysBefore)
        );

        final AggregationResults<DoubleValue> commitsPerDay
            = mongoTemplate.aggregate(agg, "commits", DoubleValue.class);
        final DoubleValue commits = commitsPerDay.getUniqueMappedResult();

        return commits != null ? commits.value : null;
    }

    private Criteria getCriteriaExpressionsForRepositories(final List<String> repos) {
        return Criteria.where("repository")
            .in(repos.stream().map(repo -> Pattern.compile("^.*" + repo + "$")).toArray());
    }
}
