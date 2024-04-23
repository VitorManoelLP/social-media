package com.socialmedia.main.infra.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.main.context.TestContainerExtension;
import com.socialmedia.main.domain.Friendship;
import com.socialmedia.main.domain.enums.FriendshipStatus;
import com.socialmedia.main.domain.payload.UsersFound;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql(statements = {
        """
            INSERT INTO keycloak.user_entity
                    (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before)
                    VALUES('a54a0b0f-f3aa-46cd-a6b4-1cef872c8696', 'osvaldo@email.com', 'osvaldo@email.com', true, true, NULL, 'Osvaldo', 'Freitas', 'c266461d-fb6d-4682-9b39-54df3151438a', 'osvaldo', 1713294937660, NULL, 0);
       """,
       """
            INSERT INTO keycloak.user_entity
                    (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before)
                    VALUES('c3dfaaae-e871-43c9-9d17-056d23e5e8c2', 'teste@gmail.com', 'teste@gmail.com', true, true, NULL, 'teste', 'teste', 'c266461d-fb6d-4682-9b39-54df3151438a', 'teste', 1713295530621, NULL, 0);
       """,
       """
            INSERT INTO keycloak.user_entity
                    (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before)
                    VALUES('84a43a24-c802-4aae-9e67-2b7f1ce35449', 'antonio2@gmail.com', 'antonio2@gmail.com', true, true, NULL, 'Antonio', NULL, '005caf1b-0752-4c32-aae2-8a68ef86a6d1', 'antonio2', 1713414479591, NULL, 0);
       """
})
public class UserResourceTest extends TestContainerExtension {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("c3dfaaae-e871-43c9-9d17-056d23e5e8c2", List.of()));
    }

    @AfterAll
    static void shutdown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSearchAllUsersWithoutYourself() throws Exception {

        final String page = mockMvc.perform(get("/api/users?page=0&size=10"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> usersFoundPage = objectMapper.readValue(page, new TypeReference<>() {
        });

        final List<LinkedHashMap<String, String>> usersFound = (List<LinkedHashMap<String, String>>) usersFoundPage.get("content");

        assertThat(usersFound)
                .isNotEmpty()
                .hasSize(2)
                .extracting(content -> content.get("name"))
                .containsExactlyInAnyOrder("Antonio", "Osvaldo");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSearchOnlyOsvaldo() throws Exception {

        final String page = mockMvc.perform(get("/api/users?page=0&size=10")
                        .param("search", "firstName==Osvaldo"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> usersFoundPage = objectMapper.readValue(page, new TypeReference<>() {
        });

        final List<LinkedHashMap<String, String>> usersFound = (List<LinkedHashMap<String, String>>) usersFoundPage.get("content");

        assertThat(usersFound)
                .isNotEmpty()
                .hasSize(1)
                .extracting(content -> content.get("name"))
                .containsExactlyInAnyOrder("Osvaldo");
    }

    @Test
    public void shouldSendFriendRequest() throws Exception {

        mockMvc.perform(post("/api/users/request/84a43a24-c802-4aae-9e67-2b7f1ce35449"))
                .andExpect(status().isOk());

        final List<Friendship> friendShipRequest = getEm().createQuery("SELECT f FROM Friendship f WHERE f.friend.id = '84a43a24-c802-4aae-9e67-2b7f1ce35449'", Friendship.class)
                .getResultList();

        Assertions.assertThat(friendShipRequest)
                .hasSize(1)
                .extracting(friendship -> friendship.getRequester().getId(), Friendship::getStatus)
                .containsExactly(Tuple.tuple(
                       "c3dfaaae-e871-43c9-9d17-056d23e5e8c2",
                        FriendshipStatus.PENDING
                ));

    }

}