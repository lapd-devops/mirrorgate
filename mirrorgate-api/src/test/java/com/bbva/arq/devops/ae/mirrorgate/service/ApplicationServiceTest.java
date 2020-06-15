/*
 * Copyright 2017 Banco Bilbao Vizcaya Argentaria, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bbva.arq.devops.ae.mirrorgate.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.bbva.arq.devops.ae.mirrorgate.dto.ApplicationReviewsDTO;
import com.bbva.arq.devops.ae.mirrorgate.dto.DashboardDTO;
import com.bbva.arq.devops.ae.mirrorgate.repository.ReviewRepository;
import com.bbva.arq.devops.ae.mirrorgate.support.Platform;
import com.bbva.arq.devops.ae.mirrorgate.support.TestObjectFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplicationServiceTest {

    //TODO improve tests
    @Mock
    private DashboardService dashboardService;
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Test
    public void testApplicationService() {
        final DashboardDTO dashboard1 = TestObjectFactory
            .createDashboardDTO("mirrorgate", Arrays.asList("mirrorgate", "mood"));
        final DashboardDTO dashboard2 = TestObjectFactory
            .createDashboardDTO("samuel", Arrays.asList("samuel1", "samuel2"));
        final List<DashboardDTO> listOfDashboards = Arrays.asList(dashboard1, dashboard2);

        final ApplicationReviewsDTO applicationReviewsDTO1 = TestObjectFactory
            .createApplicationDTO("mirrorgate", Platform.IOS);
        final List<ApplicationReviewsDTO> applicationReviewsDTOList = Collections.singletonList(applicationReviewsDTO1);

        when(dashboardService.getActiveDashboards()).thenReturn(listOfDashboards);
        when(reviewRepository.getLastReviewPerApplication(anyList())).thenReturn(applicationReviewsDTOList);

        final List<ApplicationReviewsDTO> reviews = applicationService.getApplicationsAndReviews();

        assertEquals(4, reviews.size());
    }

    @Test
    public void testNoReviewsForAnyApp() {
        final DashboardDTO dashboard1 = TestObjectFactory
            .createDashboardDTO("mirrorgate", Arrays.asList("mirrorgate", "mood"));
        final DashboardDTO dashboard2 = TestObjectFactory
            .createDashboardDTO("samuel", Arrays.asList("samuel1", "samuel2"));
        final List<DashboardDTO> listOfDashboards = Arrays.asList(dashboard1, dashboard2);

        final List<ApplicationReviewsDTO> applicationReviewsDTOList = new ArrayList<>();

        when(dashboardService.getActiveDashboards()).thenReturn(listOfDashboards);
        when(reviewRepository.getLastReviewPerApplication(anyList())).thenReturn(applicationReviewsDTOList);

        final List<ApplicationReviewsDTO> reviews = applicationService.getApplicationsAndReviews();

        assertEquals(4, reviews.size());
    }

}
