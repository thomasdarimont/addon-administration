package org.osiam.addons.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.osiam.addons.administration.model.session.GeneralSessionData;
import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    OsiamConnector connector;
    
    @Mock
    AccessToken accessToken;
    
    @Spy
    @InjectMocks
    GeneralSessionData sessionData = new GeneralSessionData();
    
    @Spy
    @InjectMocks
    UserService toTestSpy = new UserService();


    @Test
    public void searchUser_advanced() {
        final String query = "testQuery";
        final Integer limit = 13;
        final Long offset = 12L;
        final String orderBy = "orderby";
        final Boolean asc = false; // desc
        final String attributes = "attributes";

        toTestSpy.searchUser(query, limit, offset, orderBy, asc, attributes);

        ArgumentCaptor<Query> cap = ArgumentCaptor.forClass(Query.class);

        verify(connector).searchUsers(cap.capture(), same(accessToken));

        Query usedQuery = cap.getValue();
        assertEquals(query, usedQuery.getFilter());
        assertTrue(limit == usedQuery.getCount());
        assertTrue(offset == usedQuery.getStartIndex());
        assertEquals(orderBy, usedQuery.getSortBy());
        assertEquals("descending", usedQuery.getSortOrder());
        assertEquals(attributes, usedQuery.getAttributes());
    }
}